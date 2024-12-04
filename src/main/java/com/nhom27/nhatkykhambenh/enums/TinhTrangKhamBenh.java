package com.nhom27.nhatkykhambenh.enums;

public enum TinhTrangKhamBenh {
    NHAPVIEN("Nhập viện"),
    ME("Mẹ");

    private final String value;

    TinhTrangKhamBenh(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
