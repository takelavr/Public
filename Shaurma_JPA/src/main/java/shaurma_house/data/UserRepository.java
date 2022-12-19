package shaurma_house.data;

import org.springframework.data.repository.CrudRepository;

import shaurma_house.model.User;




public interface UserRepository extends CrudRepository<User, Long> {
	 User findByUsername(String username);
}
