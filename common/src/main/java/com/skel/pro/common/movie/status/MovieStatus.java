package com.skel.pro.common.movie.status;

public enum MovieStatus {

    OPEN("상영"),
    CLOSE("종료"),
    RE_OPEN("재상영");

    private String desc;

    MovieStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
