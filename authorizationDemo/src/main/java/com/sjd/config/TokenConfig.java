package com.sjd.config;/**
 * @projectname authDemo
 * @author xinao
 * @create 2021/10/22
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author sunjidong
 * @Date 2021/10/22
 * @Describtion
 **/
//@Configuration
public class TokenConfig {

    /*@Bean
    public TokenStore tokenStore(){
        //使用基于内存的tokenStore
        return new InMemoryTokenStore();
    }*/

    @Bean
    public TokenStore tokenStore(){
        //使用基于jwt的tokenStore
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * @author sunjidong
     * @date 2021/10/25
     * @Describtion 用于将 access_token转换成jwtToken
     * @param
     * @return
    */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("sjd");
        return converter;
    }

}
