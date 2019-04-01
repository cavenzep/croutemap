# Spring
## 架构
Core container：Beans、Core、Context、SpEL(Spring Expression Language)
Data Access：JDBC、JMS、ORM
Web：
AOP：
Aspects：
Test：

## IoC和DI

## IoC 容器
类型1：BeanFactory
最常用的：XmlBeanFactory
1. 加载配置文件，解析成 BeanDefinition 放在 Map 里
2. 调用 getBean 的时候，从 BeanDefinition 所属的 Map 里，
拿出 Class 对象进行实例化，同时，如果有依赖关系，将递归调用 getBean 方法 —— 完成依赖注入

类型2：ApplicationContext
三种较常见的：ClassPathXmlApplicationContext、FileSystemXmlApplicationContext、XmlWebApplicationContext


## Spring Bean
### 配置方式
1. XML：XML格式文件配置
2. 注解：`<context:annotation-config/>`开启后，在代码中使用特性的注解进行配置
3. Java Config：@Bean 和 @Configuration

## Spring Transaction
### 事务
数据库——ACID：原子性、一致性、隔离性、持久性
分布式——CAP：一致性、可用性、容错性

#### 隔离性（Isolation）
Read uncommitted：读未提交——>出现脏读(读取到其他事务未提交的数据)——>一个事务要等另一个事务提交后才能读取新数据
Read committed：读已提交——>不可重复读(数据更新UPDATE前后同一事务中的两次读数据不一样)——>读事务必须等到更新事务完成
Repeatable read：重复读——>幻速(数据写入INSERT、删除DELETE前后同一事务中的两次读数据不一样)——>CRUD操作都需要分离控制
Serializable：序列化——>
小结：1. 不可重复读的和幻读很容易混淆，不可重复读侧重于修改，幻读侧重于新增或删除；
	 2. 解决不可重复读的问题只需锁住满足条件的行，解决幻读需要锁表；
	 3. mysql默认是repeatable-read，Oracle 默认采用是Read committed
	 4. 上述问题都在事务交叉场景中出现；
	 5. mysql基于MVCC来进行事务控制，开销比repeatable-read小，效率高；
	 
## Spring版本更新比较
https://blog.csdn.net/qq_19301269/article/details/78573519
### Spring4
https://jinnianshilongnian.iteye.com/category/301336
1. Spring4新特性——泛型限定式依赖注入
2. Spring4新特性——核心容器的其他改进
	- @Conditional 引入
	- @Order 更多使用方式
	- 基于CGLIB的类代理不再要求类必须有空参构造器
3. Spring4新特性——Web开发的增强
	- @RestController
	- mvc:annotation-driven配置变化改为-连接
	- 提供AsyncRestTemplate用于客户端非阻塞异步支持
4. Spring4新特性——集成Bean Validation 1.1(JSR-349)到SpringMVC
5. Spring4新特性——Groovy Bean定义DSL
6. Spring4新特性——更好的Java泛型操作API 
7. Spring4新特性——JSR310日期API的支持
	- Clock、Instant
	- LocalDateTime、LocalDate、LocalTime、ZonedDateTime
	- Duration
8. Spring4新特性——注解、脚本、任务、MVC等其他特性改进
	- websocket
	- 注解
	- 脚本支持

### Spring5
http://ifeve.com/spring-5-new/
