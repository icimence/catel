package tech.pinto.catel.util;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class CsvRoomConfig {
    @CsvBindByName(column = "酒店名称")
    private String hotelName;
    @CsvBindByName(column = "房间类型")
    private String configName;
    @CsvBindByName(column = "有无早餐")
    private String breakfast;
    @CsvBindByName(column = "每间容纳人数")
    private int peopleLimit;
    @CsvBindByName(column = "价格")
    private long price;
}
