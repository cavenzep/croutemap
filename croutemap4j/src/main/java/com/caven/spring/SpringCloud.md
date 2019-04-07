## Spring Cloud
### 微服务解决
https://www.nginx.com/blog/introduction-to-microservices/
https://blog.csdn.net/lmy86263/article/details/74276330
http://www.ityouknow.com/spring-cloud.html

### 服务注册及发现——Eureka
1. 服务发现模式：客户端、服务端
2. 服务注册方式：自注册、第三方
作用：实现服务治理（服务注册与发现）
简介：Spring Cloud Eureka是Spring Cloud Netflix项目下的服务治理模块。
由两个组件组成：Eureka 服务端和 Eureka 客户端
 - Eureka 服务端，用作服务注册中心，支持集群部署。
 - Eureka 客户端，是一个 Java 客户端，用来处理服务注册与发现。
 
 
### 客户端负载均衡——Rabbion
负载均衡算法供7种

|策略名|	策略声明|	策略描述|	实现说明|
|:--|:--|:--|:--|
|BestAvailableRule|	public class BestAvailableRule extends ClientConfigEnabledRoundRobinRule|选择一个最小的并发请求的server|逐个考察Server，如果Server被tripped了，则忽略，在选择其中ActiveRequestsCount最小的server|
|AvailabilityFilteringRule|	public class AvailabilityFilteringRule extends PredicateBasedRule|	过滤掉那些因为一直连接失败的被标记为circuit tripped的后端server，并过滤掉那些高并发的的后端server（active connections 超过配置的阈值）|	使用一个AvailabilityPredicate来包含过滤server的逻辑，其实就就是检查status里记录的各个server的运行状态|
|WeightedResponseTimeRule|	public class WeightedResponseTimeRule extends RoundRobinRule|	根据响应时间分配一个weight，响应时间越长，weight越小，被选中的可能性越低。|	一个后台线程定期的从status里面读取评价响应时间，为每个server计算一个weight。Weight的计算也比较简单responsetime减去每个server自己平均的responsetime是server的权重。当刚开始运行，没有形成status时，使用roubine策略选择server。|
|RetryRule|	public class RetryRule extends AbstractLoadBalancerRule|对选定的负载均衡策略机上重试机制。|	在一个配置时间段内当选择server不成功，则一直尝试使用subRule的方式选择一个可用的server|
|RoundRobinRule|	public class RoundRobinRule extends AbstractLoadBalancerRule|	roundRobin方式轮询选择server|轮询index，选择index对应位置的server|
|RandomRule|	public class RandomRule extends AbstractLoadBalancerRule|随机选择一个server|	在index上随机，选择index对应位置的server|
|ZoneAvoidanceRule|	public class ZoneAvoidanceRule extends PredicateBasedRule|	复合判断server所在区域的性能和server的可用性选择server|使用ZoneAvoidancePredicate和AvailabilityPredicate来判断是否选择某个server，前一个判断判定一个zone的运行性能是否可用，剔除不可用的zone（的所有server），AvailabilityPredicate用于过滤掉连接数过多的Server。|


原理
1. Ribbon 会从 Eureka Client 里获取到对应的服务列表。
2. Ribbon 使用负载均衡算法获得使用的服务。
3. Ribbon 调用对应的服务。


### 声明式服务调用——Feign
Feign的一个关键机制就是使用了动态代理，原理如下：
1. 首先，如果你对某个接口定义了 @FeignClient 注解，Feign 就会针对这个接口创建一个动态代理。
2. 接着你要是调用那个接口，本质就是会调用 Feign 创建的动态代理，这是核心中的核心。
3. Feign的动态代理会根据你在接口上的 @RequestMapping 等注解，来动态构造出你要请求的服务的地址。
4. 最后针对这个地址，发起请求、解析响应。


### Feign 是怎么和 Ribbon、Eureka 整合的？
1. 首先，用户调用 Feign 创建的动态代理。
2. 然后，Feign 调用 Ribbon 发起调用流程
	- 首先，Ribbon 会从 Eureka Client 里获取到对应的服务列表。
	- 然后，Ribbon 使用负载均衡算法获得使用的服务。
	- 最后，Ribbon 调用 Feign ，而 Feign 调用 HTTP 库最终调用使用的服务。

原理
1. LoadBalancerFeignClient ，Spring Cloud 实现 Feign Client 接口的二次封装，实现对 Ribbon 的调用。
2. FeignLoadBalancer ，Ribbon 的集成。

### 服务容错保护断路器——Hystrix
作用：断路器，保护系统，控制故障范围。
简介：Hystrix 是一个延迟和容错库，旨在隔离远程系统，服务和第三方库的访问点，当出现故障是不可避免的故障时，停止级联故障并在复杂的分布式系统中实现弹性。

Hystrix 有两种隔离策略：
1. 线程池隔离
2. 信号量隔离
实际场景下，使用线程池隔离居多，因为支持超时功能。

Hystrix 提供缓存功能，作用是：
1. 减少重复的请求数。
2. 在同一个用户请求的上下文中，相同依赖服务的返回数据始终保持一致。


### API网关——Zuul
作用：API 网关，路由，负载均衡等多种作用。
简介：类似 Nginx ，反向代理的功能，不过 Netflix 自己增加了一些配合其他组件的特性。
在微服务架构中，后端服务往往不直接开放给调用端，而是通过一个 API网关根据请求的 url ，路由到相应的服务。
当添加API网关后，在第三方调用端和服务提供方之间就创建了一面墙，这面墙直接与调用方通信进行权限控制，后将请求均衡分发给后台服务端。


### 分布式配置中心——Config Server
作用：配置管理
简介：Spring Cloud Config 提供服务器端和客户端。服务器存储后端的默认实现使用 Git ，
	因此它轻松支持标签版本的配置环境，以及可以访问用于管理内容的各种工具。
这个还是静态的，得配合 Spring Cloud Bus 实现动态的配置更新。


### 消息总线——Bus

http://www.ityouknow.com/springcloud/2017/05/26/springcloud-config-eureka-bus.html

### 服务交互——Cloud Stream
作用：构建消息驱动能力的框架
- Binder
	应用与消息中间件之间的粘合剂，很方便的连接中间件，可以动态的改变消息的 destinations
	（对应于 Kafka 的topic，Rabbit MQ 的 exchanges）
- Publish-Subscribe
	生产者把消息通过某个 topic 广播出去（Spring Cloud Stream 中的 destinations）。
	其他的微服务，通过订阅特定 topic 来获取广播出来的消息来触发业务的进行。
- Consumer Groups
	微服务中动态的缩放同一个应用的数量以此来达到更高的处理能力是非常必须的。对于这种情况，同一个事件防止被重复消费，
	只要把这些应用放置于同一个 “group” 中，就能够保证消息只会被其中一个应用消费一次
- Durability
	消息事件的持久化是必不可少的。Spring Cloud Stream 可以动态的选择一个消息队列是持久化，还是 present。
- Bindings
	bindings 是我们通过配置把应用和spring cloud stream 的 binder 绑定在一起，
	只需要修改 binding 的配置来达到动态修改topic、exchange、type等一系列信息而不需要修改一行代码。

### 分布式服务跟踪——Sleuth
参考：
https://zipkin.io/pages/instrumenting.html

Sleuth集成ZipKin和Rabbit


### 服务监控
http://www.ityouknow.com/springboot/2018/02/06/spring-boot-actuator.html