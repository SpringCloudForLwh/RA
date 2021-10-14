package xyz.lwh.spring.cloud.client.service;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;

@Service
public class MyHealthIndicator implements HealthIndicator {

    private Boolean state = true;

    public void setState(Boolean state) {
        this.state = state;
    }


    @Override
    public Health health() {

        if (state){
            return new Health.Builder().up().build();
        }else{
            return new Health.Builder().down().build();
        }
    }
}
