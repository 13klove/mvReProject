package com.skel.pro.common.movie.status;

public enum Level {

    LV0("확인불가", "00"),
    Lv1("전체이용가", "0"),
    Lv2("12세이용가", "12"),
    Lv3("15세이용가", "15"),
    Lv4("18세이용가", "18");

    private String desc;
    private String code;

    Level(String desc, String code) {
        this.desc = desc;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
