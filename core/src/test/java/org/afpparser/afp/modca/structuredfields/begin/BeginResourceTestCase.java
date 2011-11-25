package org.afpparser.afp.modca.structuredfields.begin;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link BeginResource}.
 */
public class BeginResourceTestCase extends StructuredFieldWithTripletsTestCase<BeginResource> {

    private BeginResource sut;

    @Before
    public void setUp() throws UnsupportedEncodingException, MalformedURLException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Begin.BRS);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        sut = new BeginResource(intro, triplets, "TESTNAME".getBytes("Cp500"));
        setMembers(sut, intro, triplets);
    }

    @Test
    public void testGetterMethod() {
        assertEquals("TESTNAME", sut.getRSName());
    }
}
