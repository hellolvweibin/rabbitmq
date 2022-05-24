/*
 *@Project ：RabbitMq
 *@IDE     ：IntelliJ IDEA
 *@Author  ：Levi_Bee
 *@Date    ：2022/5/17 2:18 下午
 */


package com.lv.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author levi_bee
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {

        //1. 创建连接工程
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.124.4");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        Channel channel = null;
        try {
            //2. 创建连接Connection
            connection = connectionFactory.newConnection("生产者");
            //3. 通过连接获取通道Channel
            channel = connection.createChannel();
            //4. 通过创建交换机，声明队列，绑定关系，路由key，发送消息和接收消息
            channel.basicConsume("queue1", true, new DeliverCallback() {
                @Override
                public void handle(String s, Delivery message) throws IOException {
                        System.out.println("收到的消息是"+new String(message.getBody(),"UTF-8"));
                    }
                },new CancelCallback(){
                @Override
                    public void handle (String consumerTag){
                        System.out.println("接收失败了。。。");
                    }

            });
            System.out.println("开始接收消息");
            System.in.read(); //程序阻断

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            //7. 关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //8. 关闭连接
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }


    }
}
