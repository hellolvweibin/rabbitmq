/*
 *@Project ：RabbitMq
 *@IDE     ：IntelliJ IDEA
 *@Author  ：Levi_Bee
 *@Date    ：2022/5/17 2:17 下午
 */

//简单模式Simple
package com.lv.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;


/**
 * @author levi_bee
 */


public class Producer {
    public static void main(String[] args) {

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
            String queueName = "queue1";
            /*queueDeclare()实现声明队列
             * @param1:队列的名称
             * @param2:是否要持久化
             * @param3:排他性，即是够独占独立
             * @param4:是够自动删除，随着最后一个消费者消息完成后时候把队列删除
             * @param5:携带附属桉树
             * */
            channel.queueDeclare(queueName, false, false, false, null);
            //5. 准备消息内容
            String message = "Hello World!";
            //6. 发送消息给队列Queue
            channel.basicPublish("", queueName, null, message.getBytes());
            System.out.println("消息发送成功!");
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
