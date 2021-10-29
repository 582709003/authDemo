package com.sjd.config;/**
 * @projectname authDemo
 * @author xinao
 * @create 2021/10/22
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @author sunjidong
 * @Date 2021/10/22
 * @Describtion 认证服务配置类
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

   /* @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private ClientDetailsService clientDetailsService;*/

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @author sunjidong
     * @date 2021/10/22
     * @Describtion 令牌端点安全约束
     * @param
     * @return
    */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    /**
     * @author sunjidong
     * @date 2021/10/22
     * @Describtion 用来配置客户端详情服务的，你可以把第三方详情信息写死在这里或者通过数据库来存储调取详情信息；不是任何人都可以来申请
     *              令牌的，我们需要认证这个第三方是不是已经和我们取得合作
     * @param
     * @return
    */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //这里其实就是配置ClientDetailsService的方式，是放在内存还是放在数据库，目前只有这两种实现方式
        clients.inMemory()
                // 配置client_id
                .withClient("client")
                // 配置client_secret
                .secret(passwordEncoder.encode("112233"))
                //可以访问哪些资源
                .resourceIds("aaaa")
                // 配置访问token的有效期,一般由授权服务器端控制
                .accessTokenValiditySeconds(3600)
                // 配置刷新token的有效期,一般由授权服务器端控制
                .refreshTokenValiditySeconds(864000)
                // 配置redirect_uri,用于授权成功后的跳转
                 //.redirectUris("http://www.baidu.com")
                // 单点登录时配置
                .redirectUris("http://localhost:8082/login")
                // 该字段只适用于grant_type="authorization_code"的情况,当用户登录成功后,
                // 若该值为'true'或支持的scope值,则会跳过用户Approve的页面, 直接授权.
                .autoApprove(true)
                // 配置申请的权限范围,在资源服务器那里有一个匹配这个all的配置，当你有这个all的权限，资源服务器才会允许你获取用户信息
                // 这个all你随便起名字，只是一个标识，标识和资源服务器那边对应的上就可以了
                .scopes("all")
                // 配置grant_type,表示授权类型；refresh_token：这是为了返回的token中带有刷新令牌，
                // accesstoken过期后可以使用刷新令牌重新获取accessToken
                .authorizedGrantTypes("authorization_code", "password", "refresh_token");
               // .and().withClient()  如果需要配置多个client
    }

    /**
     * @author sunjidong
     * @date 2021/10/22
     * @Describtion 用来配置令牌的访问端点和令牌服务；
     * AuthorizationServerEndpointsConfigurer这个对象的实例可以完成令牌服务以及令牌服务各个端点的配置；
     * 配置授权类型
     *  AuthorizationServerEndpointsConfigurer对于不同类型的授权，也需要配置不同的属性
     *      authticationmanager:认证管理器，当选择了密码模式时就需要这个对象来进行鉴权
     *      userDetailService：用户主体管理服务，如果设置了这个属性，那说明有一个自己的实现
     *      authorizationCodeServices:这个属性时用来设置授权服务器的，主要用于授权码类型的模式
     *      implicitGrantService：这是用来设置隐式授权模式的状态
     *      tokenGranter：使用这个属性意味着授权完全交由自己掌控，并会忽略掉以上几个属性，
     *                   即标准的四种模式已经满足不了你的业务才会考虑使用这个
     *
     *  配置授权端点url
     *      AuthorizationServerEndpointsConfigurer这个对象首先可以通过pathMapping()方法来配置端点url的链接地址，即将oauth默认的
     *      链接地址改成其他的url
     *
     *      * 配置 AuthorizationServerEndpointsConfigurer众多相关类，包括配置身份认证器，配置认证方式，TokenStore，
     *        TokenGranter，OAuth2RequestFactory
     *      * pathMapping() 方法配置端点URL：
     *      * /oauth/authorize：授权端点
     *      * /oauth/token：令牌端点
     *      * /oauth/confirm_access：用户确认授权提交端点
     *      * /oauth/error：授权服务错误信息端点
     *      * /oauth/check_token：用于资源服务访问的令牌解析端点
     *      * /oauth/token_key：提供公有密匙的端点，如果使用JWT令牌的话
     *
     * @param
     * @return
    */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.
//                ;
//    }

    /**
     * @author sunjidong
     * @date 2021/10/22
     * @Describtion
     *  * AuthorizationServerTokenServices接口定义了一些对令牌进行管理的必要操作；
     *      * 实现AuthorizationServerTokenServices这个接口，需要继承DefaultTokenServices这个类。该类中包含了一些有用的实现，你可以使用它
     *      * 来修改令牌的格式和令牌的存储。默认情况下，它在创建一个令牌时，是使用随机值来进行填充的。这个类中完成了令牌管理的几乎所有事情，唯一需要依赖的是
     *      * spring容器中的一个tokenStore接口实现类来定制令牌持久化。而这个tokenStore有一个默认的实现就是InMemoryTokenStore，这个类会将令牌
     *      * 保存到内存中，除此之外，还有几个默认的TokenStore实现类可以使用，jdbcTokenStore、redisTokenStore、jwtTokenStore
     * @param
     * @return
     */
   /* public AuthorizationServerTokenServices tokenService(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setTokenStore(tokenStore);
        //使用accessTokenConverter将tokenStore转成jwt格式(猜想是当检验token时将token转成成jwt格式)
        services.setTokenEnhancer(accessTokenConverter);
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);
        services.setAccessTokenValiditySeconds(7200);
        services.setRefreshTokenValiditySeconds(259200);
        return services;
    }*/

    /**
     * @author sunjidong
     * @date 2021/10/22
     * @Describtion 设置授权码模式的授权码如何存取，这边用内存模式
     * @param
     * @return
     */
   // @Bean
//    public AuthorizationCodeServices authorizationCodeServices(){
//        return new InMemoryAuthorizationCodeServices();
//    }

}
