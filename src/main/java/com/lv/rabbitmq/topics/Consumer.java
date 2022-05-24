/*
 *@Project ：rabbitmq
 *@IDE     ：IntelliJ IDEA
 *@Author  ：Levi_Bee
 *@Date    ：2022/5/19 9:57 上午
 */


package com.lv.rabbitmq.topics;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author levi_bee
 */
public class Consumer {
    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {

            //1. 创建连接工程
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("192.168.124.4");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("admin");
            connectionFactory.setPassword("admin");
            connectionFactory.setVirtualHost("/");
            //获取队列名称
            final String queueName=Thread.currentThread().getName();
            Connection connection = null;
            Channel channel = null;
            try {
                //2. 创建连接Connection
                connection = connectionFactory.newConnection("生产者");
                //3. 通过连接获取通道Channel
                channel = connection.createChannel();

                //4. 定义接收消息的回调
                Channel finalChannel = channel;
                finalChannel.basicConsume(queueName, true, new DeliverCallback() {
                    @Override
                    public void handle(String s, Delivery message) throws IOException {
                        System.out.println(queueName+"收到的消息是" + new String(message.getBody(), "UTF-8"));
                    }
                }, new CancelCallback() {
                    @Override
                    public void handle(String s) {

                    }

                });
                System.out.println(queueName+"开始接收消息");
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


    };

    public static void main(String[] args) {
        //启动三个线程模拟队列
        new Thread(runnable,"queue1").start();
        new Thread(runnable,"queue2").start();
        new Thread(runnable,"queue3").start();

    }
}