package com.skel.pro.common.country.entity;

import com.google.common.collect.Lists;
import com.skel.pro.common.country.status.CountryCode;
import com.skel.pro.common.country.status.CountryCodeDiv;
import com.skel.pro.common.movieCountry.entity.MovieCountry;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "country")
@ToString(exclude = {"movieCountries"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    @Enumerated(EnumType.STRING)
    private CountryCode country;

    @OneToMany(mappedBy = "country")
    private List<MovieCountry> movieCountries = Lists.newArrayList();

    protected Country(String country){
        CountryCodeDiv countryCodeDiv = new CountryCodeDiv();
        this.country = countryCodeDiv.getCountryCode(country);
    }

    public static Country createCountry(String country){
        return new Country(country);
    }

    public void addMovieCountries(MovieCountry movieCountry){
        this.movieCountries.add(movieCountry);
    }

}
