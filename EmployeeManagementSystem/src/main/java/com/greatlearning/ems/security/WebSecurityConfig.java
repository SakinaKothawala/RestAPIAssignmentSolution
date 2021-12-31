package com.greatlearning.ems.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.greatlearning.ems.service.UserDetailsSeviceImpl;

	

	@Configuration
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Bean
		public UserDetailsService userDetailsService() {
			return new UserDetailsSeviceImpl();
		}

		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Bean
		public DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			authProvider.setUserDetailsService(userDetailsService());
			authProvider.setPasswordEncoder(passwordEncoder());

			return authProvider;
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(authenticationProvider());
		}
		
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/api/h2-console/**");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.httpBasic().
			and()
			.authorizeRequests()
				.antMatchers("/api/employees/add", "/api/roles/add","/api/users/add").hasAuthority("ADMIN")
				.antMatchers("/", "/api/employees/list","/api/employees/search/**", "/api/employees/sort").hasAnyAuthority("USER", "ADMIN")
				.anyRequest().authenticated()
				.and().formLogin().loginProcessingUrl("/login").successForwardUrl("/api/employees").permitAll()
				.and().logout().logoutSuccessUrl("/login").permitAll()
				.and().exceptionHandling().accessDeniedPage("/employees/403")
				.and().cors()
				.and().csrf().disable();
		}

}