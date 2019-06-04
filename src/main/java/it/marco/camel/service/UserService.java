package it.marco.camel.service;

import org.apache.camel.Exchange;

import it.marco.camel.model.CountryPojo;
import it.marco.camel.model.UserPojo;

public class UserService {
	
	public void livesWhere(Exchange exchange) {
		CountryPojo country = new CountryPojo();
		country.setName("ITALIA");
        exchange.getIn().setBody(country);
    }

	public UserPojo getUserById(int id) {
		UserPojo user = new UserPojo();
		user.setId(100L);
		user.setName("Marco");
		user.setSurname("Longobardi");
		return user;
	}
	
	public String updateUser(UserPojo user) {
		return "User updated correctly";
	}
}
