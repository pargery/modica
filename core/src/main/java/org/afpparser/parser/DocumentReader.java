package org.afpparser.parser;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;

import org.afpparser.afp.modca.StructuredField;
import org.afpparser.common.ByteUtils;

/**
 * Reads the AFP document and allows access to an {@link Iterator} which can iterate over all the
 * structured fields within the document and return a collection of skeletal Structured Field
 * objects.
 */
public class DocumentReader implements Iterable<StructuredField> {

    private final FileChannel channel;

    private final ByteUtils byteUtils;

    private static final byte CARRIAGE_CONTROL = 0x5a;

    private final SfIterator sfIterator;

    /**
     * Constructor
     *
     * @param afpDoc an AFP document file.
     * @throws FileNotFoundException if the document was not found
     */
    public DocumentReader(File afpDoc) throws FileNotFoundException {
        this(new FileInputStream(afpDoc));
    }

    /**
     * Constructor
     *
     * @param afpDoc the file input stream wrapping the AFP document
     */
    public DocumentReader(FileInputStream afpDoc) {
        channel = afpDoc.getChannel();
        byteUtils = ByteUtils.newLittleEndianUtils();
        sfIterator = new SfIterator();
    }

    /**
     * Checks that the carriage control character, which precedes each structured field is present.
     *
     * @return
     * @throws IOException
     */
    private final boolean hasStructuredField() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        channel.read(buffer);
        byte[] fiveA = buffer.array();
        return fiveA[0] == CARRIAGE_CONTROL;
    }

    /**
     * Reads the file channel and returns the structured field that begins at the current position
     * in the file (channel.position()). No validation is done at this point other than checking
     * that the type is a valid structured field ID. Also no parsing is performed at this stage,
     * only a skeletal {@link StructuredField} that contains primitive data relevant to all
     * structured field objects.
     *
     * @return a structured field
     */
    private StructuredField createStructuredField() {
        try {
            if (!hasStructuredField()) {
                return null;
            }
            long offset = channel.position();
            ByteBuffer buffer = ByteBuffer.allocate(7);
            channel.read(buffer);
            byte[] introducer = buffer.array();
            int sfLength = byteUtils.bytesToUnsignedInt(introducer, 0, 2);
            byte[] id = new byte[3];
            System.arraycopy(introducer, 2, id, 0, 3);
            byte flags = introducer[5];
            int extLength = 0;
            if (StructuredField.hasSfiExtension(flags)) {
                buffer = ByteBuffer.allocate(1);
                channel.read(buffer);
                extLength = byteUtils.bytesToUnsignedInt(buffer.array());
            }
            StructuredField sf = new StructuredField(offset, sfLength, id, flags, extLength);
            channel.position(offset += sf.bytesToNextStructuredField());
            return sf;
        } catch (EOFException eof) {
            return null;
        } catch (IOException ioe) {
            throw new InvalidAfpException("This document is invalid.", ioe);
        }
    }

    /**
     * Returns an iterator used to iterate over each of the structured fields that make the document.
     */
    @Override
    public Iterator<StructuredField> iterator() {
        return sfIterator;
    }

    private final class SfIterator implements Iterator<StructuredField> {

        private StructuredField nextSf;

        private SfIterator() {
            nextSf = createStructuredField();
        }

        @Override
        public boolean hasNext() {
            return nextSf != null;
        }

        @Override
        public StructuredField next() {
            StructuredField sf = nextSf;
            nextSf = createStructuredField();
            return sf;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() not supported");
        }
    }
}
