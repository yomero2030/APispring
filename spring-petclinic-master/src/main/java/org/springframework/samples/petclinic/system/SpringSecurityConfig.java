/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.samples.petclinic.record.RecordRepository;
import org.springframework.samples.petclinic.record.RecordService;
import org.springframework.samples.petclinic.user.MyUserDetailsService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

/**
 *
 * @author katZ_
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private MyUserDetailsService userDetailsService;
    
    @Autowired
    private RecordRepository recordRepository;
   
    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().
                disable()
                
                .authorizeRequests()
                .antMatchers(
                       "/api/newCitas",
                        "/api/citas/{id}",
                        "/api/deleteCitas/{id}",
                        "/api/loginApp/{user}",
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/resources/**",
                        "/static/**",
                        "/Login**",
                        "/owner_signup",
                        "/webjars/**",
                        "/API**").permitAll()
                .antMatchers("/owner/**").access("hasAuthority('OWNER_PRIVILEGE')")
                .antMatchers("/admin/**").access("hasAuthority('ADMIN_PRIVILEGE')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .failureHandler(customAuthenticationFailureHandler())
                .loginPage("/Login?")
                .successHandler(myAuthenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .clearAuthentication(true)
                //.invalidateHttpSession(true)
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
    
    
    
    
  
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {       
        String idForEncode = "bcrypt";//no borrar en caso que se necesita multiple encriptaciones kevin del futuro
        Map encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
        return passwordEncoder;
    }

    public class CustomAuthenticationFailureHandler
            implements AuthenticationFailureHandler {

        private ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {

            RecordService recordService = new RecordService(recordRepository);
            System.out.println("test error------");
            String username = request.getParameter("username");
            System.out.println(exception.getClass());

            if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
                response.sendRedirect("/login?error");
                recordService.badCredentials(username);
                System.out.println(exception);
            } else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
                response.sendRedirect("/login?disabled");
                recordService.userDisabled(username);
                System.out.println(exception);
            } else if (exception.getClass().isAssignableFrom(SessionAuthenticationException.class)) {
                response.sendRedirect("/login?loggedin");
                System.out.println(exception);
            } else if (exception.getClass().isAssignableFrom(LockedException.class)) {
                System.out.println("Pero fue por no existir");
                response.sendRedirect("/login?error");
                recordService.badCredentials(username);
                System.out.println(exception);
            }
        }
    }

    public class MySimpleUrlAuthenticationSuccessHandler
            implements AuthenticationSuccessHandler {

        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                HttpServletResponse response, Authentication authentication)
                throws IOException {

            RecordService recordService = new RecordService(recordRepository);
            System.out.println("test------ sucess");
            String username = request.getParameter("username");
            recordService.success(username);

            handle(request, response, authentication);
            //clearAuthenticationAttributes(request);
        }

        protected void handle(HttpServletRequest request,
                HttpServletResponse response, Authentication authentication)
                throws IOException {

            String targetUrl = determineTargetUrl(authentication);

            if (response.isCommitted()) {
                return;
            }

            redirectStrategy.sendRedirect(request, response, targetUrl);
        }

        protected String determineTargetUrl(Authentication authentication) {

            
            //no borrar en caso de que se necesita roles mas adelante
            boolean isUser = false;
            boolean isAdmin = false;
            System.out.println("aqui en determin" + authentication.getAuthorities());
            Collection<? extends GrantedAuthority> authorities
                    = authentication.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                System.out.println("que roles tienes tu: " + grantedAuthority.getAuthority().toString());
                if(grantedAuthority.getAuthority().toString().compareTo("OWNER_PRIVILEGE") == 0)
                    isUser = true;
                if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                    isUser = true;
                    break;
                } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                    isAdmin = true;
                    break;
                }
            }

            if(isUser)
                return "/owner/";
            return "/";

        }

        protected void clearAuthenticationAttributes(HttpServletRequest request) {
            HttpSession session = request.getSession(false);
            if (session == null) {
                return;
            }
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }

        public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
            this.redirectStrategy = redirectStrategy;
        }

        protected RedirectStrategy getRedirectStrategy() {
            return redirectStrategy;
        }
    }

}
