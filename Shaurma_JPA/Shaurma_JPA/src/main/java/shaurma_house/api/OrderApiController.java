package shaurma_house.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import shaurma_house.data.JpaOrderRepository;
import shaurma_house.data.OrderProps;
import shaurma_house.model.Order;

@Slf4j
@RestController
@RequestMapping(value = "/orders", produces = "application/json")
@CrossOrigin(origins = "*")
public class OrderApiController {

	private JpaOrderRepository orderRepo;
	private OrderProps props;
	
	@Autowired
    public OrderApiController(OrderProps props, JpaOrderRepository orderRepo) {
        this.props = props;
        this.orderRepo = orderRepo;
    }

	//Заказы
	//удаление заказа по Id DELETE 
	@DeleteMapping("/{orderId}")
	  @ResponseStatus(HttpStatus.NO_CONTENT)
	  public void deleteOrder(@PathVariable("orderId") Long orderId) {
	    try {
	      orderRepo.deleteById(orderId);
	    } catch (EmptyResultDataAccessException ignored) {
	      //doNothing
	    }
	  }
	
	//правка заказа PUT (полная замена заказа)
	@PutMapping("/{orderId}")
	public Order putOrder(@RequestBody Order order) {
	        return orderRepo.save(order);
	}
	
	//правка заказа PATCH
	@PatchMapping(path="/{orderId}", consumes="application/json")
	public Order patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch) {

		Order order = orderRepo.findById(orderId).get();
		
		if (patch.getName() != null) {
			order.setName(patch.getName());
		}
    
		if (patch.getStreet() != null) {
			order.setStreet(patch.getStreet());
		}
    
		if (patch.getCity() != null) {
			order.setCity(patch.getCity());
		}
    
		if (patch.getState() != null) {
			order.setState(patch.getState());
		}
    
		if (patch.getZip() != null) {
			order.setZip(patch.getZip());
		}
		
		if (patch.getCcNumber() != null) {
			order.setCcNumber(patch.getCcNumber());
		}
    
		if (patch.getCcExpiration() != null) {
			order.setCcExpiration(patch.getCcExpiration());
		}
    
		if (patch.getCcCVV() != null) {
			order.setCcCVV(patch.getCcCVV());
		}

		return orderRepo.save(order);
	}
	
}