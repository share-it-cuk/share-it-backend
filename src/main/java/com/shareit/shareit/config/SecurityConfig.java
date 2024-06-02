/*
package com.shareit.shareit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// 모든 경로 허용
			.authorizeHttpRequests((authorizeHttpRequests)->
				authorizeHttpRequests.anyRequest().permitAll()
			)
			.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}
}
*/
