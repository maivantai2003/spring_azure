package com.nhom27.nhatkykhambenh.enums;

public enum MoiQuanHe {
    TOI("Tôi"),
    CHA("Cha"),
    ME("Mẹ"),
    VO("Vợ"),
    CHONG("Chồng"),
    CON("Con"),
    ANH("Anh"),
    CHI("Chị"),
    EM("Em"),
    CHAU("Cháu"),
    ONGNOI("Ông nội"),
    ONGNGOAI("Ông ngoại"),
    BANOI("Bà nội"),
    BANGOAI("Bà ngoại"),
    KHAC("Khác");

    private final String value;

    MoiQuanHe(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
