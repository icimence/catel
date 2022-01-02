package tech.pinto.catel.vo.user;

import tech.pinto.catel.enums.VipLevel;
import lombok.Data;

@Data
public class VipForm {

    int userId;
    int day;
    VipLevel vipLevel;

}
