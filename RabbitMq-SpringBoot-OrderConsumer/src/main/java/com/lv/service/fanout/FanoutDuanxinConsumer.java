/*
 *@Project ：rabbitmq
 *@IDE     ：IntelliJ IDEA
 *@Author  ：Levi_Bee
 *@Description  ：
 *@Date    ：2022/5/24 9:33 上午
 */


package com.lv.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author levi_bee
 */

//监听队列
@RabbitListener(queues = {"duanxin.fanout.queue"})
@Component
public class FanoutDuanxinConsumer {

    /**
     * @RabbitHandler 消息落脚点
     */
    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("duanxin-fanout---接收到了订单信息是：--->"+message);
    }
}
