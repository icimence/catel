package tech.pinto.catel.vo.user;

import tech.pinto.catel.enums.VipType;
import lombok.Data;

@Data
public class VipForm {

    int userId;
    int day;
    VipType vipType;

}
