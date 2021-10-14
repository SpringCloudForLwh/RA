package xyz.lwh.spring.cloud.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.lwh.spring.cloud.client.service.MyHealthIndicator;

@RestController
@RequestMapping("/cgh")
public class HealthController {

    @Autowired
    MyHealthIndicator myHealthIndicator;

    @RequestMapping("/up")
    public String  up(){

        myHealthIndicator.setState(true);

        return "ok";
    }

    @RequestMapping("/down")
    public String  down(){

        myHealthIndicator.setState(false);

        return "ok";
    }


}
