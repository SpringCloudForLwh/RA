## 服务注册与发现

### eureka单节点搭建

1.pom依赖
```$xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <!-- eureka server依赖已经包含了 spring-boot-starter-web依赖 -->
```

2.application.yml
```$sh
eureka:
    client:
        # 是否将自己注册到eureka: false 注册中心自己不需要注册自己
        register-with-eureka: false
        # 是否拉取服务注册列表
        fetch-registry: false
        server-url: 
            defaultZone: http://localhost:8888/eureka/
```

3.application.properties

```$properties
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
eureka.client.server-url.defaultZone=http://localhost:8888/eureka/
```

4.启动类添加注解
```$java
//将本app标识为eurekaServer
@EnableEurekaServer     
//eureka server 会暴露一些端点供client调用，用于注册、获取注册表、维护心跳等
```