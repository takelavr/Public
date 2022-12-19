package shaurma_house.api;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import shaurma_house.data.JpaShaurmaRepository;
import shaurma_house.model.Shaurma;


@RestController
@RequestMapping(path="/api/shaurmas",
				produces="application/json")
@CrossOrigin(origins="http://shaurma-house:8080")
public class ShaurmaApiController {
	
	private JpaShaurmaRepository shaurmaRepo;

	public ShaurmaApiController(JpaShaurmaRepository shaurmaRepo) {
		this.shaurmaRepo = shaurmaRepo;

	}
	
	//Рецепт
	//Получение шаурмы по по Id Get
	@GetMapping("/{id}")
	public ResponseEntity<Shaurma> shaurmaById(@PathVariable("id") Long id) {
		Optional<Shaurma> optTaco = shaurmaRepo.findById(id);
		if (optTaco.isPresent()) {
			return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	//Добавление рецепта POST
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Shaurma postShaurma(@RequestBody Shaurma shaurma) {
		return shaurmaRepo.save(shaurma);
	}
	


}
