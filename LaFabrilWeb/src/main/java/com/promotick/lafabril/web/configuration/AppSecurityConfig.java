package com.promotick.lafabril.web.configuration;

import com.promotick.lafabril.web.security.CustomAuthenticationFilter;
import com.promotick.lafabril.web.security.LoginFailureHandler;
import com.promotick.lafabril.web.security.LoginSuccessHandler;
import com.promotick.lafabril.web.security.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private LoginSuccessHandler loginSuccessHandler;
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    public AppSecurityConfig(UserDetailsService userDetailsService, LoginSuccessHandler loginSuccessHandler, LoginFailureHandler loginFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/recuperar/**").permitAll()
                    .antMatchers("/registro/**").permitAll()
                    //.antMatchers("/recompensas/**").permitAll() 
                .and()
                .formLogin()
                    .loginProcessingUrl(SpringUtil.LOGIN_PROCESSING_URL)
                    .loginPage(SpringUtil.LOGIN_PAGE).permitAll()
                    .failureHandler(loginFailureHandler)
                    .successHandler(loginSuccessHandler)
                .and()
                .logout()
                    .logoutUrl(SpringUtil.LOGOUT_URL)
                    .logoutSuccessUrl(SpringUtil.LOGOUT_SUCCESS_URL)
                    .invalidateHttpSession(true)
                    .deleteCookies(SpringUtil.JSESSIONID)
                .and()
                .exceptionHandling()
                .and()
                    .csrf().disable();

        http.addFilterBefore(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CustomAuthenticationFilter customUsernamePasswordAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        customAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        customAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(SpringUtil.LOGIN_PROCESSING_URL,"POST"));
        customAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);
        customAuthenticationFilter.setUsernameParameter(SpringUtil.USERNAME_PARAMETER);
        customAuthenticationFilter.setPasswordParameter(SpringUtil.PASSWORD_PARAMETER);
        customAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        return customAuthenticationFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
