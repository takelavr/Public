package shaurma_house.web;

import lombok.extern.slf4j.Slf4j;
import shaurma_house.data.JpaOrderRepository;
import shaurma_house.model.ShaurmaOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("shaurmaOrder")
public class OrderController {

	private JpaOrderRepository orderRepo;
	
	@Autowired
	public  OrderController(JpaOrderRepository orderRepo) {
	        this.orderRepo = orderRepo;
	}
    
	@GetMapping("/current")
	public String orderForm(Model model){
	  model.addAttribute("shaurmaOrder", new ShaurmaOrder());
	  return "orderForm";
	}
    
    // метод обрабатывает заполнение формы доставки и оплаты, также проверяет на ошибки и передает данные через POST  дальше
    @PostMapping
    public String processOrder(@Valid ShaurmaOrder order,
    						   Errors errors,
    						   SessionStatus sessionStatus) {	
    			if (errors.hasErrors()) {
    				return "orderForm";
    			}
    		log.info("Order submitted: {}", order);
    		sessionStatus.setComplete();
    		return "redirect:/thanks";
    }



}
