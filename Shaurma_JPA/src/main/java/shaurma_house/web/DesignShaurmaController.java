package shaurma_house.web;

import lombok.extern.slf4j.Slf4j;
import shaurma_house.data.JpaIngredientRepository;
import shaurma_house.data.JpaShaurmaRepository;
import shaurma_house.model.Ingredient;
import shaurma_house.model.Order;
import shaurma_house.model.Shaurma;
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
    private JpaShaurmaRepository shaurmaRepo;
   

    @Autowired
    public DesignShaurmaController(JpaIngredientRepository ingredientRepo, JpaShaurmaRepository shaurmaRepo) {
        this.ingredientRepo = ingredientRepo;
        this.shaurmaRepo = shaurmaRepo;
        
    }
    
    @GetMapping
    public String showDesignForm() {
    	return "design";
    }
   
    //метод обрабатывает создание Шаурмы
    @PostMapping
    public String processShaurma(@Valid Shaurma shaurma, 
    						  Errors errors,
    						  @ModelAttribute Order order, 
    						  JpaIngredientRepository ingredientRepo) {
    	
    	if (errors.hasErrors()) {
    			return "design";
    		}
    	
    	log.info("Processing shaurma: {}", shaurma);
    	
    	shaurmaRepo.save(shaurma);
    	
    	return "redirect:/orders/current";
   }
    
    @ModelAttribute(name = "shaurmaOrder")
    public Order order() {
    	return new Order();	
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
