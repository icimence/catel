package com.example.hotel.dto;

import lombok.Data;

import java.util.List;

@Data
public class DtoReserveGroup extends DtoReserve {
    private int peoplePerRoom;
    private long residentGroupId;
    private List<Integer> preferredRoomId;
}
