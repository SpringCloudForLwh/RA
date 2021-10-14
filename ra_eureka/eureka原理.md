# Eureka原理
Eureka 是古希腊词（都喜欢用古希腊？）意思是："发现了"
eureka分两部分，客户端和服务端
## eureka提供了以下功能
- 注册 register
- 心跳 renew
- 注册列表 fetch registry
- 注销 cancel
- 延迟 time lag
- 连接协议 http
### 注册 register
想要参与eureka服务注册与发现，客户端需要向服务端提交自己的信息<br>
注册发生在第一次心跳的时候
### 心跳 renew
客户端需要每30秒发送一次心跳来续租，通知eureka服务端，当前实例依然存活<br>
如果客户端在90秒内没有提交心跳，则认为该实例已死亡，从注册表中剔除
### 注册表 fetch registry
eureka客户端从服务端获取服务注册列表，并且缓存在本地<br>
依此来查找其他服务，通过获取上一个 获取周期 和当前获取周期之间进行增量更新，可定期更新（30秒）<br>
节点信息在服务器中保存更长的时间（大概3分钟），因此获取节点信息的时候可能会多次返回相同的实例，客户端需要有去重能力
<br>在获取增量之后，客户端会比较服务端返回的实例数量，来对比是否需要重新拉去注册表。
### 注销 cancel
eureka客户端在关闭的时候，向服务端发送cancel请求，请求将本实例剔除
### 延迟 time lag
同步有延迟，客户端的操作需要一定时间才能反映到eureka服务端上，然后其他客户端才能增量更新。由于eureka是定时刷新缓存信息，所有客户端对数据的修改，最多需要2分钟来传播到其他客户端。
### 传输协议
eureka采用http协议，REST风格，通常情况下使用JSON来完成节点间的数据传输


## 相关配置
客户端配置
```properties
#心跳间隔，默认30s
eureka.instance.lease-renewal-interval-in-seconds=5
#拉取注册表间隔,默认30s,对于需要快速更新服务注册列表的应用，可以适当缩小该值
eureka.client.registry-fetch-interval-seconds=5
#心跳过期时间 默认90s
eureka.instance.lease-expiration-duration-in-seconds=60
```
服务端配置
```properties
#自我保护 当服务短时间内大量消亡，没有心跳，则认为eureka客户端网络有问题，出于保护<br>
#不剔除这部分服务，因为有可能服务还能提供，只是暂时无法维护心跳。宁可放过，也不杀错。
eureka.server.enable-self-preservation=true/false
#服务失效间隔 ms
eureka.server.eviction-interval-timer-in-ms=3000
```

### 自我保护机制
客户端每分钟续约数量小于客户端总数的85%时，触发保护机制

每分钟心跳次数（renewsLastMin） 小于 numberOfRenewsPerMinThreshold时,并且开启保护模式，不再自动过期客户端实例。
```
expectedNumberOfRenewsPerMin = 当前应用数 x 2 ;
renewalPercentThreshold = @Value{eureka.server.renewalPercentThreshold:0.85}
numberOfRenewsPerMinThreshold =expectedNumberOfRenewsPerMin x renewalPercentThreshold  
```
为什么要实例数乘以2，因为默认30s心跳一次，一个实例一分钟会提交2次心跳
相关代码：
```java
AbstractInstanceRegistry
PeerAwareInstanceRegistryImpl
```


# 元数据
eureka元数据有2种
- 标准元数据 
    - 主机名，端口，状态，健康检查等信息
- 客户自定义元数据
    - 通过eureka.instance.matedata-map.{key}={value}
## <br>
一般情况自定义元数据并不会有什么影响，除非开发在编码时明确元数据含义，做出不同的动作。

## 网络配置
1.ip配置
```
eureka:
    instance:
        perfer-ip-address: true/false
true表示将ip注册到eureka，false表示将hostname注册到eureka
```
2.eureka多网卡选择，如果配置指定网卡eth0，则client只能通过eth0来注册服务

3.指定ip
```
eureka: 
    instance:
        perfer-ip-address: true
        ip-address: 实际ip
    如果设置了ip，则元数据中能够看到该ip，其他应用也是通过ip来调用本服务
```



    
 