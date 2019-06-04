package it.marco.camel.builder;

import org.apache.camel.builder.RouteBuilder;

public class RestDSLRouteBuilderRepeated extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration().component("spark-rest").port(9091);
		
		rest()
	    	.get("/say/hello").to("direct:hello")
	    	.get("/say/bye").to("direct:bye");

		from("direct:hello")
	    	.transform().constant("Hello World");
	
		from("direct:bye")
	    	.transform().constant("Bye World");

	}

}
