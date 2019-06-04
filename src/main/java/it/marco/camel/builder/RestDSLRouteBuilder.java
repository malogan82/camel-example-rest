package it.marco.camel.builder;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import it.marco.camel.model.CountryPojo;
import it.marco.camel.model.UserPojo;
import it.marco.camel.service.UserErrorService;
import it.marco.camel.service.UserService;

public class RestDSLRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		onException(UnrecognizedPropertyException.class)
			.handled(true)
			.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
			.setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
			.setBody().constant("Invalid json data");
		
		//restConfiguration().component("spark-rest").port(9091);
		restConfiguration()
			.component("restlet")
			.host("localhost")
			.port("9091")
			.bindingMode(RestBindingMode.json)
			.apiContextPath("/api-doc")
            .apiProperty("api.title", "User API").apiProperty("api.version", "1.2.3")
            .apiProperty("cors", "true");
		
//		rest("/say").consumes("text/plain").produces("text/plain")
//		    .get("/hello").to("direct:hello")
//	    	.get("/bye").to("direct:bye")
//	    	.verb("TRACE", "/hello").route().transform().simple("${body}");
		
		rest("/say").consumes("text/plain").produces("text/plain")
	    	.get("/hello").to("direct:hello")
	    	.get("/bye").to("direct:bye")
	    	.get("/{id}").description("Find user by ID.").outType(UserPojo.class)
	    		.param().name("id").type(RestParamType.path).description("The ID of the user to get information about.").dataType("int").endParam()
	    		.to("bean:userService?method=getUserById(${header.id})")
	    	.put().type(UserPojo.class).description("Updates or creates a user.")
	    		.param().name("body").type(RestParamType.body).endParam()
	    		.to("bean:userService?method=updateUser");
		
		rest("/email").consumes("text/plain,application/json").produces("text/html")
			.post("/to/{recipient}").to("direct:foo")
			.get("/for/{username}").to("direct:bar");
		
//		rest("/email")
//			.get("/to/{recipient}").consumes("text/plain,application/json").produces("text/html").to("direct:foo")
//			.post("/to/{recipient}").consumes("text/plain,application/json").produces("text/html").to("direct:foo");

		rest("/users/")
			.post("lives").type(UserPojo.class).outType(CountryPojo.class)
				.route()
					.choice()
						.when().simple("${body.id} < 100")
							.bean(new UserErrorService(), "idTooLowError")
						.otherwise()
						    .bean(new UserService(), "livesWhere");
		
		rest("/customers/")
			.get("/{id}").to("direct:customerDetail")
			.get("/{id}/orders")
			  .param()
			  	.name("verbose")
			  	.type(RestParamType.query)
			  	.defaultValue("false")
			  	.description("Verbose order details")
			  .endParam()
			    .to("direct:customerOrders")
			.post("/neworder").to("direct:customerNewOrder");
			  
		from("direct:customerOrders")
			.log("${header.verbose}")
			.transform()
			.simple("${header.verbose}");
			
			
		from("direct:hello")
	    	.transform().constant("Hello World");
	
		from("direct:bye")
	    	.transform().constant("Bye World");
		
		from("direct:foo")
		    .transform().simple("Email sent to ${header.recipient}");
		
		from("direct:bar")
	    	.transform().simple("Email address for ${header.username} is marco.longobardi@eng.it");

	}

}
