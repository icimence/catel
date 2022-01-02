package tech.pinto.catel.user.dto;

import lombok.Data;

@Data
public class DtoUserInfo {
    private long id;
    private String email;
    private String username;
    private double credit;
    private String userType;
    private String vipType;
    private String avatar;
}
