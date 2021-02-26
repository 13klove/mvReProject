package com.skel.pro.common.actor.status;

public enum ActorRole {

    ACTOR("배우"),
    DIRECTOR("감독")
    ;

    private String desc;

    ActorRole(String desc) {
        this.desc = desc;
    }

}
