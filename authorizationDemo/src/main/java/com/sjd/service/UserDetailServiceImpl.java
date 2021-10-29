package com.sjd.service;/**
 * @projectname authDemo
 * @author xinao
 * @create 2021/10/26
 */

import com.sjd.config.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author sunjidong
 * @Date 2021/10/26
 * @Describtion
 **/
@Component
public class UserDetailServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User(username,new BCryptPasswordEncoder().encode("123"), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return user;
    }
}
