package security;

import lombok.Data;
import shaurma_house.model.User;



@Data
public class RegistrationForm {

	private String username;
	private String password;
	private String fullname;
	private String street;
	private String city;
	private String state;
	private String phone;
	
	public User toUser() {
        return new User(username, password, fullname, street, city, state, phone);
    }
}
