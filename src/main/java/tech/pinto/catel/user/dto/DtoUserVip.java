package tech.pinto.catel.user.dto;

import lombok.Data;
import tech.pinto.catel.user.VipLevel;

@Data
public class DtoUserVip {
    private long id;
    private int days;
    private VipLevel level;
}
