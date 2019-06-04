package it.marco.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.main.Main;
import org.apache.camel.spring.SpringCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
	
	public static Logger LOGGER = LoggerFactory.getLogger(SpringTest.class);
	
	public static void main(String[] args) {
		AbstractXmlApplicationContext appContext = new ClassPathXmlApplicationContext("camel-context.xml");
		//AbstractXmlApplicationContext appContext = new ClassPathXmlApplicationContext("camel-context-embedded.xml");
		//AbstractXmlApplicationContext appContext = new ClassPathXmlApplicationContext("camel-context-repeated.xml");
		//AbstractXmlApplicationContext appContext = new ClassPathXmlApplicationContext("camel-context-dynamic-to.xml");
		//AbstractXmlApplicationContext appContext = new ClassPathXmlApplicationContext("camel-context-uri-template.xml");
		try {
			CamelContext camelContext = SpringCamelContext.springCamelContext(appContext);
			Main main = new Main();
			main.getCamelContexts().add(camelContext);
			main.start();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
	}

}
