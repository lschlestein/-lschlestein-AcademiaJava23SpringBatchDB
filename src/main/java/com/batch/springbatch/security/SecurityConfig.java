package com.batch.springbatch.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity//para verificar as roles dos usuários
public class SecurityConfig {
    @Bean
    //authentication
    public UserDetailsService userDetailsService() {
      /*  UserDetails admin = User.withUsername("Lucas")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("Alberto")
                .password(passwordEncoder().encode("123"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);*/
        return new UserInfoUserDetailsService();
    }

   /* @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/jobs/importPessoas", "/users/add").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/jobs/listar", "/jobs/cadastrar", "/users/listar")//precisa authenticar
                .authenticated().and().formLogin().and().build();
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/jobs/importPessoas","/usuarios/adicionar").permitAll();
                    authorize.requestMatchers( "/jobs/**");   //.hasRole("ADMIN");
                    authorize.anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean//Database Users
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
