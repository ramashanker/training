package com.activemq.app.message.consume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

  private final Logger logger = LoggerFactory.getLogger(Consumer.class);

  @JmsListener(destination = "test.queue")
  public void listener(String message) {
    System.out.println("Received Message: " + message);
  }
}
