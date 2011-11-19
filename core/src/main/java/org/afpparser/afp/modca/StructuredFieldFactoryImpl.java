package org.afpparser.afp.modca;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.afpparser.afp.modca.structuredfields.begin.BeginObjectHandler;
import org.afpparser.afp.modca.structuredfields.map.MapObjectHandler;

/**
 * A plain vanilla Structured Field factory that creates objects of each type
 * and does nothing else.
 */
public class StructuredFieldFactoryImpl implements StructuredFieldFactory {

    private final FileChannel channel;

    public StructuredFieldFactoryImpl(FileChannel channel) {
        this.channel = channel;
    }

    public byte[] createStructuredField(SfIntroducer intro) {
        try {
            long byteOffset = intro.getOffset() + SfIntroducer.SF_Introducer_FIELD
                    + SfIntroducer.Carriage_Control_FIELD;
            if (intro.hasExtData()) {
                byteOffset += intro.getExtLength() + SfIntroducer.ExtLength_FIELD;
            }
            ByteBuffer buffer = ByteBuffer.allocate(intro.getLength()
                    - SfIntroducer.SF_Introducer_FIELD - SfIntroducer.Carriage_Control_FIELD);
            channel.read(buffer, byteOffset);
            return buffer.array();
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }
    }

    @Override
    public StructuredField createBegin(SfIntroducer introducer) {
        byte[] payload = createStructuredField(introducer);
        return BeginObjectHandler.handle(introducer, payload);
    }

    @Override
    public StructuredField createMap(SfIntroducer introducer) {
        byte[] payload = createStructuredField(introducer);
        return MapObjectHandler.handle(introducer, payload);
    }

}