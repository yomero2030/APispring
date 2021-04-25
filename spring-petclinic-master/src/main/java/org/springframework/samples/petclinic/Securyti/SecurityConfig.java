/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.Securyti;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author Faabian
 */
public class SecurityConfig {
    
    @Order(1)
    @Configuration
    public static class RestConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .antMatcher("/API/**")

                .cors()
                    .and()
                .csrf()
                    .disable() // we don't need CSRF because our token is invulnerable
                .authorizeRequests()
                    .antMatchers(HttpMethod.PUT, "/**","/")
                    .permitAll()
                    .antMatchers(HttpMethod.PUT, "API/**")
                    .permitAll()
                    .antMatchers(HttpMethod.POST, "/userLogins")
                    .permitAll()
                    .antMatchers(HttpMethod.POST, "/user")
                    .permitAll()
                    .antMatchers(HttpMethod.POST, "/user/**")
                    .permitAll()
                    .antMatchers(HttpMethod.POST, "/**","/")
                    .permitAll()
                    .antMatchers(HttpMethod.GET, "/userLogins")
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

    }


    @Order(2)
    @Configuration
    public static class WebConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.exceptionHandling().accessDeniedPage("/403");

            http

                    .formLogin()
                    .and()
                    .authorizeRequests().antMatchers("/**").permitAll()
                    .antMatchers("/**","/").permitAll()
                    .antMatchers("/user").permitAll()
                    .antMatchers("/user/**").permitAll()
                    .antMatchers("/userLogins").permitAll()
                    .antMatchers("/API/**").permitAll();

        }


        @Override
        public void configure(WebSecurity web) throws Exception {
            web
                    .ignoring()

                        .antMatchers(
                                HttpMethod.GET,
                                "/",
                                "/*.html",
                                "/**/favicon.ico",
                                "/**/*.html",
                                "/**/*.css",
                                "/**/*.js"

                        )
                        .antMatchers(
                                HttpMethod.POST,
                                "/userLogin",
                                "/user",
                                "/user/**",
                                "/**",
                                "/",
                                "/owners/**",
                                "/API/**"

                        )
                ;
            ;
        }
    }

}
