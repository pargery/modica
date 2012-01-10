package org.afpparser.afp.ptoca;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.afpparser.afp.modca.Parameters;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link RepeatString}.
 */
public class RepeatStringTestCase extends ControlSequenceTestCase<RepeatString> {
    private RepeatString sut;
    private RepeatString noString;
    private final String repeatedString = "This string is purely for test purposes.";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        ByteBuffer bb = ByteBuffer.allocate(42);
        bb.put((byte) 0x01);
        bb.put((byte) 0x02);
        bb.put(repeatedString.getBytes("Cp500"));
        Parameters params = new Parameters(bb.array(), "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.REPEAT_STRING;
        int length = 44;

        boolean isChained = false;

        sut = new RepeatString(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);

        // Test that when the control sequence length is < the length of the string, the string is
        // truncated
        length = 6;
        params.skipTo(0);
        noString = new RepeatString(expectedCsId, length, isChained, params);
    }

    @Test
    public void testGetterMethods() throws UnsupportedEncodingException {
        assertEquals(0x102, sut.getRepeatLength());
        assertEquals(repeatedString, sut.getRepeatDataString("Cp500"));
        String expectedString = "RepeatString=\"" + repeatedString + " to fill 258bytes";
        assertEquals(expectedString, sut.getValueAsString());

        assertEquals(258, sut.getRepeatLength());
        assertEquals("Th", noString.getRepeatDataString("Cp500"));
    }
}
