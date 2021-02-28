package com.skel.pro.batch.reposiroty;

import com.google.common.collect.Lists;
import com.skel.pro.batch.domain.actor.repository.ActorJpaRepository;
import com.skel.pro.common.actor.entity.Actor;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Transient;

import java.util.List;

@SpringBootTest
@EnableAutoConfiguration
@EnableBatchProcessing
public class ActorJpaRepositoryTest {

    @Autowired
    ActorJpaRepository actorJpaRepository;

    @Test
    @Transient
    public void repositoryTest(){
        List<Actor> actorList = Lists.newArrayList();
        actorList.add(Actor.createActor("여명", "감독"));
        actorList.add(Actor.createActor("키타키 마유", "배우"));
        List<Actor> actorsByNameRoleIn = actorJpaRepository.findByNameAndRoleIn(actorList);
        System.out.println(actorsByNameRoleIn);
    }

}
