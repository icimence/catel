package tech.pinto.catel.order.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DtoReservePersonal extends DtoReserve {
    private long configId;
    private List<Long> residents;
}
