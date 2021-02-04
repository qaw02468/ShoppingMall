package tw.yu.shoppingmall.ware.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author - a89010531111@gmail.com
 */
@Configuration
public class MyRabbitConfig {

    @Value("${myRabbitmq.MQConfig.queues}")
    private String queues;

    @Value("${myRabbitmq.MQConfig.eventExchange}")
    private String eventExchange;

    @Value("${myRabbitmq.MQConfig.routingKey}")
    private String routingKey;

    @Value("${myRabbitmq.MQConfig.delayQueue}")
    private String delayQueue;

    @Value("${myRabbitmq.MQConfig.letterRoutingKey}")
    private String letterRoutingKey;

    @Value("${myRabbitmq.MQConfig.ttl}")
    private Integer ttl;

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Exchange stockEventExchange() {

        return new TopicExchange(eventExchange, true, false);
    }

    @Bean
    public Queue stockReleaseQueue() {
        return new Queue(queues, true, false, false);
    }

    @Bean
    public Queue stockDelayQueue() {

        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", eventExchange);
        args.put("x-dead-letter-routing-key", letterRoutingKey);
        args.put("x-message-ttl", ttl);

        return new Queue(delayQueue, true, false, false, args);
    }

    @Bean
    public Binding stockLockedReleaseBinding() {

        return new Binding(queues, Binding.DestinationType.QUEUE, eventExchange, letterRoutingKey + ".#", null);
    }

    @Bean
    public Binding stockLockedBinding() {

        return new Binding(delayQueue, Binding.DestinationType.QUEUE, eventExchange, routingKey, null);
    }
}