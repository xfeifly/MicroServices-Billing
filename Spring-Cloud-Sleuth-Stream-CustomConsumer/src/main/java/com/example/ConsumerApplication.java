package com.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.stream.SleuthSink;
import org.springframework.cloud.sleuth.stream.SleuthStreamAutoConfiguration;
import org.springframework.cloud.sleuth.stream.Spans;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;








@EnableBinding(SleuthSink.class)
@Import(ConsumerConfiguration.class)
@SpringBootApplication(exclude = SleuthStreamAutoConfiguration.class)
public class ConsumerApplication {
	
	private static final Log log = LogFactory.getLog(ConsumerApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
	
	@StreamListener(SleuthSink.INPUT)
    public void sink(Spans input) throws Exception {
		log.info("In consumer: the Spans are: " + input.getSpans().toString());
    }
	
}
