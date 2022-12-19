package security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import shaurma_house.data.UserRepository;


@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	private UserRepository userRepo;

	 
	@Autowired
	public RegistrationController(UserRepository personRepo) {
			 this.userRepo = personRepo;
	}
	
	@GetMapping
	public String registerForm() {
		return "registration";
	}
	
	@PostMapping
	public String processRegistration(RegistrationForm form) {
		userRepo.save(form.toUser());
		return "redirect:/login";
	}

	
	
}
