package shaurma_house.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import shaurma_house.model.Order;
import shaurma_house.model.User;


@Repository
public interface JpaOrderRepository extends CrudRepository<Order, Long> {
	
	List<Order> findByUserOrderByPlacedAtDesc (User user, Pageable pagenable);
	
	
}
