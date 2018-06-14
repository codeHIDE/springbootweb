package com.jyc.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("freemarker")
public class FreemarkerController {

    @RequestMapping(value = "hello", method = RequestMethod.GET)  
    public String hello(Map<String,Object> map) {
        
        map.put("msg", "Hello Freemarker");
        return "hello";
    }
}