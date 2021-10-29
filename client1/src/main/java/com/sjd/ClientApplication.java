package com.sjd; /**
 * @projectname authDemo
 * @author xinao
 * @create 2021/10/29
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * @author sunjidong
 * @Date 2021/10/29
 * @Describtion
 **/
@EnableOAuth2Sso
@SpringBootApplication
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class,args);
    }
}
