package shaurma_house.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import shaurma_house.data.JpaIngredientRepository;
import shaurma_house.model.Ingredient;

import org.springframework.core.convert.converter.Converter;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
 
	private JpaIngredientRepository ingredientRepo;
	
	@Autowired
	public IngredientByIdConverter(JpaIngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
 
	@Override
	public Ingredient convert(String id) {
		return ingredientRepo.findById(id).orElse(null);
	}
	 
}
