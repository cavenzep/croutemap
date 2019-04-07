# RabbitMQ
## 概述及基本原理
参考:
https://www.jianshu.com/p/79ca08116d57
https://blog.csdn.net/qq_30764991/article/details/80573352
https://www.sojson.com/blog/48.html
https://blog.csdn.net/love905661433/column/info/31759

## Consumer Acknowledgements
参考：https://www.rabbitmq.com/confirms.html#acknowledgement-modes
- Consumer
1. basic.consume：basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map<String, Object> arguments, Consumer callback)


- Ack
1. basic.ack：basicAck(long deliveryTag, boolean multiple)
2. basic.nack：basicNack(long deliveryTag, boolean multiple, boolean requeue)
3. basic.reject：basicReject(long deliveryTag, boolean requeue)

NOTE：Manual, Auto
Therefore, automatic message acknowledgement should be considered unsafe and not suitable for all workloads.

## Publisher Confirms
参考：https://www.rabbitmq.com/confirms.html#acknowledgement-modes
- 用法
rabbitTemplate.setReturnCallback
rabbitTemplate.setConfirmCallback

为了确认Confirms消息, CachingConnectionFactory 的publisherConfirms 属性也需要设置为true，
确认的消息会根据它注册的RabbitTemplate.ConfirmCallback setConfirmCallback回调发送到给客户端。
一个RabbitTemplate也仅能支持一个ConfirmCallback。

### 配置
```bash
# 开启发送确认
spring.rabbitmq.publisher-confirms=true
# 开启发送失败退回
spring.rabbitmq.publisher-returns=true
# 开启ACK
spring.rabbitmq.listener.direct.acknowledge-mode=manual
spring.rabbitmq.listener.simple.acknowledge-mode=manual
```


## docker run
docker run -d --name rabbitmq --publish 5671:5671 \
 --publish 5672:5672 --publish 4369:4369 \
 --publish 25672:25672 --publish 15671:15671 --publish 15672:15672 \
 rabbitmq:3-management
 
## rabbitmq tutorials
https://www.rabbitmq.com/getstarted.html
 
## 项目
spring-workbench/spring-amqp

## 生产配置
- 集群
- 高可用
- 消息丢失

## 好处
1. 应用解耦（系统拆分）
2. 异步处理（预约挂号业务处理成功后，异步发送短信、推送消息、日志记录等）
3. 消息分发
4. 流量削峰
5. 消息缓冲

## 面试题
1. 处理消息丢失的问题
	- 生产者
	- mq持久化
	- 消费者
2. 消息重复问题
	- 生产者confirm模式
	- 消费者ack
3. 高可用架构