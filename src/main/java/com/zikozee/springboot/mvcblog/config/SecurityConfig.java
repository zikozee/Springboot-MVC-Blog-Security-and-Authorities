package com.zikozee.springboot.mvcblog.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    public SecurityConfig(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }
    //FIX
    //Error was cos of salting, springboot uses bCrypt that starts with $2a
    // fix by salting using test in config
    //BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
     //   System.out.println(bCryptPasswordEncoder.encode("test123"));

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled"
                        + " from users where username=?")
                .authoritiesByUsernameQuery("select username, authority "
                        + "from authorities where username=?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }
//    @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//            auth.inMemoryAuthentication()
//                    .withUser("zikozee").password(passwordEncoder().encode("ziko123")).roles("EMPLOYEE")
//                    .and()
//                    .withUser("mary").password(passwordEncoder().encode("test123")).roles("MANAGER")
//                    .and()
//                    .withUser("susan").password(passwordEncoder().encode("test123")).roles("ADMIN");
//        }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/users/register","/css/**","/img/**","/js/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/showMyLoginPage")
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
