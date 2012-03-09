package org.modica.afp.modca.structuredfields.map;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Map;
import org.modica.afp.modca.triplets.RepeatingTripletGroup;
import org.modica.afp.modca.triplets.TripletHandler;

/**
 * A handler for constructing {@link Map} type structured fields.
 */
public final class MapObjectHandler {

    private MapObjectHandler() {
    }

    public static StructuredField handle(StructuredFieldIntroducer intro, Parameters params, Context context) {
        try {
            StructuredField sf;
            RepeatingTripletGroup tripletGroup;
            switch (intro.getType().getCategoryCode()) {
            case coded_font:
                tripletGroup = TripletHandler.parseRepeatingGroup(params, context);
                sf = new MapCodedFont(intro, tripletGroup);
                break;
            case image:
                tripletGroup = TripletHandler.parseRepeatingGroup(params, context);
                sf = new MapImageObject(intro, tripletGroup);
                break;
            default:
                sf = null;
            }
            return sf;
        } catch (Exception uee) {
            throw new IllegalStateException(uee);
        }
    }
}