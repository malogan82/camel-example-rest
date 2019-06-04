package it.marco.camel.builder;

import org.apache.camel.builder.RouteBuilder;

public class RestDSLRouteBuilderDynamicTo extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration().component("spark-rest").port(9091);
		
		rest("/say")
	    	.get("/hello/{language}").toD("jms:queue:hello-${header.language}");
		
		from("jms:queue:hello-italian")
			.transform().constant("Hello world");
		
	}

}
