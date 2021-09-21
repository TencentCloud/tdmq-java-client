package com.tencent.tdmq.demo.cloud.producer;

import com.tencent.tdmq.demo.cloud.Config;
import org.apache.pulsar.client.api.*;

public class AsyncMessageProducer {

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
        String value = "my-async-message";

        //异步发送消息
        producer.sendAsync(value.getBytes()).thenAccept(msgId -> {
            System.out.println("Message with ID " + msgId + " successfully sent");
        });

        //异步关闭生产者
        producer.closeAsync()
                .thenRun(() -> System.out.println("Producer closed"))
                .exceptionally((ex) -> {
                    System.err.println("Failed to close producer: " + ex);
                    return null;
                });
    }
}
