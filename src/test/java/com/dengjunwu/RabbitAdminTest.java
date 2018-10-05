package com.dengjunwu;

import com.dengjunwu.entity.OrderMsg;
import com.dengjunwu.queue.RabbitProducer;
import org.assertj.core.internal.Maps;
import org.junit.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dengjunwu on 2018/10/4.
 */

public class RabbitAdminTest extends BaseTest{
    @Autowired
    RabbitAdmin rabbitAdmin;
    @Autowired
    RabbitProducer rabbitProducer;

    @Test
    public void testAdmin(){
        rabbitAdmin.declareExchange(
                new DirectExchange("test.direct", false, false));
        rabbitAdmin.declareExchange(
                new TopicExchange("test.topic", false, false));
        rabbitAdmin.declareExchange(
                new FanoutExchange("test.fanout", false, false));
    }

    @Test
    public void testSend(){
        try {
            OrderMsg orderMsg
                    = OrderMsg.builder()
                    .id(2L).amount(55.5)
                    .description("测试发消息")
                    .name("发消息")
                    .createTime(System.currentTimeMillis())
                    .updateTime(System.currentTimeMillis())
                    .build();
            Map<String, Object> properties = new HashMap<>();
            properties.put("x-delay", 60000);
            rabbitProducer.send(JSONObject.toJSONString(orderMsg), properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
