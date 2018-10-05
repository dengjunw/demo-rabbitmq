package com.dengjunwu;

import com.dengjunwu.queue.RabbitProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

/**
 * Created by dengjunwu on 2018/10/4.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.dengjunwu"})
@Slf4j
public class Bootstrap {
    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(Bootstrap.class, args);

//        RabbitProducer rabbitProducer = context.getBean(RabbitProducer.class);
//        try {
//            rabbitProducer.send("hello", null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        log.info("{} profile is active", Arrays.toString(activeProfiles));
    }
}
