package tech.pinto.catel.enums;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsableRoom {
    private long roomId;
    private LocalDate date;
}
