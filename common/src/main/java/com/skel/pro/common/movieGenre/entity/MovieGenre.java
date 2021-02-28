package com.skel.pro.common.movieGenre.entity;

import com.skel.pro.common.genre.entity.Genre;
import com.skel.pro.common.movie.entity.Movie;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "movie_genre")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieGenreId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    protected MovieGenre(Movie movie, Genre genre){
        this.movie = movie;
        movie.addMovieGenre(this);
        this.genre = genre;
        genre.addMovieGenre(this);
    }

    public static MovieGenre createMovieCountry(Movie movie, Genre genre){
        MovieGenre movieGenre = new MovieGenre(movie, genre);
        return movieGenre;
    }

}
