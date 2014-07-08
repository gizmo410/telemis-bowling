package com.telemis.bowling.query.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 * @since 08/07/14
 */
@Repository
@RepositoryRestResource(path = "games",
        itemResourceRel = "game",
        collectionResourceRel = "games")
public interface GameViewRepository extends JpaRepository<GameView, String> {

    @Override
    @RestResource(exported = false)
    <S extends GameView> S save(S entity);

    @Override
    @RestResource(exported = false)
    void delete(GameView entity);

    @Override
    @RestResource(exported = false)
    void delete(String id);

}
