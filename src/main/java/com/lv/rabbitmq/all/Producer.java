/*
 *@Project ：rabbitmq
 *@IDE     ：IntelliJ IDEA
 *@Author  ：Levi_Bee
 *@Date    ：2022/5/19 9:57 上午
 */


package com.lv.rabbitmq.all;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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
            //5. 准备消息内容
            String message = "Hello Levi_Bee!";
            //6. 准备交换机
            String exchangeName = "direct_message_exchange";
//            //7. 定义路由key ,因为交换机类型为fanout，所以这里为空
//            String routeKey = "";
            //8. 指定交换机的类型
            String exchangeType = "direct";

            //声明交换机，注意：如果直接往交换机里面发送消息的话，会报错，需要先声明 ,true代表持久化，重启不丢失
            channel.exchangeDeclare(exchangeName, exchangeType, true);

            //声明队列
            channel.queueDeclare("queue5", true, false, false, null);
            channel.queueDeclare("queue6", true, false, false, null);
            channel.queueDeclare("queue7", true, false, false, null);

            //绑定队列

            channel.queueBind("queue5",exchangeName,"order");
            channel.queueBind("queue6",exchangeName,"order");
            channel.queueBind("queue7",exchangeName,"course");



            //9. 发送消息给队列Queue
            channel.basicPublish(exchangeName, "order", null, message.getBytes());
            System.out.println("消息发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送消息出现一场。。。");

        } finally {
            //10. 关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //11. 关闭连接
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
