package tech.pinto.catel.user;

import lombok.Getter;

@Getter
public enum VipLevel {
    Nil(8),
    Small(4),
    Big(2);

    private final int annulLimit;

    VipLevel(int annulLimit) {
        this.annulLimit = annulLimit;
    }

}
