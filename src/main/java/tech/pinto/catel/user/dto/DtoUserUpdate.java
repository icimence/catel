package tech.pinto.catel.user.dto;

import lombok.Data;

@Data
public class DtoUserUpdate {
    private long id;
    private String name;
    private String email;
    private String avatar;
}
