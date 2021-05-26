package com.tencent.tdmq.demo.cloud;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.NetModel;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * 简单的生产和消息例子 test
 *
 */

public class SimpleProducerAndCosnumer {

	public static void invork() throws PulsarClientException {
		Map<String, String> authParams = new HashMap<>();
		authParams.put("secretId", "*********************************");
		authParams.put("secretKey", "*********************************");
		authParams.put("region", "ap-guangzhou");
		PulsarClient client = PulsarClient.builder()
				.authenticationCloud("org.apache.pulsar.client.impl.auth.AuthenticationCloudCam", authParams)
				.netModelKey("1300*****0/vpc-******/subnet-********")// 填写接入点的路由ID
				.serviceUrl("pulsar://*.*.*.*:6000")// 填写接入点的地址
				.build();
		// 创建消费者对象
		Consumer<byte[]> consumer = client.newConsumer()
				.topic("persistent://1300****30/default/mytopic")//命名规则:appid/namespace/topic
				.subscriptionName("sub1")
				.subscribe();
		// 创建生产者对象
		Producer<byte[]> producer = client.newProducer()
				.topic("persistent://1300****30/default/mytopic")
				.create();

		for (int i = 0; i < 5; i++) {
			String value = "my-sync-message-" + i;
			System.out.println("");
			MessageId msgId = producer.newMessage().value(value.getBytes()).send();
			System.out.println("produce sync msg id:" + msgId + ", value:");
		}
		producer.close();
		for (int i = 0; i < 5; i++) {
			Message<byte[]> msg = consumer.receive();
			String msgId = msg.getMessageId().toString();
			String value = new String(msg.getValue());
			System.out.println("receive msg " + msgId + ",value:" + value);
			consumer.acknowledge(msg);
		}
		// 关闭
		consumer.close();
		client.close();
	}

	public static void main(String[] args) throws JoranException, PulsarClientException {
		String logbackFile = "../conf/logback.xml";
		if (logbackFile != null) {
			LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(lc);
			lc.reset();
			configurator.doConfigure(logbackFile);
		}
		invork();

	}

}
