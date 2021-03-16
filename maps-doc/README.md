# swagger-bootstarp-ui & knife4j集成web框架示例项目

>大家好,我在GitChat中开了一篇关于[《Knife4j 及 Swagger 在企业开发中的实践》](https://gitbook.cn/gitchat/activity/5f86b4cb1772090f20e13b03)
>地址：[https://gitbook.cn/gitchat/activity/5f86b4cb1772090f20e13b03](https://gitbook.cn/gitchat/activity/5f86b4cb1772090f20e13b03)
>
>欢迎对 SpringFox、Swagger、Knife4j 感兴趣以及想了解的人员一起来chat 
>


`swagger-bootstrap-ui`自1.9.6版本后,正式更名为`knife4j`

为了契合当前微服务架构的发展,将后端Java代码和前端Ui界面进行了分离,项目的使用上也有了稍微的区别,主要分一下几种情况

- 不使用增强功能,纯粹换一个swagger的前端皮肤
- Spring Boot项目单体架构使用增强功能
- Spring Cloud微服务架构

## 模块说明

本项目列举了所有场景下的demo示例,模块说明

| 模块                            | 说明                                                         |
| ------------------------------- | ------------------------------------------------------------ |
| knife4j-spring-boot-demo        | 在Spring Boot架构下集成knife4j的项目示例                     |
| knife4j-spring-boot-single-demo | 在Spring Boot单体架构下集成knife4j的项目示例                 |
| knife4j-spring-cloud-gateway    | 在Spring Cloud微服务架构下通过gateway网集成knife4j的示例     |
| swagger-bootstrap-ui-demo-mvc   | 在Spring MVC模式下集成swagger-bootstrap-ui                   |
| swagger-bootstrap-ui-demo       | 在Spring Boot单体架构下集成swagger-bootstrap-ui              |
| swagger-bootstrap-ui-gateway    | 在Spring Cloud微服务架构下通过gateway网关集成swagger-bootstrap-ui |
| swagger-bootstrap-ui-zuul       | 在Spring Cloud微服务架构下通过zuul网关集成swagger-bootstrap-ui |

## 业务场景

### 不使用增强功能,纯粹换一个swagger的前端皮肤

不使用增强功能,纯粹换一个swagger的前端皮肤，这种情况是最简单的,你项目结构下无需变更

可以直接引用swagger-bootstrap-ui的最后一个版本1.9.6或者使用knife4j-spring-ui

老版本引用

```xml
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>swagger-bootstrap-ui</artifactId>
    <version>1.9.6</version>
</dependency>
```

新版本引用

```xml
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-ui</artifactId>
    <version>${lastVersion}</version>
</dependency>
```

### Spring Boot项目单体架构使用增强功能

在Spring Boot单体架构下,knife4j提供了starter供开发者快速使用

```xml
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>${knife4j.version}</version>
</dependency>
```

该包会引用所有的knife4j提供的资源，包括前端Ui的jar包

### Spring Cloud微服务架构

在Spring Cloud的微服务架构下,每个微服务其实并不需要引入前端的Ui资源,因此在每个微服务的Spring Boot项目下,引入knife4j提供的微服务starter

```xml
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-micro-spring-boot-starter</artifactId>
    <version>${knife4j.version}</version>
</dependency>
```

在网关聚合文档服务下,可以再把前端的ui资源引入

```xml
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>${knife4j.version}</version>
</dependency>
```

## 另外说明

不管是knife4j还是swagger-bootstrap-ui

对外提供的地址依然是doc.html

访问http://ip:port/doc.html即可查看文档

**这是永远不会改变的**