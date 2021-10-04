# Eureka高可用搭建
高可用：通过运行多个eureka实例并且相互注册的方式来实现eureka高可用。Server各节点会彼此增量更新服务注册信息，从而保证节点中的数据一致（最终一致）

## 搭建步骤

### 1.准备工作
- 准备多个服务器节点部署eureka，并且配置好hostname，以及hostname映射。eureka最好是使用hostname来配置，使用ip会有问题。
- 本人单机情况下，在hosts文件中配置127.0.0.1多个不同的hostname
### 2.Server配置
节点1：
```properties
eureka:
    client:
        # 是否注册到其他server
        register-with-eureka: true
        # 是否拉取服务注册列表
        fetch-registry: true
        serviceUrl:
            defaultZone: http://lwh2.com:8888/eureka/,http://lwh3.com:8988/eureka/
    instance:
        hostname: lwh1.com
management:
    endpoint:
        shutdown:
            enabled: true
server:
    port: 8788                
```
节点2：
```properties
eureka:
    client:
        # 是否注册到其他server
        register-with-eureka: true
        # 是否拉取服务注册列表
        fetch-registry: true
        serviceUrl:
            defaultZone: http://lwh2.com:8988/eureka/,http://lwh3.com:8788/eureka/
    instance:
        hostname: lwh2.com
management:
    endpoint:
        shutdown:
            enabled: true
server:
    port: 8888                
```
节点3：
```properties
eureka:
    client:
        # 是否注册到其他server
        register-with-eureka: true
        # 是否拉取服务注册列表
        fetch-registry: true
        serviceUrl:
            defaultZone: http://lwh2.com:8788/eureka/,http://lwh3.com:8888/eureka/
    instance:
        hostname: lwh3.com
management:
    endpoint:
        shutdown:
            enabled: true
server:
    port: 8988                
```