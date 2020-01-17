package com.zjcds.cde.scheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ma
 * @version 1.0
 * @date 2020/1/13 18:16
 */
@Controller
public class LoginController {

    @RequestMapping("/hello")
    public String hello() {
        //这边我们,默认是返到templates下的login.html
        return "login";
    }
}
