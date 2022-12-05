package shaurma_house.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import shaurma_house.model.Shaurma;


@Repository
public interface JpaShaurmaRepository extends CrudRepository<Shaurma, Long> {

}
