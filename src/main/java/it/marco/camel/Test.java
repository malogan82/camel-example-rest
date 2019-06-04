package it.marco.camel;

import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import it.marco.camel.builder.RestDSLRouteBuilder;
import it.marco.camel.service.UserService;

public class Test {
	
	public static Logger LOGGER = LoggerFactory.getLogger(Test.class);
	
	public static void main(String[] args) {
		Main main = new Main();
		ActiveMQJMSConnectionFactory activeMQJMSConnectionFactory = 
				new ActiveMQJMSConnectionFactory("tcp://localhost:61616", "admin", "admin");
		JmsComponent jmsComponent = new JmsComponent();
		jmsComponent.setConnectionFactory(activeMQJMSConnectionFactory);
		jmsComponent.setMessageConverter(new SimpleMessageConverter());
		main.bind("jms", jmsComponent);
		main.bind("userService",new UserService());
		main.addRouteBuilder(new RestDSLRouteBuilder());
		//main.addRouteBuilder(new RestDSLRouteBuilderEmbedded());
		//main.addRouteBuilder(new RestDSLRouteBuilderRepeated());
		//main.addRouteBuilder(new RestDSLRouteBuilderDynamicTo());
		//main.addRouteBuilder(new RestDSLRouteBuilderURITemplate());
		try {
			main.start();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
	}

}
