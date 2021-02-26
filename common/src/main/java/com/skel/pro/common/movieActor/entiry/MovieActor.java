package com.skel.pro.common.movieActor.entiry;

import com.skel.pro.common.actor.entity.Actor;
import com.skel.pro.common.movie.entity.Movie;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "movie_actor")
public class MovieActor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieActorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private Actor actor;

}
