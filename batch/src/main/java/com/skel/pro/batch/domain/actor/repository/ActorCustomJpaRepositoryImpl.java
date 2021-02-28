package com.skel.pro.batch.domain.actor.repository;

import com.skel.pro.common.actor.entity.Actor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ActorCustomJpaRepositoryImpl implements ActorCustomJpaRepository{

    private final EntityManager entityManager;

    @Override
    public List<Actor> findByNameAndRoleIn(List<Actor> actors){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select a from Actor a where (a.name, a.role) in ");
        stringBuilder.append("( ");
        for(int i=0; i<actors.size(); i++) {
            if(i!=(actors.size()-1)) stringBuilder.append("('" + actors.get(i).getName() + "', '" + actors.get(i).getRole() + "'),");
            else stringBuilder.append("('" + actors.get(i).getName() + "', '" + actors.get(i).getRole() + "')");
        }
        stringBuilder.append(" )");
        return entityManager.createQuery(stringBuilder.toString(), Actor.class).getResultList();
    }

}
