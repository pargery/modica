package org.afpparser.afp.ptoca;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.Parameters;
import org.junit.Before;
import org.junit.Test;

public class TransparentDataTestCase extends ControlSequenceTestCase<TransparentData> {

    private TransparentData sut;
    private final String testString = "This is a string purely used for test purposes.";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        Parameters params = new Parameters(testString.getBytes("Cp500"), "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.TRANSPARENT_DATA;
        int length = 49;
        boolean isChained = true;

        sut = new TransparentData(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() throws UnsupportedEncodingException {
        assertEquals(testString, sut.getDataString("Cp500"));
        assertEquals("\"" + testString + "\"", sut.getValueAsString());
    }
}
