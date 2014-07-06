package be.milieuinfo.midas.query.medewerker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @since 02/07/14
 */
@Repository
@RepositoryRestResource(path = "medewerkers",
        itemResourceRel = "medewerker",
        collectionResourceRel = "medewerkers")
public interface MedewerkerViewRepository extends JpaRepository<MedewerkerView, String> {

    @Override
    @RestResource(exported = false)
    <S extends MedewerkerView> S save(S entity);

    @Override
    @RestResource(exported = false)
    void delete(MedewerkerView entity);

    @Override
    @RestResource(exported = false)
    void delete(String id);


    @Query(nativeQuery = true,
            value = "SELECT medewerker.*, ts_rank_cd(tsvector, query) AS rank " +
                    "FROM midas.medewerker_view medewerker, plainto_tsquery('dutch', :keyword) query, to_tsvector('dutch', medewerker.full_text) tsvector " +
                    "WHERE tsvector @@ query " +
                    "ORDER BY rank DESC LIMIT 100")
    List<MedewerkerView> findByKeyword(@Param("keyword") String keyword);

}
