package com.skel.pro.batch.domain.country.repository;

import com.skel.pro.common.country.entity.Country;
import com.skel.pro.common.country.status.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryJpaRepository extends JpaRepository<Country, Long> {

    public List<Country> findByCountryIn(List<CountryCode> country);

}
