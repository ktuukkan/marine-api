public class ShipType {

    private static final String[] SPECIAL = {
            "Pilot vessel",
            "Search and rescue vessel",
            "Tug",
            "Port tender",
            "Vessel with anti-pollution capability",
            "Law enforcement vessel",
            "Spare - for local vessels",
            "Spare - for local vessels",
            "Medical transport",
            "Ship not party to an armed conflict"
    };

    private static final String[] FIRST_DIGIT = {
            "Not specified",
            "Reserved for future use",
            "WIG",
            "Vessel",
            "HSC",
            "See above",
            "Passenger ship",
            "Cargo ship",
            "Tanker",
            "Other types of ship"
    };

    private static final String[] SECOND_DIGIT = {
            "General",
            "Category X",
            "Category Y",
            "Category Z",
            "Category OS",
            "Reserved for future use",
            "Reserved for future use",
            "Reserved for future use",
            "Reserved for future use",
            "No additional information"
    };

    private static final String[] VESSEL = {
            "Fishing",
            "Towing",
            "Towing and exceeding",
            "Engaged in dredging or underwater operations",
            "Engaged in diving operations",
            "Engaged in military operations",
            "Sailing",
            "Pleasure craft",
            "Reserved for future use",
            "Reserved for future use"
    };

    /**
     * Return string describing the ship and cargo type.
     *
     * @param type Ship and cargo type indicator
     * @return a text string describing the ship and cargo type.
     */
    public static String shipTypeToString(int type) {
        if (isValidType(type)) {
            int d1 = type / 10;
            int d2 = type % 10;

            switch (d1) {
                case 0:
                    return FIRST_DIGIT[0] + " " + Integer.toString(type);
                case 3:
                    return FIRST_DIGIT[3] + ", " + VESSEL[d2];
                case 5:
                    return SPECIAL[d2];
                default:
                    return FIRST_DIGIT[d1] + ", " + SECOND_DIGIT[d2];
            }
        }
        return "N/A";
    }

    /**
     * Returns a string describing the first digit. Describes the type of vessel.
     *
     * @param type Ship and cargo type indicator
     * @return String a text string describing the ship type digit
     */
    public static String shipTypeToVesselString(int type) {
        if (isValidType(type)) {
            int d1 = type / 10;
            int d2 = type % 10;

            switch (d1) {
                case 0:
                    return FIRST_DIGIT[0] + " " + Integer.toString(type);
                case 3:
                    return FIRST_DIGIT[3];
                case 5:
                    return SPECIAL[d2];
                default:
                    return FIRST_DIGIT[d1];
            }
        }
        return "N/A";
    }

    /**
     * Returns a string describing the second digit. Usually describes the type of cargo being carried,
     * but may also describe the activity the vessel is engaged in.
     *
     * @param type Ship and cargo type indicator
     * @return String a text string describing the cargo type digit
     */
    public static String shipTypeToCargoString(int type) {
        if (isValidType(type)) {
            int d1 = type / 10;
            int d2 = type % 10;

            switch (d1) {
                case 0:
                    return FIRST_DIGIT[0] + " " + Integer.toString(type);
                case 3:
                    return VESSEL[d2];
                case 5:
                    return SPECIAL[d2];
                default:
                    return SECOND_DIGIT[d2];
            }
        }
        return "N/A";
    }

    private static boolean isValidType(int type) {
        return 0 <= type && type <= 255 && type < 100 || (type >= 100 && type < 200) || type >= 200 && type < 256;
    }
}
