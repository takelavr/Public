package shaurma_house.api;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import shaurma_house.data.JpaIngredientRepository;
import shaurma_house.data.JpaShaurmaRepository;
import shaurma_house.data.UserRepository;
import shaurma_house.model.Ingredient;
import shaurma_house.model.Shaurma;
import shaurma_house.model.Ingredient.Type;

@Slf4j
@RestController
@RequestMapping(path="/api/ingredients",
				produces="application/json")
@CrossOrigin(origins="*")
public class IngredientApiController {

	private JpaShaurmaRepository shaurmaRepo;
	
	RestTemplate rest = new RestTemplate();
	
	public IngredientApiController (JpaShaurmaRepository shaurmaRepo) {
		this.shaurmaRepo = shaurmaRepo;
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	//Ингредиенты
	//получение наименования ингредиента по Id GET
//	public Ingredient getIngredientById(String ingredientId) {
//		 return rest.getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, ingredientId);
//	}
	
	public Ingredient getIngredientById(String ingredientId) {
		 ResponseEntity<Ingredient> responseEntity = rest.getForEntity("http://localhost:8080/ingredients/{id}",
				 						Ingredient.class, ingredientId);
		 
		 log.info("Fetched time: {}",
		 responseEntity.getHeaders().getDate());
		 return responseEntity.getBody();
	}
	
	// замена ингредиента PUT
	public void updateIngredient(Ingredient ingredient) {
		rest.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
	}
	
	//удаление ингредиентов DELETE
	public void deleteIngredient(Ingredient ingredient) {
		rest.delete("http://localhost:8080/ingredients/{id}", ingredient.getId());
	}
	
	//добавление нового ингредиента POST
	public Ingredient createIngredient(Ingredient ingredient) {
		return rest.postForObject("http://localhost:8080/ingredients", ingredient, Ingredient.class);
	}
	
	//Ручная загрузка ингредиентов и заказов для тестирования
		@Bean
		public CommandLineRunner dataLoader(JpaIngredientRepository repo, UserRepository userRepo, JpaShaurmaRepository shaurmaRepo) {
			return args -> {
							Ingredient ing1 = new Ingredient("NORM", "Обычная лепешка", Type.TORT);
							Ingredient ing2 = new Ingredient("CHES", "Сырная лепешка", Type.TORT);
							Ingredient ing3 = new Ingredient("CUCU", "Огурчик маринованный", Type.FILLER1);
							Ingredient ing4 = new Ingredient("TOMAT", "Помидор сочный", Type.FILLER1);
							Ingredient ing5 = new Ingredient("CARR", "Морковь по-корейски", Type.FILLER2);
							Ingredient ing6 = new Ingredient("POTA", "Картофель фри", Type.FILLER2);
							Ingredient ing7 = new Ingredient("NORS", "Обычную", Type.SHARPNESS);
							Ingredient ing8 = new Ingredient("SHAR", "Поострее", Type.SHARPNESS);
							Ingredient ing9 = new Ingredient("MAY", "Майонез", Type.SAUCE);
							Ingredient ing10 = new Ingredient("KETC", "Кетчуп", Type.SAUCE);
		 
							repo.save(ing1);
							repo.save(ing2);
							repo.save(ing3);
							repo.save(ing4);
							repo.save(ing5);
							repo.save(ing6);
							repo.save(ing7);
							repo.save(ing8);
							repo.save(ing9);
							repo.save(ing10);
		 
							Shaurma sh1 = new Shaurma();
							sh1.setName("шаурма1");
							sh1.setIngredients(Arrays.asList(ing1, ing3, ing4, ing6, ing9));
							shaurmaRepo.save(sh1);
		 
							Shaurma sh2 = new Shaurma();
							sh2.setName("шаурма2");
							sh2.setIngredients(Arrays.asList(ing1, ing3, ing4, ing7));
							shaurmaRepo.save(sh2);
		 
							Shaurma sh3 = new Shaurma();
							sh3.setName("шаурма3");
							sh3.setIngredients(Arrays.asList(ing2, ing4, ing10));
							shaurmaRepo.save(sh3);
						  };
		}
	
	
}
