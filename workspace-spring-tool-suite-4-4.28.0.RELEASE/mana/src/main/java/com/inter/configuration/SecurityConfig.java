package com.inter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/").permitAll();
			auth.requestMatchers("/fragments/**").permitAll();
			auth.requestMatchers("/images/**").permitAll();
			auth.requestMatchers("/register").permitAll();
			auth.requestMatchers("/search/**").permitAll();
			auth.requestMatchers("/advert/**").permitAll();
			auth.anyRequest().authenticated();
		}).formLogin(form -> {
			form.loginPage("/login").permitAll();
			form.defaultSuccessUrl("/account").permitAll();
			form.failureUrl("/login?error=true").permitAll();
		}).logout(logout -> {
			logout.logoutUrl("/logout").permitAll();
			logout.logoutSuccessUrl("/").permitAll();
		});

		return http.build();
	}
}
