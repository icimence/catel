package tech.pinto.catel.hotel;

public enum HotelStar {
    Five,
    Four,
    Three;

    public static HotelStar from(int s) {
        switch (s) {
            case 3:
                return Three;
            case 4:
                return Four;
            case 5:
                return Five;
            default:
                return null;
        }
    }
}
