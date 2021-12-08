package tech.pinto.catel.vo.user;

import tech.pinto.catel.enums.UserType;
import tech.pinto.catel.enums.VipType;
import lombok.Data;

@Data
public class UserVO {

    private Integer id;
    private String email;
    private String password;
    private String username;
    private Double credit;
    private UserType userType;
    private String avatar;
    private String vipEnd;
    private VipType vipType;

}
