package com.skel.pro.common.movieCountry.entity;

import com.skel.pro.common.country.entity.Country;
import com.skel.pro.common.movie.entity.Movie;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "movie_country")
@ToString(exclude = {"movie", "country"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieCountryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    protected MovieCountry(Movie movie, Country country){
        this.movie = movie;
        movie.addMovieCountries(this);
        this.country = country;
        country.addMovieCountries(this);
    }

    public static MovieCountry createMovieCountry(Movie movie, Country country){
        MovieCountry movieCountry = new MovieCountry(movie, country);
        return movieCountry;
    }

}
