package tech.pinto.catel.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class DtoReserveGroup extends DtoReserve {
    private int peoplePerRoom;
    private long residentGroupId;
    private List<Integer> preferredRoomId;
}
