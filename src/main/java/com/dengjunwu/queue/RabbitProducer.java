package com.dengjunwu.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * Created by dengjunwu on 2018/10/4.
 */
@Component
@Slf4j
public class RabbitProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //消息的确认
    final RabbitTemplate.ConfirmCallback confirmCallback
            = (correlationData, ack, cause) -> {
        log.info("correlationData: {}", correlationData);
        log.info("ack: {}",ack);
        if (!ack){
            log.error("ack failure");
        }
    };

    //消息的返回
    final RabbitTemplate.ReturnCallback returnCallback
            = (message, replyCode, replyText, exchange, routingKey) ->
            log.info("return: exchange:{}, routingKey:{}, replyCode:{}, replyText:{}",
            exchange, routingKey, replyCode, replyText);


    public void send(Object message, Map<String, Object> properties)throws Exception{
        MessageHeaders messageHeaders = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message, messageHeaders);

        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);

        rabbitTemplate.convertAndSend("demo_delay_topic", "demo.first", msg,(message1 -> {
            if (properties != null && properties.containsKey("x-delay")){
                message1.getMessageProperties().setHeader("x-delay", properties.get("x-delay"));
            }
            return message1;
        }));
    }
}
