package com.tencent.tdmq.demo.cloud;

/**
 * MQ 配置
 */
public class Config {
    // Topic请从控制台复制完整路径，补充在persistent://后面
    public static final String TOPIC = "persistent://pulsar-b4edpk92pbee/ns1/topic01";
    // Subscription请在Topic详情页创建和复制
    public static final String SUBSCRIPTION = "subscription";
    // Token请在角色管理复制
    public static final String TOKEN = "eyJrZXlJZCI6InB1bHNhci1iNGVkcGs5MnBiZWUiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwdWxzYXItYjRlZHBrOTJwYmVlX3Rlc3QifQ.BvvS_HGmJugR2_WV8Fn4KuP7aRDMaXQr8FzIu_EiWus";
    // service url请在集群管理
    public static final String SERVICE_URL = "http://pulsar-b4edpk92pbee.tdmq.ap-gz.public.tencenttdmq.com:8080";

}

