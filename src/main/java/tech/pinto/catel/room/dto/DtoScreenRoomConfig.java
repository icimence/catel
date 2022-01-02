package tech.pinto.catel.room.dto;

import lombok.Data;

@Data
public class DtoScreenRoomConfig {
    private long id;
    private String inDate;
    private String outDate;
    private int roomNumber;
}
