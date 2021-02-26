package com.skel.pro.common.actor.entity;

import com.google.common.collect.Lists;
import com.skel.pro.common.actor.status.ActorRole;
import com.skel.pro.common.movieActor.entiry.MovieActor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "actor")
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

}
