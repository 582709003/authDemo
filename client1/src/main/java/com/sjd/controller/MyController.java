package com.sjd.controller;/**
 * @projectname authDemo
 * @author xinao
 * @create 2021/10/29
 */

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunjidong
 * @Date 2021/10/29
 * @Describtion
 **/
@RestController
public class MyController {

    @RequestMapping("/test")
    public String test(Authentication authentication){
        return authentication.getPrincipal().toString();

    }

}
