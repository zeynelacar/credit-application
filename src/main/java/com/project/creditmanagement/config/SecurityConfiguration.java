package com.project.creditmanagement.config;

import com.project.creditmanagement.security.TokenFilterConfig;
import com.project.creditmanagement.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("secured")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Important, for cross-site forgery.
        http.csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Authorizations of entry points.
        //Might be dummy long, but to show what is authorized etc. it is necessary IMHO.
        http.authorizeRequests()


                .antMatchers("/user/signin").permitAll()
                .antMatchers("/user/signup").permitAll()
                .antMatchers("/user/delete/{}username").authenticated()
                .antMatchers("/user/get-by-username").hasRole("ADMIN")
                .antMatchers("/application/results").hasRole("ADMIN")
                .antMatchers("/application/delete").hasRole("ADMIN")
                .antMatchers("/applicant/all").hasRole("ADMIN")
                .antMatchers("/applicant/find").hasRole("ADMIN")
                .antMatchers("/applicant/delete").hasRole("ADMIN")
                .antMatchers("/applicant/add").hasRole("ADMIN")
                .antMatchers("/applicant/update").hasRole("ADMIN")
                .anyRequest().authenticated();

        // Apply JWT
        http.apply(new TokenFilterConfig(tokenProvider));

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(6);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
