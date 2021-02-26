package com.skel.pro.common.genre.status;

public enum GenreCode {

    ACTION("액션"),
    SF("SF"),
    DRAMA("드라마");

    private String desc;

    GenreCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
