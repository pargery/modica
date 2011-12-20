package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;

/**
 * The End Presentation Text Object structured field terminates the current presentation text object
 * initiated by a Begin Presentation Text Object structured field.
 */
public class EndPresentationTextObject extends StructuredFieldWithTriplets {

    private final EndFieldName pTdoName;

    public EndPresentationTextObject(SfIntroducer introducer, List<Triplet> triplets,
            Parameters params) throws UnsupportedEncodingException {
        super(introducer, triplets);
        pTdoName = new EndFieldName(params);
    }

    /**
     * Returns the name of the presentation text data object that is being terminated. If a name is
     * specified, it must match the name in the most recent Begin Presentation Text Object
     * structured field in the page, or overlay, or a X’01’ exception condition exists. If the first
     * two bytes of PTdoName contain the value X'FFFF', the name matches any name specified on the
     * Begin Presentation Text Object structured field that initiated the current definition.
     *
     * @return the Presentation Text Object that this object ends
     */
    public String getPTdoName() {
        return pTdoName.getName();
    }

    /**
     * Whether or not the name of this object matches any name in the BeginPresentationTextObject.
     *
     * @return true if the name matches any
     */
    public boolean nameMatchesAny() {
        return pTdoName.matchesAny();
    }

    @Override
    public String toString() {
        return getType().toString() + " PTdoName=" + pTdoName;
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("PresentationTextObjectName", getPTdoName());
        return params;
    }
}
