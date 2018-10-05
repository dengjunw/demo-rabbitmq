package com.dengjunwu.queue;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


/**
 * Created by dengjunwu on 2018/10/4.
 */

@Component
@Slf4j
public class RabbitConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "demo_delay_queue", durable = "true"),
//            exchange = @Exchange(value = "demo_delay_topic", durable = "true", type = "topic",ignoreDeclarationExceptions = "true"),
//            key = "demo.#"
//    ))
    @RabbitListener(queues = "demo_delay_queue")
    @RabbitHandler
    public void consumeMsg(Message message, Channel channel) throws Exception{
        log.info("message body: {}", message.getPayload());
        Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);
    }
}
