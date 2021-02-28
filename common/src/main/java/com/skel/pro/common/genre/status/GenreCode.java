package com.skel.pro.common.genre.status;

public enum GenreCode {

    ADVENTURE("어드벤처"),
    FEAR("공포"),
    FANTASY("판타지"),
    DOCUMENTARY("다큐멘터리"),
    FAMILY("가족"),
    WAR("전쟁"),
    PERIOD_DRAMA("시대극"),
    ETC("기타"),
    CRIME("범죄"),
    ANIMATION("애니메이션"),
    MUSICAL("뮤지컬"),
    MARTIAL_ARTS("무협"),
    THRILLER("스릴러"),
    MYSTERY("미스터리"),
    SHOW("공연"),
    ACTION("액션"),
    COMEDY("코미디"),
    ROMANCE("로맨스"),
    SF("SF"),
    DRAMA("드라마"),
    MELLOW("멜로"),
    WESTERN("서부"),
    ADULT("성인");

    private String desc;

    GenreCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
