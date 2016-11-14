package com.sylvanas.qrcode.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 *
 * Created by sylvanasp on 2016/11/11.
 */
@Controller
public class CommonController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/hello",produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String hello() {
        return "Hello,World!";
    }

}
