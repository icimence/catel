package tech.pinto.catel.enums;

public enum VipType {
    Nil(2),
    Small(4),
    Big(8);

    int annulLimit;

    VipType(int annulLimit) {
        this.annulLimit = annulLimit;
    }

    public int getAnnulLimit() {
        return annulLimit;
    }
}
