package com.tencent.tdmq.demo.cloud;

/**
 * MQ 配置
 */
public class Config {
    // Topic请从控制台复制完整路径，补充在persistent://后面
    // 格式为persistent://集群（租户）ID/命名空间/Topic名称
    public static final String TOPIC = "persistent://pulsar-****/namespace/topic";
    // Subscription请在Topic详情页创建和复制
    public static final String SUBSCRIPTION = "subscriptionName";
    // Token请在角色管理复制
    public static final String TOKEN = "eyJr****";
    // service url请在集群管理接入地址处复制
    public static final String SERVICE_URL = "http://pulsar-****.****.tencenttdmq.com:8080";

}

