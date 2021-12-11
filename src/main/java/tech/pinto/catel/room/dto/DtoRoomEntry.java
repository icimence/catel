package tech.pinto.catel.room.dto;

import lombok.Data;

@Data
public class DtoRoomEntry {
    private Long roomId;
    private long residentId;
    private double price;
    private boolean breakfast;
    private String name;
    private String type;
}
