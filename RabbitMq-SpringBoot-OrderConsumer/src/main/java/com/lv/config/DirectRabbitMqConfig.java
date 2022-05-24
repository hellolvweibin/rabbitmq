/*
 *@Project ：rabbitmq
 *@IDE     ：IntelliJ IDEA
 *@Author  ：Levi_Bee
 *@Description  ：配置类
 *@Date    ：2022/5/23 6:14 下午
 */


package com.lv.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author levi_bee
 */

@Configuration
public class DirectRabbitMqConfig {

    //1. 声明注册direct模式的交换机

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct_order_exchange", true, true);

    }

    //2.声明队列 sms.direct.queue,email.direct.queue,duanxin.direct.queue

    @Bean
    public Queue smsDirectQueue() {
        return new Queue("sms.direct.queue", true);
    }

    @Bean
    public Queue duanxinDirectQueue() {
        return new Queue("duanxin.direct.queue", true);
    }

    @Bean
    public Queue emailDirectQueue() {
        return new Queue("email.direct.queue", true);
    }
    //3.完成绑定关系（队列和交换机完成绑定关系）

    @Bean
    public Binding smsDirectBing() {
        return BindingBuilder.bind(smsDirectQueue()).to(directExchange()).with("sms");
    }

    @Bean
    public Binding duanxinDirectBing() {
        return BindingBuilder.bind(duanxinDirectQueue()).to(directExchange()).with("duanxin");
    }

    @Bean
    public Binding emailDirectBing() {
        return BindingBuilder.bind(emailDirectQueue()).to(directExchange()).with("email");
    }
}
