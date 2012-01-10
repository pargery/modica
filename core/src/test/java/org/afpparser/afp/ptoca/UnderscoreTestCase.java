package org.afpparser.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

public class UnderscoreTestCase extends ControlSequenceTestCase<Underscore> {

    private Underscore sut;

    @Before
    public void setUp() {
        Parameters params = new Parameters(ByteUtils.createByteArray(0), "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.UNDERSCORE;
        int length = 3;
        boolean isChained = true;

        sut = new Underscore(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
}

    @Test
    public void testGetterMethods() {
        assertEquals(false, sut.bypassRelativeMoveInline());
        assertEquals(false, sut.bypassAbsoluteMoveInline());
        assertEquals(false, sut.bypassSpaceChars());
        String expected = "BypassRMI=" + false
                + " BypassAMI=" + false
                + " BypassSpaceChars=" + false;
        assertEquals(expected, sut.getValueAsString());
    }
}