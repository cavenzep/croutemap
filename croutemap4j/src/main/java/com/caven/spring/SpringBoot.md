## Spring Boot
### @SpringBootApplication
1. Configuration
	- JavaConfig方式IoC配置方式，被注解的类内部包含有一个或多个被@Bean注解的方法，
	这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义
2. ComponentScan
3. EnableAutoConfiguration
	- AutoConfiguration相关注解

### Spring Boot启动详解
@EnableAutoConfiguration的强大功能：
1. @Import(EnableAutoConfigurationImportSelector.class)，将所有符合条件的@Configuration配置都加载到当前SpringBoot创建并使用的IoC容器
2. SpringFactoriesLoader(从指定的配置文件META-INF/spring.factories加载配置)
3. 使用log trace来看看启动日志，更好理解

参考：
https://juejin.im/post/5b679fbc5188251aad213110
https://www.cnblogs.com/zheting/p/6707035.html


### Spring Boot 2.X 有什么新特性？
1. 起步 JDK 8 和支持 JDK 9
2. 第三方库的升级
3. Reactive Spring
4. HTTP/2 支持
5. 配置属性的绑定
6. Gradle 插件
7. Actuator 改进
8. 数据支持的改进
9. Web 的改进
10. 支持 Quartz 自动配置
11. 测试的改进

