package de.digiwill.configuration;

import de.digiwill.service.UserHandleManager;
import de.digiwill.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserHandleManager userHandleManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/greeting", "/register", "/requestPasswordReset", "/resetPassword", "/callback", "/about").permitAll()
                .antMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/api/csrf").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/?login")
                .loginProcessingUrl("/")
                .failureUrl("/?login&error")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
        //.antMatchers("/admin/**").hasRole("ADMIN")
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userHandleManager)
                .passwordEncoder(SecurityHelper.getEncoder());
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return userHandleManager;
    }
}
