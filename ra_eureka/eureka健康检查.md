# Eureka 健康检查

客户端和服务端通过心跳保持来维护服务状态，而只有服务状态为UP的实例才能被访问到。假如心跳一直正常，但是实例出现了一些异常，无法连接数据库，或者缓存等等，已经无法提供服务了
<br>
所以需要将实际的健康状态更新到服务端。开启eureka健康检查。每次心跳维护的时候上报健康信息。
```
eureka:
    client:
        healthcheck:
            enabled: true
```
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

```java
//
@Service
public class HealthStatusService implements HealthIndicator{
    private Boolean status = true;
    
    public Health health(){
        if (status){
            return new Health.Builder().up().bulid();
        }else{
            return new Health.Builder().down().bulid();
        }
    }
    public String getStatus(){
        return this.status.toString();
    }
}
```