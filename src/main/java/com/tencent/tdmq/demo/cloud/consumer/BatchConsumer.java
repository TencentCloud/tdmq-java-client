package com.tencent.tdmq.demo.cloud.consumer;

import com.tencent.tdmq.demo.cloud.Config;
import org.apache.pulsar.client.api.*;

import java.util.concurrent.TimeUnit;

public class BatchConsumer {
    public static void main(String[] args) throws PulsarClientException {
        invoke();
    }

    private static void invoke() throws PulsarClientException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(Config.SERVICE_URL)
                .authentication(AuthenticationFactory.token(Config.TOKEN))
                .build();
        System.out.println(">> pulsar client created.");

        //创建消费者
        Consumer<byte[]> consumer = client.newConsumer()
                .topic(Config.TOPIC)
                .subscriptionName(Config.SUBSCRIPTION)
                //声明消费模式，默认值为 SubscriptionType.Exclusive
                //可以选择订阅模式包括
                //Exclusive 独占模式
                //Failover 灾备模式（独占模式的主备模式）
                //Shared 共享模式
                //Key_Shared 按照Key分类的共享模式（同一个key的消息会发往固定的partition和消费者）
                .subscriptionType(SubscriptionType.Exclusive)
                //配置从最早开始消费，否则可能会消费不到历史消息
                .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                //配置批量消费参数
                .batchReceivePolicy(BatchReceivePolicy.builder()
                        .maxNumMessages(10)
                        .maxNumBytes(1024 * 1024)
                        .timeout(200, TimeUnit.MILLISECONDS)
                        .build())
                .subscribe();
        System.out.println(">> pulsar consumer created.");

        Messages messages = consumer.batchReceive();
        for (Object messsage : messages) {
            Message<byte[]> msg = (Message<byte[]>)messsage;
            MessageId msgId = msg.getMessageId();
            String value= new String(msg.getValue());
            // TODO:对消息进行处理
            System.out.println("receive msg " + msgId + ",value:" + value);
        }
        consumer.acknowledge(messages);

        consumer.close();
        client.close();

    }

}
