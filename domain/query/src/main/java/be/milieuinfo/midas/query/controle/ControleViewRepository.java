package be.milieuinfo.midas.query.controle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 * @since 25/06/14
 */
@Repository
@RepositoryRestResource(path = "controles",
        itemResourceRel = "controle",
        collectionResourceRel = "controles")
public interface ControleViewRepository extends JpaRepository<ControleView, String> {

    @Override
    @RestResource(exported = false)
    <S extends ControleView> S save(S entity);

    @Override
    @RestResource(exported = false)
    void delete(ControleView entity);

    @Override
    @RestResource(exported = false)
    void delete(String id);

}
