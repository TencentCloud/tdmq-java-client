# tdmq-java-client

腾讯云TDMQ Pulsar版 Java Demo

## 使用说明

### 第一步 创建资源

请先在[TDMQ 控制台](https://console.cloud.tencent.com/tdmq/cluster)或者使用云API创建对应资源，包括以下：
- 集群
- 命名空间
- 角色Token
- Topic
- 订阅

### 第二步 
将对应资源信息复制到 Config.java 内

```java
    // Topic请从控制台复制完整路径，补充在persistent://后面
    // 格式为persistent://集群（租户）ID/命名空间/Topic名称
    public static final String TOPIC = "persistent://pulsar-****/namespace/topic";
    // Subscription请在Topic详情页创建和复制
    public static final String SUBSCRIPTION = "subscriptionName";
    // Token请在角色管理复制
    public static final String TOKEN = "eyJr****";
    // service url请在集群管理接入地址处复制
    public static final String SERVICE_URL = "http://pulsar-****.****.tencenttdmq.com:8080";
```

### 第三步
直接在IDE内部运行 `SimpleProducerAndConsumer.java` 的main方法即可快速体验消息生产和消费
