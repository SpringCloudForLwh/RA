# Spring Boot 2.x Actuator监控应用

## 开启监控
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

## 默认端点
Spring Boot 2.x中，Actuator默认暴露health和info端口，不能满足监控需求，需要额外开放端点

```properties
management.endpoints.web.exposure.include=*
```

```properties
actuator
beans
caches
health
info
conditions
configprops
env
loggers
heapdump
threaddump
metrics
scheduledtasks
mappings
refresh
features
service-registry
```
api端点功能
```properties
health 展示系统状态 {"status":"UP/DOWN"}

shutdown 关闭springboot应用
可开启关闭 management.endpoint.shutdown.enabled=false/true

autoconfig 获取应用自动化配置

beans 获取spring上下文创建的beans

configprops 获取应用中配置的属性信息

env 获取应用用到的环境变量

mappings 获取spring web中注册的控制器mapping

info 获取应用自定义信息

metrics 获取应用指标信息
{"names":["http.server.requests","jvm.buffer.count","jvm.buffer.memory.used","jvm.buffer.total.capacity","jvm.classes.loaded","jvm.classes.unloaded","jvm.gc.live.data.size","jvm.gc.max.data.size","jvm.gc.memory.allocated","jvm.gc.memory.promoted","jvm.gc.pause","jvm.memory.committed","jvm.memory.max","jvm.memory.used","jvm.threads.daemon","jvm.threads.live","jvm.threads.peak","jvm.threads.states","logback.events","process.cpu.usage","process.start.time","process.uptime","system.cpu.count","system.cpu.usage","tomcat.sessions.active.current","tomcat.sessions.active.max","tomcat.sessions.alive.max","tomcat.sessions.created","tomcat.sessions.expired","tomcat.sessions.rejected"]}
metrics/jvm.memory.max

threaddump 获取线程dump

```