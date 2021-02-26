package com.skel.pro.common.movie.entity;

import com.google.common.collect.Lists;
import com.skel.pro.common.movie.status.Level;
import com.skel.pro.common.movie.status.MovieStatus;
import com.skel.pro.common.movieActor.entiry.MovieActor;
import com.skel.pro.common.movieCountry.entity.MovieCountry;
import com.skel.pro.common.movieGenre.entity.MovieGenre;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.MapUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Getter
@Entity
@Table(name = "movie")
@ToString(exclude = {"movieGenres", "movieCountries", "movieActors"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    private String title;

    private String titleEn;

    private String bannerImg;

    @Enumerated(EnumType.STRING)
    private Level level;

    private String runtime;

    private LocalDate openDate;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieGenre> movieGenres = Lists.newArrayList();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieCountry> movieCountries = Lists.newArrayList();

    private Double point;

    private Long count;

    private String award;

    private String story;

    private MovieStatus movieStatus;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieActor> movieActors = Lists.newArrayList();

    public static Movie createMovieByTitle(String title){
        Movie movie = new Movie();
        movie.changeTitle(title);
        return movie;
    }

    public static Movie createCloseMovieDetail(Map<String, Object> param){
        Movie movie = new Movie();
        movie.movieDataUpdate(MapUtils.getString(param, "title"), MapUtils.getString(param, "titleEn"), MapUtils.getString(param, "level")
                , MapUtils.getString(param, "banner"), MapUtils.getString(param, "runtime"), MapUtils.getString(param, "openDate")
                , MapUtils.getDouble(param, "point"), MapUtils.getLong(param, "count"), MapUtils.getString(param, "award"), MapUtils.getString(param, "story"), MovieStatus.CLOSE);
        return movie;
    }

    private void movieDataUpdate(String title, String titleEn, String level, String bannerImg, String runtime, String openDate, Double point, Long count, String award, String story, MovieStatus movieStatus){
        this.title = title;
        this.titleEn = titleEn;
        this.level = selectLevel(level);
        this.bannerImg = bannerImg;
        this.runtime = runtime;
        this.openDate = LocalDate.parse(openDate, DateTimeFormatter.BASIC_ISO_DATE);
        this.point = point;
        this.count = count;
        this.award = award;
        this.story = story;
        this.movieStatus = movieStatus;
    }

    public void changeTitle(String title){
        this.title = title;
    }

    public Level selectLevel(String level){
        if(level.equals(Level.Lv1.getCode())) return Level.Lv1;
        else if(level.equals(Level.Lv2.getCode())) return Level.Lv2;
        else if(level.equals(Level.Lv3.getCode())) return Level.Lv3;
        else return Level.Lv4;
    }

    public void addMovieCountries(MovieCountry movieCountry){
        movieCountries.add(movieCountry);
    }

}
