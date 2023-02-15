package com.zbom;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a")
public class Controller {
    @RequestMapping("/b")
    public String test(String s) {
        System.out.println("传入的值："+s);
        return s;
    }
}
