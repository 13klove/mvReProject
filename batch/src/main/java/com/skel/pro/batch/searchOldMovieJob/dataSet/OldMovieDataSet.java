package com.skel.pro.batch.searchOldMovieJob.dataSet;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class OldMovieDataSet {

    private List<String> oldMovies = Lists.newArrayList();

}
