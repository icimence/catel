package tech.pinto.catel.room.dto;

import lombok.Data;

@Data
public class DtoConfigInfo {
    private Integer id;
    private String roomType;
    private Double price;
    private Integer total;
    private Boolean breakfast;
    private Integer peopleMax;
}
