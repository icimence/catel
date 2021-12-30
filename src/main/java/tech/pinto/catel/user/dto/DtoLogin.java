package tech.pinto.catel.user.dto;

import lombok.Data;

@Data
public class DtoLogin {

    private String email; // or username
    private String password;

}
