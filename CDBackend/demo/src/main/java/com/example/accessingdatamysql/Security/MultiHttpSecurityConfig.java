package com.example.accessingdatamysql.Security;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.mongodb.ReflectionDBObject.JavaWrapper;

// @Configuration
@EnableWebSecurity
public class MultiHttpSecurityConfig {

    // @Autowired
    // public void configureGlobal(AuthenticationManagerBuilder auth) throws
    // Exception {
    // try {
    // auth.inMemoryAuthentication().withUser("user").password("password").roles("USER").and().withUser("admin")
    // .password("password").roles("USER", "ADMIN");
    // } catch (Exception ex) {
    // throw ex;
    // }

    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true) // Enables PreAuthorization
    public static class UserWebSecurity extends WebSecurityConfigurerAdapter {
        @Autowired
        private UserDetailsServiceImpl userDetailsService;

        @Autowired
        private JwtUserFilter jwtFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/user/register")
                    .permitAll().antMatchers(HttpMethod.POST, "/user/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/user/unitTest").permitAll().anyRequest().authenticated().and()
                    // .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                    // .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                    // // this disables session creation on Spring Security
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            System.out.println("User Configuration");
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
        }

        @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

    }

}
