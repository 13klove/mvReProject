package com.skel.pro.batch.movieDetailDataJob.dto;

import com.skel.pro.common.actor.status.ActorRole;
import lombok.Data;

import java.util.Objects;

@Data
public class Actor {

    private String actorImg;

    private ActorRole role;

    private String name;

    public Actor(String actorImg, String role, String name) {
        this.actorImg = actorImg;
        this.role = role.equals("감독")?ActorRole.DIRECTOR:ActorRole.ACTOR;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return role == actor.role && Objects.equals(name, actor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, name);
    }

}
