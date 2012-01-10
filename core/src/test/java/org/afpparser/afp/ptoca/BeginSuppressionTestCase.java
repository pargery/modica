package org.afpparser.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.Parameters;
import org.junit.Before;
import org.junit.Test;

public class BeginSuppressionTestCase extends ControlSequenceTestCase<BeginSuppression> {

    private BeginSuppression sut;
    private final byte suppressionId = 0x33;

    @Before
    public void setUp() {
        Parameters params = new Parameters(new byte[] { suppressionId }, "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.BEGIN_SUPPRESSION;
        int length = 4;
        boolean isChained = true;

        sut = new BeginSuppression(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(suppressionId, sut.getLid());

        assertEquals("lid=0x33", sut.getValueAsString());
    }
}
