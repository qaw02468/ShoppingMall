package tw.yu.shoppingmall.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author - a89010531111@gmail.com
 */
@Configuration
public class MyMqConfig {

    @Value("${myRabbitmq.MQConfig.queues}")
    private String queues;

    @Value("${myRabbitmq.MQConfig.eventExchange}")
    private String eventExchange;

    @Value("${myRabbitmq.MQConfig.routingKey}")
    private String routingKey;

    @Value("${myRabbitmq.MQConfig.delayQueue}")
    private String delayQueue;

    @Value("${myRabbitmq.MQConfig.createOrder}")
    private String createOrder;

    @Value("${myRabbitmq.MQConfig.ReleaseOther}")
    private String ReleaseOther;

    @Value("${myRabbitmq.MQConfig.ReleaseOtherKey}")
    private String ReleaseOtherKey;

    @Value("${myRabbitmq.MQConfig.ttl}")
    private Integer ttl;

    @Bean
    public Queue orderDelayQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", eventExchange);
        args.put("x-dead-letter-routing-key", routingKey);
        args.put("x-message-ttl", ttl);
        return new Queue(delayQueue, true, false, false, args);
    }

    @Bean
    public Queue orderReleaseOrderQueue() {
        return new Queue(queues, true, false, false);
    }

    @Bean
    public Exchange orderEventExchange() {

        return new TopicExchange(eventExchange, true, false);
    }

    @Bean
    public Binding orderCreateOrderBinding() {

        return new Binding(delayQueue, Binding.DestinationType.QUEUE, eventExchange, createOrder, null);
    }

    @Bean
    public Binding orderReleaseOrderBinding() {

        return new Binding(queues, Binding.DestinationType.QUEUE, eventExchange, routingKey, null);
    }

    @Bean
    public Binding orderReleaseOtherBinding() {

        return new Binding(ReleaseOther, Binding.DestinationType.QUEUE, eventExchange, ReleaseOtherKey + ".#", null);
    }

}