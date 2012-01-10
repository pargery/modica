package org.afpparser.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.Parameters;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link SetInlineMargin}.
 */
public class SetInlineMarginTestCase extends ControlSequenceTestCase<SetInlineMargin> {

    private SetInlineMargin sut;

    @Before
    public void setUp() {
        Parameters params = new Parameters(new byte[] { 0x7F, 0x00 }, "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.SET_INLINE_MARGIN;
        int length = 4;
        boolean isChained = true;

        sut = new SetInlineMargin(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x7F00, sut.getDisplacement());

        assertEquals("move 32512", sut.getValueAsString());
    }
}
