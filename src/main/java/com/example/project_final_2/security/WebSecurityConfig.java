package com.example.project_final_2.security;

import com.example.project_final_2.security.filter.CustomAuthenticationFilter;
import com.example.project_final_2.security.filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String SECRET_KEY = "secret-key";

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //dùng để bcrypt password từ client gửi xuống vầ so sánh với ở DB
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        //auth API
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/auth/**").permitAll();
        //product API
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/product/show-product/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/product/add-product/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/product/edit-product/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/product/delete-product/**").hasAnyAuthority("ROLE_ADMIN");
        //user API
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/user/show-user/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/user/search-user/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/user/edit-user/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/user/delete-user/**").hasAnyAuthority("ROLE_ADMIN");
        //cart API
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/cart/show-cart/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/cart/add-cart/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/cart/edit-cart/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/cart/delete-cart/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/cart/reset-cart/**").hasAnyAuthority("ROLE_USER");
        //invoice API
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/invoice/show-invoice/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/invoice/add-invoice/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/invoice/edit-invoice/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/invoice/delete-invoice/**").hasAnyAuthority("ROLE_ADMIN");
        //rating API
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/rating/show-rating/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/rating/add-rating/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/rating/edit-rating/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/rating/delete-rating/**").permitAll();
        //authenticated other requests
        http.authorizeRequests().anyRequest().authenticated();
        //permit other requests
//        http.authorizeRequests().anyRequest().permitAll();
    }

}
