package tech.pinto.catel.enums;

import lombok.Getter;

@Getter
public enum VipLevel {
    Nil(2),
    Small(4),
    Big(8);

    private final int annulLimit;

    VipLevel(int annulLimit) {
        this.annulLimit = annulLimit;
    }

}
