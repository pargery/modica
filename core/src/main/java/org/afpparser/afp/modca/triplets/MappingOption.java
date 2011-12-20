package org.afpparser.afp.modca.triplets;

import java.util.HashMap;
import java.util.Map;


/**
 * The Mapping Option is used to specify the mapping of a data object presentation space to an
 * object area.
 */
public class MappingOption extends Triplet {
    private static final int LENGTH = 3;
    private final MapValue mapValue;

    /**
     * Specifies the mapping option to be used for the data object referenced by the structured
     * field.
     */
    public enum MapValue {
        /**
         * Position. The upper left corner of the data object’s presentation space or window is
         * positioned coincident with the data object’s content origin specified in the XocaOset and
         * YocaOset parameters in the Object Area Position structured field. All data must be
         * presented within the object area extents, or a X’01’ exception condition exists.
         */
        Position(0x00),
        /**
         * Position and trim. The upper left corner of the data object’s presentation space or
         * window is positioned coincident with the data object’s content origin specified in the
         * XocaOset and YocaOset parameters in the Object Area Position structured field. All data
         * that falls within the object area extents is presented, but data that falls outside of
         * the object area is not presented.
         */
        PositionAndTrim(0x10),
        /**
         * Scale to fit. The center of the data object’s presentation space or window is mapped to
         * the center of the object area defined by the associated Object Area Descriptor structured
         * field. The data object is symmetrically scaled up or down while preserving the aspect
         * ratio so that, at its maximum data size, it is totally contained in the object area. When
         * this option is specified, the data object’s content origin specified in the XocaOset and
         * YocaOset parameters in the Object Area Position structured field is ignored.
         */
        ScaleToFit(0x20),
        /**
         * Center and trim. The center of the data object’s presentation space or window is mapped
         * to the center of the object area defined by the associated Object Area Descriptor
         * structured field. All data that falls within the object area is presented, but data that
         * falls outside of the object area is not presented.
         * <p>
         * When this option is specified, the data object’s content origin specified in the
         * XocaOset and YocaOset parameters in the Object Area Position structured field is ignored.
         * </p>
         */
        CenterAndTrim(0x30),
        /**
         * This mapping is supported for IOCA FS10 for the migration of IM image objects. It
         * provides a mapping for the IOCA FS10 image object similar to the mapping defined for the
         * IM image object. The origin of the IOCA FS10 presentation space is positioned at the
         * origin of the object area. Each image point in the presentation space is mapped to a
         * presentation device pel. Any portion of the image that falls outside the object area is
         * trimmed.
         */
        ImagePointToPel(0x41),
        /**
         * This mapping is supported for IOCA FS10 for the migration of IM image objects. It
         * provides a mapping for the IOCA FS10 image object similar to that defined for the IM
         * image object. The origin of the IOCA FS10 presentation space is positioned at the origin
         * of the object area. Each image point in the presentation space is doubled in both
         * directions, resulting in four new image points. The four new image points are then mapped
         * to presentation device pels. Any portion of the image that falls outside the object area
         * is trimmed.
         */
        ImagePointToPelWithDoubleDot(0x42),
        /**
         * This mapping is supported for IOCA FS10 for the migration of IM image objects. It
         * provides a function for the IOCA FS10 image object similar to that defined for the celled
         * IM image object. The IOCA FS10 presentation space is positioned in the object area so
         * that its origin is coincident with the origin of the object area and its size is
         * unchanged. The presentation space is then replicated in the X and Y directions of the
         * object area until the object area is filled. Each new replicate of the presentation space
         * in the X direction is precisely aligned with the presentation space previously placed in
         * the X direction. Each new replicate of the presentation space in the Y direction is
         * precisely aligned with the presentation space previously placed in the Y direction. If
         * the last presentation space in either the X or Y direction fits only partially into the
         * object area, the portion of the presentation space that falls outside the object area is
         * trimmed. All data that falls within the object area extents is presented, but data that
         * falls outside of the object area is not presented. When this option is specified, the
         * data object’s content origin specified in the XocaOset and YocaOset parameters in the
         * Object Area Position structured field is ignored.
         */
        ReplicateAndTrim(0x50),
        /**
         * Scale to fill. The center of the data object’s presentation space or window is mapped to
         * the center of the object area defined by the associated Object Area Descriptor structured
         * field. The data object is scaled up or down so that it totally fills the object area in
         * both the X and Y directions. This may require that the object presentation space be
         * asymmetrically scaled by different scale factors in the X and Y directions. Therefore,
         * this mapping does not, in general, preserve the the aspect ratio of the data object.
         * <p>
         * When this option is specified, the data object’s content origin specified in the XocaOset
         * and YocaOset parameters in the Object Area Position structured field is ignored.
         * </p>
         */
        ScaleToFill(0x60),
        /**
         * UP3i Print Data mapping. This mapping is only used to map UP3i Print Data objects. The
         * specific mapping function is defined by the UP3i Print Data format, which is identified
         * by the Print Data Format ID that is specified in the first 4 bytes of the UP3i Print Data
         * object. For a definition of UP3i Print Data formats, see the UP3i specification available
         * at the UP3i web site at www.up3i.org
         */
        UP3iPrintDataMapping(0x70);
        private final byte value;

        private final static Map<Byte, MapValue> CACHE = new HashMap<Byte, MappingOption.MapValue>();
        static {
            for (MapValue mv : MapValue.values()) {
                CACHE.put(mv.getValue(), mv);
            }
        }

        /**
         * Default constructor.
         * @param value the integer map value
         */
        private MapValue(int value) {
            this.value = (byte) value;
        }

        /**
         * Returns the byte value for this MapValue.
         * @return the byte value
         */
        public byte getValue() {
            return value;
        }

        /**
         * Converts a byte into the associated MapValue object, if byte is given that doesn't
         * correspond to a map value, null is returned.
         *
         * @param mvByte the MapValue byte
         * @return the MapValue object
         */
        public static MapValue getMapValue(byte mvByte) {
            MapValue mv = CACHE.get(mvByte);
            assert mv != null;
            return mv;
        }
    }

    public MappingOption(byte mapValue) {
        this.mapValue = MapValue.getMapValue(mapValue);
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    /**
     * Get the map value.
     *
     * @return the map value
     */
    public MapValue getMapvalue() {
        return mapValue;
    }

    @Override
    public TripletIdentifiers getTid() {
        return TripletIdentifiers.mapping_option;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MappingOption)) {
            return false;
        }
        MappingOption obj = (MappingOption) o;
        return this.mapValue == obj.mapValue;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + mapValue.hashCode();
        hashCode = 31 * hashCode + getTid().hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return getTid().name() + " mapValue=" + mapValue;
    }

    @Override
    public String valueToString() {
        return "MapValue=" + mapValue.toString();
    }
}
