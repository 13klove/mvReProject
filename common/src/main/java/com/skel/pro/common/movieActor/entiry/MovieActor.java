package com.skel.pro.common.movieActor.entiry;

import com.skel.pro.common.actor.entity.Actor;
import com.skel.pro.common.movie.entity.Movie;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "movie_actor")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieActor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieActorId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private Actor actor;

    protected MovieActor(Movie movie, Actor actor){
        this.movie = movie;
        movie.addMovieActor(this);
        this.actor = actor;
        actor.addMovieActor(this);
    }

    public static MovieActor createMovieActor(Movie movie, Actor actor){
        MovieActor movieActor = new MovieActor(movie, actor);
        return movieActor;
    }



}
