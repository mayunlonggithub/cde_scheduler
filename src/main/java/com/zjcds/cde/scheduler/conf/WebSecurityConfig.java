package com.zjcds.cde.scheduler.conf;


import com.zjcds.cde.scheduler.security.filter.JsonAuthenticationFilter;
import com.zjcds.cde.scheduler.security.handler.JsonAccessDeniedHandler;
import com.zjcds.cde.scheduler.security.handler.JsonAuthFailureHandler;
import com.zjcds.cde.scheduler.security.handler.JsonAuthLogoutSuccessHandler;
import com.zjcds.cde.scheduler.security.handler.JsonAuthSuccessHandler;
import com.zjcds.cde.scheduler.security.handler.JsonAuthenticationEntryPoint;
import com.zjcds.cde.scheduler.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;


/**
 * @author：chenlb@zjcds.com
 * @date: 2019/9/8
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JsonAccessDeniedHandler accessDeniedHandler;

    @Resource
    private JsonAuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private JsonAuthSuccessHandler successHandler;

    @Resource
    private JsonAuthFailureHandler failureHandler;

    @Resource
    private JsonAuthLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    @Qualifier("daoAuthenticationProvider")
    @Setter
    private AuthenticationProvider daoAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/range_code/**","/dept/**","/error_code/**").permitAll()
                .antMatchers("/static/**", "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**").permitAll()
                .antMatchers("/**").hasRole("DEFAULT")
                //处理跨域请求中的Preflight请求
                .anyRequest().authenticated()
                .anyRequest().permitAll()

                .and()
                //这里必须要写formLogin()，不然原有的UsernamePasswordAuthenticationFilter不会出现，也就无法配置自定义的UsernamePasswordAuthenticationFilter
                .formLogin()
//                .loginPage("/hello")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .permitAll()


                .and()
                .httpBasic()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll()

                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)

                .and()
                //.addFilterBefore(new TokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                //用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
                .addFilterAt(jsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                //开启跨域 cors()
                .cors()

                .and()
                .csrf()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    /**
     * 密码加盐
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注册自定义的UsernamePasswordAuthenticationFilter
     *
     * @return JSON登录参数接收
     * @throws Exception
     */
    @Bean
    public JsonAuthenticationFilter jsonAuthenticationFilter() throws Exception {
        JsonAuthenticationFilter filter = new JsonAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        filter.setFilterProcessesUrl("/login");

        // 这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceDelegate();
    }

    /**
     * 自定义AuthenticationProvider
     *
     * @return AuthenticationProvider
     */
    @Bean("daoAuthenticationProvider")
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(encoder());
        return daoAuthenticationProvider;
    }

    /**
     * 代理UserService实现UserDetailsService接口
     *
     * @param <D>
     */
    public static class UserDetailsServiceDelegate<D extends UserService> implements UserDetailsService {

        @Setter
        @Getter

        @Autowired
        @Qualifier("userServiceImpl")
        private D delegate;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            if (StringUtils.isBlank(username)) {
                throw new UsernameNotFoundException("用户名不能为空!!");
            }
            UserDetails userDetails = delegate.queryUserByAccount(username);
            if (userDetails == null)
                throw new UsernameNotFoundException("未找到" + username);
            if (!userDetails.isEnabled())
                throw new DisabledException("用户账号" + username + "当前不可用!");
            return userDetails;
        }
    }
}
