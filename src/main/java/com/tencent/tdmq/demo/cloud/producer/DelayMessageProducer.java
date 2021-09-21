package com.tencent.tdmq.demo.cloud.producer;

import com.tencent.tdmq.demo.cloud.Config;
import org.apache.pulsar.client.api.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class DelayMessageProducer {

    public static void main(String[] args) throws PulsarClientException {
        invoke();
    }

    private static void invoke() throws PulsarClientException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(Config.SERVICE_URL)
                .authentication(AuthenticationFactory.token(Config.TOKEN))
                .build();
        System.out.println(">> pulsar client created.");

        //创建生产者
        Producer<byte[]> producer = client.newProducer()
                .topic(Config.TOPIC)
                .create();
        System.out.println(">> pulsar producer created.");

        //生产延时消息
        long delayTime = 10L;
        String value = "my-delay-message";
        MessageId delayedMsgId = producer.newMessage()
                .value(value.getBytes())
                .deliverAfter(delayTime, TimeUnit.SECONDS) //单位可以自由选择
                .send();
        System.out.println("Message with ID " + delayedMsgId + ",value:" + value + " will be sent later");

        //生产定时消息
        /*
        try {
            //需要先将显式的时间转换为 Timestamp
            long timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-10-10 00:00:00").getTime();
            //通过调用 producer 的 deliverAt 方法来实现定时消息
            MessageId fixedMsgId = producer.newMessage()
                    .value(value.getBytes())
                    .deliverAt(timeStamp)
                    .send();
            System.out.println("Message with ID " + delayedMsgId + ",value:" + value + " will be sent at fixed time");
        } catch (ParseException e) {
            //TODO 添加对 Timestamp 解析失败的处理方法
            e.printStackTrace();
        }
        */
        //关闭生产者
        producer.close();

    }
}
