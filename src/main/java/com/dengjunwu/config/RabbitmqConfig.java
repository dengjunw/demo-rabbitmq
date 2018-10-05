package com.dengjunwu.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dengjunwu on 2018/10/4.
 */
@Configuration
public class RabbitmqConfig {

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setAddresses("127.0.0.1:5672");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        cachingConnectionFactory.setVirtualHost("/");
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public CustomExchange getTopicExchange(){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "topic");
//        return new TopicExchange("demo_delay_topic", false, false, args);
        return new CustomExchange("demo_delay_topic", "x-delayed-message", true, false, args);
    }
    @Bean
    public Queue getQueue(){
        return new Queue("demo_delay_queue", true);
    }
    @Bean
    public Binding getBinding(){
        return new Binding("demo_delay_queue", Binding.DestinationType.QUEUE, "demo_delay_topic", "demo.#", null);
    }

    @Bean
    public RabbitTemplate getRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }
}
