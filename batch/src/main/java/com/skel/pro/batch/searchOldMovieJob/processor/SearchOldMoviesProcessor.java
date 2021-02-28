package com.skel.pro.batch.searchOldMovieJob.processor;

import com.skel.pro.batch.domain.actor.repository.ActorJpaRepository;
import com.skel.pro.batch.domain.country.repository.CountryJpaRepository;
import com.skel.pro.batch.domain.genre.repository.GenreJpaRepository;
import com.skel.pro.common.actor.entity.Actor;
import com.skel.pro.common.country.entity.Country;
import com.skel.pro.common.country.status.CountryCodeDiv;
import com.skel.pro.common.genre.entity.Genre;
import com.skel.pro.common.genre.status.GenreCodeDiv;
import com.skel.pro.common.movie.entity.Movie;
import com.skel.pro.common.movieActor.entiry.MovieActor;
import com.skel.pro.common.movieCountry.entity.MovieCountry;
import com.skel.pro.common.movieGenre.entity.MovieGenre;
import com.skel.pro.util.crw.CrwVo;
import com.skel.pro.util.crw.du.DuMovieDetailParser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.batch.item.ItemProcessor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SearchOldMoviesProcessor implements ItemProcessor<String, Movie> {

    private final CloseableHttpClient httpClient;
    private final ActorJpaRepository actorJpaRepository;
    private final GenreJpaRepository genreJpaRepository;
    private final CountryJpaRepository countryJpaRepository;
    private final CountryCodeDiv countryCodeDiv;
    private final GenreCodeDiv genreCodeDiv;

    @Override
    public Movie process(String s) throws Exception {
        CrwVo detailCrwVo = CrwVo.builder().input(s).build();
        DuMovieDetailParser duMovieDetailParser = new DuMovieDetailParser(httpClient);
        duMovieDetailParser.read(detailCrwVo);

        Map<String, Object> movie = (Map<String, Object>) detailCrwVo.getOutput();
        if(movie!=null){
            Movie movieDetail = Movie.createCloseMovieDetail(movie);

            String countrys[] = (String[]) MapUtils.getObject(movie, "country");
            if(countrys!=null) {
                List<Country> jpaCountry = countryJpaRepository.findByCountryIn(Arrays.stream(countrys).map(a -> countryCodeDiv.getCountryCode(a)).collect(Collectors.toList()));
                jpaCountry.forEach(a -> MovieCountry.createMovieCountry(movieDetail, a));
            }


            String genre[] = (String[]) MapUtils.getObject(movie, "genre");
            if(genre!=null){
                List<Genre> jpaGenre = genreJpaRepository.findByGenreIn(Arrays.stream(genre).map(a->genreCodeDiv.getGenreCode(a)).collect(Collectors.toList()));
                jpaGenre.forEach(a-> MovieGenre.createMovieCountry(movieDetail, a));
            }


            List<Map<String, String>> actors = (List<Map<String, String>>) MapUtils.getObject(movie, "actors");
            List<Actor> actorList = actors.stream().map(a -> Actor.createActor(MapUtils.getString(a, "name"), MapUtils.getString(a, "role"))).collect(Collectors.toList());
            List<Actor> jpaActors = actorJpaRepository.findByNameAndRoleIn(actorList);
            jpaActors.forEach(a-> MovieActor.createMovieActor(movieDetail, a));

            return movieDetail;
        }

        return Movie.createMovieByTitle(null);
    }

}
