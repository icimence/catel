package tech.pinto.catel.room;

public enum RoomType {
    BigBed,
    DoubleBed,
    Family,
    Business,
    Luxury,
    Tatami,
    Studio,
    Single;

    public static RoomType fromName(String name) throws Exception {
        switch (name) {
            case "双床房":
            case "标准间":
            case "套房":
            case "行政套房":
                return DoubleBed;
            case "大床房":
                return BigBed;
            case "家庭房":
            case "亲子房":
                return Family;
            case "商务套房":
                return Business;
            case "豪华套房":
                return Luxury;
            case "单人房":
                return Single;
            case "榻榻米房":
                return Tatami;
            case "影音房":
                return Studio;
            default:
                throw new Exception("不支持的房型 " + name);
        }
    }
}
