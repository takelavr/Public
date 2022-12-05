package shaurma_house.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import shaurma_house.model.ShaurmaOrder;

@Repository
public interface JpaOrderRepository extends CrudRepository<ShaurmaOrder, Long> {
	
}
