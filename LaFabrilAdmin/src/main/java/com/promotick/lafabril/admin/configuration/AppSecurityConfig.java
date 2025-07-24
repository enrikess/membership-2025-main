package com.promotick.lafabril.admin.configuration;

import com.promotick.lafabril.admin.security.*;
import com.promotick.lafabril.admin.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private LoginSuccessHandler loginSuccessHandler;
    private LoginFailureHandler loginFailureHandler;

    public AppSecurityConfig(UserDetailsService userDetailsService, LoginSuccessHandler loginSuccessHandler, LoginFailureHandler loginFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/cron/**").permitAll()
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

        http.addFilterBefore(fitroInterceptor(), FilterSecurityInterceptor.class);
        http.addFilterBefore(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthFilter customUsernamePasswordAuthenticationFilter() throws Exception {
        AuthFilter authFilter = new AuthFilter();
        authFilter.setAuthenticationManager(authenticationManagerBean());
        authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(SpringUtil.LOGIN_PROCESSING_URL,"POST"));
        authFilter.setAuthenticationFailureHandler(loginFailureHandler);
        authFilter.setUsernameParameter(SpringUtil.USERNAME_PARAMETER);
        authFilter.setPasswordParameter(SpringUtil.PASSWORD_PARAMETER);
        authFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        return authFilter;
    }

    @Bean
    public FilterSecurityInterceptor fitroInterceptor() throws Exception {
        FilterSecurityInterceptor authFilter = new FilterSecurityInterceptor();
        authFilter.setAuthenticationManager(authenticationManagerBean());
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
        RoleVoter rv = new RoleVoter();
        rv.setRolePrefix(SpringUtil.PREFIJO_ROL);
        AuthenticatedVoter av = new AuthenticatedVoter();
        decisionVoters.add(rv);
        decisionVoters.add(av);
        AffirmativeBased affirmativeBased = new AffirmativeBased(decisionVoters);

        authFilter.setAccessDecisionManager(affirmativeBased);
        authFilter.setSecurityMetadataSource(dbFilterInvocationSecurityMetadataSource());
        return authFilter;
    }

    @Bean
    public DbFilterInvocationSecurityMetadataSource dbFilterInvocationSecurityMetadataSource() {
        return new DbFilterInvocationSecurityMetadataSource();
    }

}
