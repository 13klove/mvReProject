package com.skel.pro.batch.domain.genre.repository;

import com.skel.pro.common.genre.entity.Genre;
import com.skel.pro.common.genre.status.GenreCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreJpaRepository extends JpaRepository<Genre, Long> {

    public List<Genre> findByGenreIn(List<GenreCode> genres);

}
