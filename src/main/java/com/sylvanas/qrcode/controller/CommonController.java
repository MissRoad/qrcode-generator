package com.sylvanas.qrcode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 *
 * Created by sylvanasp on 2016/11/11.
 */
@Controller
public class CommonController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

}
