package tech.pinto.catel.util;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class CsvHotel {
    @CsvBindByName(column = "酒店名称")
    private String name;
    @CsvBindByName(column = "星级")
    private int star;
    @CsvBindByName(column = "地址")
    private String address;
    @CsvBindByName(column = "酒店描述")
    private String desc;
}
