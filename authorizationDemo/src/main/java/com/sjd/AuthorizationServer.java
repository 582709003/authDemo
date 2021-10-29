package com.sjd;/**
 * @projectname authDemo
 * @author xinao
 * @create 2021/10/22
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author sunjidong
 * @Date 2021/10/22
 * @Describtion 启动类
 **/
@SpringBootApplication
public class AuthorizationServer {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AuthorizationServer.class, args);

    }

}
