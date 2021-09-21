package com.tencent.tdmq.demo.cloud;

import org.apache.pulsar.client.api.*;

/**
 * 简单的生产和消息例子
 *
 */

public class SimpleProducerAndConsumer {


	public static void main(String[] args) throws PulsarClientException {
        invoke();
	}

	private static void invoke() throws PulsarClientException {

        // 一个Pulsar client对应一个客户端链接
        // 原则上一个进程一个client，尽量避免重复创建，消耗资源
        // 关于客户端和生产消费者的最佳实践，可以参考官方文档 https://cloud.tencent.com/document/product/1179/58090
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(Config.SERVICE_URL)
                .authentication(AuthenticationFactory.token(Config.TOKEN))
                .build();
        System.out.println(">> pulsar client created.");

        //创建消费者
        Consumer<byte[]> consumer = client.newConsumer()
                .topic(Config.TOPIC)
                .subscriptionName(Config.SUBSCRIPTION)
                //声明消费模式为exclusive（独占）模式
                .subscriptionType(SubscriptionType.Exclusive)
                //配置从最早开始消费，否则可能会消费不到历史消息
                .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                .subscribe();
        System.out.println(">> pulsar consumer created.");

        //创建生产者
        Producer<byte[]> producer = client.newProducer()
                .topic(Config.TOPIC)
                .create();
        System.out.println(">> pulsar producer created.");

        //生产5条消息
        for (int i = 0; i < 5; i++) {
            String value = "my-sync-message-" + i;
            //发送消息
            MessageId msgId = producer.newMessage().value(value.getBytes()).send();
            System.out.println("deliver msg " + msgId + ",value:" + value);
        }
        //关闭生产者
        producer.close();

        //消费5条消息
        for (int i = 0; i < 5; i++) {
            //接收当前offset对应的一条消息
            Message<byte[]> msg = consumer.receive();
            MessageId msgId = msg.getMessageId();
            String value = new String(msg.getValue());
            System.out.println("receive msg " + msgId + ",value:" + value);
            //接收到之后需要ack，否则该消息会被视为堆积
            //经过TTL时间后，该消息会被自动ack，TTL在控制台【命名空间】处设置
            consumer.acknowledge(msg);
        }

        //关闭消费进程
        consumer.close();
        //关闭客户端
        client.close();
    }

}

