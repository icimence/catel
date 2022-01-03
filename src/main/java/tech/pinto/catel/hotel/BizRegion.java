package tech.pinto.catel.hotel;

import tech.pinto.catel.util.error.EnumOutRange;

public enum BizRegion {
    玄武,
    建邺,
    江宁,
    雨花台,
    栖霞,
    鼓楼,
    秦淮;

    public static BizRegion from(String bizStr) throws EnumOutRange {
        switch (bizStr) {
            case "玄武":
                return 玄武;
            case "建邺":
                return 建邺;
            case "江宁":
                return 江宁;
            case "雨花台":
                return 雨花台;
            case "栖霞":
                return 栖霞;
            case "秦淮":
                return 秦淮;
            case "鼓楼":
                return 鼓楼;
            default:
                throw new EnumOutRange(BizRegion.class.getName(), bizStr);
        }
    }
}
