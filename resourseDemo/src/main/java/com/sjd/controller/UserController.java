package com.sjd.controller;/**
 * @projectname authDemo
 * @author xinao
 * @create 2021/10/26
 */

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunjidong
 * @Date 2021/10/26
 * @Describtion
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication){
        return authentication.getPrincipal();
    }

}
