/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infiniteskills.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author соколов
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    
    private BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**"); // #3
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/resources/**", "/vc").permitAll()
                .antMatchers("/addquestion", "/profil").hasAnyRole("USER")
                .and();

        http.formLogin()
                // ????????? ???????? ? ?????? ??????
                .loginPage("/login")
                // ????????? action ? ????? ??????
                .loginProcessingUrl("/j_spring_security_check")
                // ????????? URL ??? ????????? ??????
                .failureUrl("/enter?error")
                // ????????? ????????? ?????? ? ?????? ? ????? ??????
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .defaultSuccessUrl("/index", true)
                // даем доступ к форме логина всем
                .permitAll();

        http.logout()
                // ????????? ?????? ?????? ????
                .permitAll()
                // ????????? URL ???????
                .logoutUrl("/logout")
                // ????????? URL ??? ??????? ???????
                .logoutSuccessUrl("/")
                // ?????? ?? ???????? ??????? ??????
                .invalidateHttpSession(true);
    }

}
