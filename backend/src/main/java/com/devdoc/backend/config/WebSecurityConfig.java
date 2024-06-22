package com.devdoc.backend.config;


import com.devdoc.backend.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

// WebSecurityConfig: 웹 보안 설정

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter; // JWT 인증 필터

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors() // CORS 설정 적용
				.and()
				.csrf() // CSRF 보호 비활성화
				.disable()
				.httpBasic() // HTTP Basic 인증 비활성화
				.disable()
				.sessionManagement() // 세션 관리 설정
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용하지 않음 (Stateless)
				.and()
				.authorizeRequests() // 요청에 대한 인증 설정
				.antMatchers("/", "/auth/**", "/h2-console/**").permitAll() // 특정 경로 인증 없이 접근 허용
				.anyRequest() // 나머지 모든 요청
				.authenticated(); // 인증 필요


		http.headers().frameOptions().sameOrigin(); // h2-console 접근을 위한 설정. 같은 오리진에서의 프레임 사용 허용

		http.addFilterAfter(
				jwtAuthenticationFilter, // JWT 인증 필터 추가
				CorsFilter.class // CORS 필터 이후에 실행
		);
	}
}
