package com.zbom;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/c")
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/d")
    @HystrixCommand(fallbackMethod = "hystrixCommand")
    public String test(String s) {
        System.out.println("输入的值="+s);
        return restTemplate.getForObject("http://EUREKA-SERVICE/a/b?s=" + s, String.class);
    }

    /**
     * 熔断器回调
     */
    public String hystrixCommand(String id) {
        return "hi," + id + ", error!";
    }
}
