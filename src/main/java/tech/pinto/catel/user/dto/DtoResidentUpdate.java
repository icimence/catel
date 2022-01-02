package tech.pinto.catel.user.dto;

import lombok.Data;

@Data
public class DtoResidentUpdate {
    private long residentId;
    private String name;
    private String phoneNumber;
}
