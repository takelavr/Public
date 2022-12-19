package shaurma_house.web;

import lombok.extern.slf4j.Slf4j;
import shaurma_house.data.JpaOrderRepository;
import shaurma_house.data.JpaShaurmaRepository;
import shaurma_house.data.OrderProps;
import shaurma_house.data.UserRepository;
import shaurma_house.model.Ingredient;
import shaurma_house.model.Order;
import shaurma_house.model.Shaurma;
import shaurma_house.model.User;
import shaurma_house.model.Ingredient.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("shaurmaOrder")
@ConfigurationProperties(prefix="shaurma.orders")
public class OrderController {

	private JpaOrderRepository orderRepo;
	private JpaShaurmaRepository shaurmaRepo;
	private UserRepository user;
	private OrderProps props;
	
	public OrderController(JpaOrderRepository orderRepo, OrderProps props, JpaShaurmaRepository shaurmaRepo) {
		this.orderRepo = orderRepo;
		this.shaurmaRepo = shaurmaRepo;
		this.props = props;
	}
	
	@Autowired
	public  OrderController(JpaOrderRepository orderRepo) {
	        this.orderRepo = orderRepo;
	}
    
	@GetMapping("/current")
	public String orderForm(Model model){
	  model.addAttribute("shaurmaOrder", new Order());
	  return "orderForm";
	}
    
    // метод обрабатывает заполнение формы доставки и оплаты, также проверяет на ошибки и передает данные через POST  дальше
    @PostMapping
    public String processOrder(@Valid Order order,
    						   Errors errors,
    						   SessionStatus sessionStatus,
    						   JpaShaurmaRepository shaurmaRepo) {	
    	
    			if (errors.hasErrors()) {
    				return "orderForm";
    			}
    		log.info("Order submitted: {}", order);
    		
    		orderRepo.save(order);
    		
    		sessionStatus.setComplete();
    		
    		return "redirect:/thanks";
    }
    
    @GetMapping
    public String ordersForUser(User user, Model model) {
    
    	Pageable pageable = PageRequest.of(0, props.getPageSize());
    	 model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
    	 
    	 return "orderList";
    }
    
      



}
