package com.rih.config;

import com.rih.config.security.AuthEntryPointJwt;
import com.rih.config.security.AuthTokenFilter;
import com.rih.entity.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthEntryPointJwt unAuthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unAuthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .authorizeRequests().antMatchers("/**").permitAll()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                  .antMatchers(HttpMethod.GET, "/recipes/**").hasAnyAuthority("User")
                  .antMatchers(HttpMethod.POST, "/recipes/**").hasAnyAuthority("Admin")
                  .antMatchers(HttpMethod.GET, "/ingredients/**").hasAnyAuthority("User")
                  .antMatchers(HttpMethod.POST, "/ingredients/match").hasAnyAuthority("User")
                  .antMatchers(HttpMethod.POST, "/ingredients/**").hasAnyAuthority("Admin")

//                .antMatchers("/recipes/**").hasAnyAuthority( "user")
//                .antMatchers("/recipes/**").hasAnyAuthority("USER", "ROLE_USER", "user", "USER_ROLE")
//                .antMatchers("/recipes/**").permitAll()
//                .antMatchers("/users/**").hasAnyAuthority("Admin")
//                  .antMatchers(HttpMethod.GET, "/recipes").hasRole("USER")
//                  .antMatchers(HttpMethod.GET, "/recipes").hasRole("User")
//                  .antMatchers(HttpMethod.GET, "/recipes").hasAnyAuthority("USER")
//                  .antMatchers(HttpMethod.GET, "/recipes/**").hasAnyAuthority("ADMIN_ROLE")

//                  .antMatchers(HttpMethod.GET, "/recipes/**").hasAnyRole("USER", "user")

                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
