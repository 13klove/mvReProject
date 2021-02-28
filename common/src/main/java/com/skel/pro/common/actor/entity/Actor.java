package com.skel.pro.common.actor.entity;

import com.google.common.collect.Lists;
import com.skel.pro.common.actor.status.ActorRole;
import com.skel.pro.common.movieActor.entiry.MovieActor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "actor")
@ToString(exclude = {"movieActors"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actorId;

    private String actorImg;

    @Enumerated(EnumType.STRING)
    private ActorRole role;

    private String name;

    @OneToMany(mappedBy = "actor")
    private List<MovieActor> movieActors = Lists.newArrayList();

    protected Actor(String name, String role){
        this.name = name;
        this.role = role.equals("감독")?ActorRole.DIRECTOR:ActorRole.ACTOR;
    }

    public static Actor createActor(String name, String role){
        return new Actor(name, role);
    }

    public void addMovieActor(MovieActor movieActor) {movieActors.add(movieActor);}

}
