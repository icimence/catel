package com.example.hotel.dto;

import lombok.Data;

import java.util.List;

@Data
public class DtoReservePersonal extends DtoReserve {
    private long roomId;
    private List<Integer> residents;
}
