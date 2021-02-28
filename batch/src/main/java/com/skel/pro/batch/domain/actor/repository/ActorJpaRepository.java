package com.skel.pro.batch.domain.actor.repository;

import com.skel.pro.common.actor.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorJpaRepository extends JpaRepository<Actor, Long>, ActorCustomJpaRepository {

}
