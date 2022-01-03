package tech.pinto.catel.hotel;

import tech.pinto.catel.util.error.EnumOutRange;

public enum HotelStar {
    One,
    Two,
    Three,
    Four,
    Five;

    public static HotelStar from(int s) throws EnumOutRange {
        switch (s) {
            case 1:
                return One;
            case 2:
                return Two;
            case 3:
                return Three;
            case 4:
                return Four;
            case 5:
                return Five;
            default:
                throw new EnumOutRange(HotelStar.class.getName(), String.valueOf(s));
        }
    }
}
