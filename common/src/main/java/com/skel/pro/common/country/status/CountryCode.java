package com.skel.pro.common.country.status;

public enum CountryCode {

    USA("미국"),
    KOR("한국"),
    FRA("프랑스");

    private String desc;

    CountryCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
