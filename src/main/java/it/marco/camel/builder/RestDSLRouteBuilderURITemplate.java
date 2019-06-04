package it.marco.camel.builder;

import org.apache.camel.builder.RouteBuilder;

public class RestDSLRouteBuilderURITemplate extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration().component("spark-rest").port(9091);
		
		rest("/say")
	    	.get("/hello/{name}").to("direct:hello")
	    	.get("/bye/{name}").to("direct:bye");

		from("direct:hello")
	    	.transform().simple("Hello ${header.name}");
	
		from("direct:bye")
	    	.transform().simple("Bye ${header.name}");

	}

}
