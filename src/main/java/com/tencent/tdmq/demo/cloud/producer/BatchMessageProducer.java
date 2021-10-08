package com.tencent.tdmq.demo.cloud.producer;

import com.tencent.tdmq.demo.cloud.Config;
import org.apache.pulsar.client.api.*;

import java.util.concurrent.TimeUnit;

public class BatchMessageProducer {

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
                .enableBatching(true)
                .batchingMaxMessages(10)
                .batchingMaxBytes(1024)
                .batchingMaxPublishDelay(10, TimeUnit.SECONDS)
                .create();
        System.out.println(">> pulsar producer created.");
        String value = "my-sync-message";

        //同步发送消息
        MessageId msgId = producer.newMessage().value(value.getBytes()).send();
        System.out.println("Message with ID " + msgId + ",value:" + value + " successfully sent");

        //关闭生产者
        producer.close();
    }
}
