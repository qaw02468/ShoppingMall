package tw.yu.shoppingmall.order.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.yu.common.to.mq.SecKillOrderTo;
import tw.yu.shoppingmall.order.service.OrderService;

import java.io.IOException;

/**
 * @author - a89010531111@gmail.com
 */
@RabbitListener(queues = "order.seckill.order.queue")
@Component
public class OrderSecKillListener {

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void listener(SecKillOrderTo secKillOrderTo, Channel channel, Message message) throws IOException {
        try {
            orderService.createSecKillOrder(secKillOrderTo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}