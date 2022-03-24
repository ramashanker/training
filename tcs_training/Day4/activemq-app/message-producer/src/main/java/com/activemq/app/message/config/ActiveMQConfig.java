import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class ActiveMQConfig {

  @Value("${activemq.broker.url}")
  private String brokerUrl;

  @Bean
  public Queue queue() {
    return new ActiveMQQueue("test-queue");
  }

  @Bean
  public ActiveMQConnectionFactory activeMQConnectionFactory() {
    ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
    activeMQConnectionFactory.setBrokerURL(brokerUrl);
    return activeMQConnectionFactory;
  }

  @Bean
  public JmsTemplate jmsTemplate() {
    return new JmsTemplate(activeMQConnectionFactory());
  }
}
