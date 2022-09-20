package com.security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.security.demo.security.ApplicationUserRole.*;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/","index","/css/*","/js/*").permitAll()
                        .antMatchers("/api/**").hasRole(STUDENT.name())
                        .antMatchers(HttpMethod.DELETE,"/managment/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                        .antMatchers(HttpMethod.POST,"/managment/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                        .antMatchers(HttpMethod.PUT,"/managment/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                        .antMatchers(HttpMethod.GET,"/managment/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails FaresUser = User.builder()
                .username("fares")
                .password(passwordEncoder.encode("azerty12345"))
                /*.roles(STUDENT.name())*/
                .authorities(STUDENT.getGrantedAuthorities())
                .build();
        UserDetails AdminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("azerty12345"))
                /*.roles(ADMIN.name())*/
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails TomUser = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("azerty12345"))
                /*.roles(ADMINTRAINEE.name())*/
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(FaresUser,AdminUser,TomUser);
    }

}
