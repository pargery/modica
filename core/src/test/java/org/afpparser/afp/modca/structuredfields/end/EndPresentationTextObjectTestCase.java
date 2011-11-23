package org.afpparser.afp.modca.structuredfields.end;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.End;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link EndPresentationTextObject}. 
 */
public class EndPresentationTextObjectTestCase extends
        StructuredFieldWithTripletsTestCase<EndPresentationTextObject> {

    private EndPresentationTextObject sut;
    private EndPresentationTextObject sutMatchesAny;
    private final String pageName = "Testpage";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(End.EPT);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        sut = new EndPresentationTextObject(intro, triplets, pageName.getBytes("Cp500"));
        sutMatchesAny = new EndPresentationTextObject(intro, triplets, ByteUtils.createByteArray(
                0xff, 0xff));
        setMembers(sut, intro, triplets);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(pageName, sut.getPTdoName());
        assertEquals(false, sut.nameMatchesAny());

        assertEquals(null, sutMatchesAny.getPTdoName());
        assertEquals(true, sutMatchesAny.nameMatchesAny());
    }
}