package it.marco.camel.builder;

import org.apache.camel.builder.RouteBuilder;

public class RestDSLRouteBuilderEmbedded extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration().component("spark-rest").port(9091);
		
		rest("/say").
			get("/hello").route().transform().constant("Hello World").endRest().
			get("/bye").route().transform().constant("Bye World").endRest();
	}

}
