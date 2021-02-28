package com.skel.pro.batch.domain.movie.repository;

import com.skel.pro.common.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieJpaRepository extends JpaRepository<Movie, Long> {
}
