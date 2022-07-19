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
//                .antMatchers("/profile/public/**").permitAll()
                .antMatchers("/attach/**").permitAll()
                .antMatchers("/profile/public/*").permitAll()
                .antMatchers("/auth/public/**").permitAll()


               .antMatchers(  "/profile/adm/*","/comment/adm/**").hasRole("ADMIN")
               .antMatchers(  "/playlist/adm/**").hasRole("ADMIN")
               .antMatchers(  "/channel/user/*").hasRole("USER")
               .antMatchers(  "/comment/user/**").hasRole("USER")
               .antMatchers(  "/video/user/**").hasRole("USER")
               .antMatchers(  "/video/adm/**").hasRole("ADMIN")
               .antMatchers(  "/playlist/user/**","/playlist/byId/*").hasRole("USER")
               .antMatchers(  "/channel/adminAndUser/**","/playlist/userandAdmin/**").hasAnyRole("USER","ADMIN")
               .antMatchers(  "/channel/pagination/").hasRole("ADMIN")
               .antMatchers(  "/profile/user/*").hasRole("USER")
               .antMatchers(  "/video_like/**","/comment_like/**").hasRole("USER")
               .antMatchers(  "/category/adm/**").hasRole("ADMIN")
               .antMatchers(  "/subscription/user/**").hasRole("USER")
               .antMatchers(  "/subscription/adm/*").hasRole("ADMIN")
                .antMatchers(  "/report/user/**").hasRole("USER")
                .antMatchers(  "/report/adm/**").hasRole("ADMIN")


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
