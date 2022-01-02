package tech.pinto.catel.user.dto;

import lombok.Data;

@Data
public class DtoChangePwd {
    private long id;
    private String oldPass;
    private String newPass;
}
