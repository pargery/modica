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
 * The End Resource Group structured field terminates the definition of a resource group initiated
 * by a Begin Resource Group structured field.
 */
public class EndResourceGroup extends StructuredFieldWithTriplets {

    private final EndFieldName rGrpName;

    public EndResourceGroup(SfIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        rGrpName = new EndFieldName(params);
    }

    /**
     * Returns the name of the resource group that is being terminated. If a name is specified, it
     * must match the name in the most recent Begin Resource Group structured field in the print
     * file, document, page, or data object, or a X’01’ exception condition exists. If the first two
     * bytes of RGrpName contain the value X'FFFF', the name matches any name specified on the Begin
     * Resource Group structured field that initiated the current definition.
     *
     * @return the resource group name
     */
    public String getRGrpName() {
        return rGrpName.getName();
    }

    /**
     * Returns true if the page name should match any corresponding page name on the
     * BeginResourceGroup object.
     *
     * @return true if the page name should match any
     */
    public boolean nameMatchesAny() {
        return rGrpName.matchesAny();
    }

    @Override
    public String toString() {
        return getType().getName() + " RGrpName=" + rGrpName;
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("ResourceGroupName", getRGrpName());
        return params;
    }
}
