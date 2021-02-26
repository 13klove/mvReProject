package com.skel.pro.common.genre.status;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenreCodeDiv {

    private Map<String, GenreCode> genereCodeIdx = Arrays.stream(GenreCode.values()).collect(Collectors.toMap(GenreCode::getDesc, Function.identity()));

    public GenreCode getGenreCode(String value){
        return genereCodeIdx.get(value);
    }

}
