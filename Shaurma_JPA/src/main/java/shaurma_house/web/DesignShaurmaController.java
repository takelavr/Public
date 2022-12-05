package shaurma_house.web;

import lombok.extern.slf4j.Slf4j;
import shaurma_house.data.JpaIngredientRepository;
import shaurma_house.model.Ingredient;
import shaurma_house.model.Shaurma;
import shaurma_house.model.ShaurmaOrder;
import shaurma_house.model.Ingredient.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("shaurma")
public class DesignShaurmaController {

    private final JpaIngredientRepository ingredientRepo;

    @Autowired
    public DesignShaurmaController(JpaIngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }
    
    @GetMapping
    public String showDesignForm() {
    	return "design";
    }
    //метод обрабатывает создание Шаурмы
    @PostMapping
    public String processTaco(@Valid Shaurma shaurma, 
    						  Errors errors,
    						  @ModelAttribute ShaurmaOrder shaurmaOrder) {
    	if (errors.hasErrors()) {
    			return "design";
    		}
    	
    	shaurmaOrder.addTaco(shaurma);
    	log.info("Processing shaurma: {}", shaurma);
    	
    	return "redirect:/orders/current";
   }
    
    @ModelAttribute(name = "shaurmaOrder")
    public ShaurmaOrder order() {
    	return new ShaurmaOrder();
    }
    
    @ModelAttribute(name = "shaurma")
    public Shaurma shaurma() {
    	return new Shaurma();
    }
    
    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));
        Type[] types = Type.values();
        for (Type type: types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }    
        
    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type){
            return ingredients
                    		  .stream()
                    		  .filter(x -> x.getType().equals(type))
                    		  .collect(Collectors.toList());
    }   
    
    
}    
