package com.skel.pro.batch.domain.actor.repository;

import com.skel.pro.common.actor.entity.Actor;

import java.util.List;

public interface ActorCustomJpaRepository {

    public List<Actor> findByNameAndRoleIn(List<Actor> actors);

}
