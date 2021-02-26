package com.skel.pro.common.country.status;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CountryCodeDiv {

    private Map<String, CountryCode> countryCodeIdx = Arrays.stream(CountryCode.values()).collect(Collectors.toMap(CountryCode::getDesc, Function.identity()));

    public CountryCode getCountryCode(String value){
        return countryCodeIdx.get(value);
    }

}
