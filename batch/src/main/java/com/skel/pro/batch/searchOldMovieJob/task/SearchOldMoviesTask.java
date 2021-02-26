package com.skel.pro.batch.searchOldMovieJob.task;

import com.skel.pro.batch.searchOldMovieJob.dataSet.OldMovieDataSet;
import com.skel.pro.util.crw.CrwVo;
import com.skel.pro.util.crw.nv.NvMoviesParser;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

/**
* @date: 2021.02.25
* @author: hb
* @desc: 상영이 끝난 영화이름만 조회
**/
@RequiredArgsConstructor
public class SearchOldMoviesTask implements Tasklet {

    public final CloseableHttpClient client;
    public final OldMovieDataSet oldMovieDataSet;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        CrwVo crwVo = CrwVo.builder().build();
        NvMoviesParser nvMoviesParser = new NvMoviesParser(client);
        nvMoviesParser.read(crwVo);
        oldMovieDataSet.setOldMovies((List<String>) crwVo.getOutput());
        System.out.println(oldMovieDataSet);
        return RepeatStatus.FINISHED;
    }

}
