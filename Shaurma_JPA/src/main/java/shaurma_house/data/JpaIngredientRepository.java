package shaurma_house.data;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import shaurma_house.model.Ingredient;

@Repository
public interface JpaIngredientRepository extends CrudRepository<Ingredient, String> {

	    Iterable<Ingredient> findAll();
	    Optional<Ingredient> findById(String id);
	   
	
}
