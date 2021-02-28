package com.skel.pro.common.genre.entity;

import com.google.common.collect.Lists;
import com.skel.pro.common.genre.status.GenreCode;
import com.skel.pro.common.genre.status.GenreCodeDiv;
import com.skel.pro.common.movieGenre.entity.MovieGenre;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "genre")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;

    @Enumerated(EnumType.STRING)
    private GenreCode genre;

    @OneToMany(mappedBy = "genre")
    private List<MovieGenre> movieGenres = Lists.newArrayList();

    protected Genre(String genre){
        GenreCodeDiv genreCodeDiv = new GenreCodeDiv();
        this.genre = genreCodeDiv.getGenreCode(genre);
    }

    public void addMovieGenre(MovieGenre movieGenre){
        movieGenres.add(movieGenre);
    }

}
