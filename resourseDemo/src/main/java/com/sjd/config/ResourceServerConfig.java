package com.sjd.config;/**
 * @projectname authDemo
 * @author xinao
 * @create 2021/10/22
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @author sunjidong
 * @Date 2021/10/22
 * @Describtion
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_KEY = "aaaa";

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_KEY)
                //非jwt格式
                .tokenServices(tokenService())
                //jwt方式的验证token
                .tokenStore(tokenStore)
                .stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/user/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated();
        super.configure(http);
    }


    //配置accessToken远程验证策略
    @Bean
    public ResourceServerTokenServices tokenService(){
        RemoteTokenServices services = new RemoteTokenServices();
        services.setClientId("client");
        services.setClientSecret("112233");
        services.setCheckTokenEndpointUrl("http://127.0.0.1:8080/oauth/check_token");
        return services;
    }

   /* public ResourceServerTokenServices tokenService(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setTokenStore(tokenStore);
        return services;
    }*/

}
