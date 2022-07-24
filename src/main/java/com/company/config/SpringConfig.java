package com.company.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Authentication
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Authorization
        http.authorizeRequests()
                 .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/profile/public/**","/auth/public/**").permitAll()




               .antMatchers(  "/company/adm/**").hasRole("ADMIN")
               .antMatchers(  "/transfer/adm/**").hasRole("ADMIN")
               .antMatchers(  "/profile/adm/**").hasRole("ADMIN")
               .antMatchers(  "/client/bank/**").hasRole("BANK")
               .antMatchers(  "/client/adm/**").hasRole("ADMIN")
               .antMatchers(  "/card/bank/**").hasAnyRole("BANK","UZCARD")
               .antMatchers(  "/card/all/**","/transfer/all/***").hasAnyRole("BANK","PAYMENT")
               .antMatchers(   "/transfer/any/**").hasAnyRole("BANK","PAYMENT","USER","ADMIN")
               .antMatchers(  "/transfer/all/**").hasAnyRole("BANK")
               .antMatchers(  "/transaction/bankAndPayment/**").hasAnyRole("BANK","PAYMENT")

                .anyRequest().authenticated()
                .and().addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors().disable().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
         return NoOpPasswordEncoder.getInstance();


    }
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
