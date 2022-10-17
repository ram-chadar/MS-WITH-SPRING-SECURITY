
package com.jbk.product.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public CustomUserDetailService customUserDetilService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(customUserDetilService).passwordEncoder(passwordEncoder());

		// using in memory with password encoder
		//auth.inMemoryAuthentication().withUser("ram").password(this.passwordEncoder().encode("ram")).roles("ADMIN");
		//auth.inMemoryAuthentication().withUser("sham").password(this.passwordEncoder().encode("sham")).roles("NORMAL");

		// using in memory without password encoder
		// auth.inMemoryAuthentication().withUser("ram").password("ram").roles("ADMIN");
	}

// Role based authentication
	// ADMIN >> READ , WRITR,UPDATE
	// NORMAL >> READ

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/loginuser","/adduser").permitAll()
				.antMatchers("/user/**").hasRole("NORMAL") // NORMAL USER
				.antMatchers("/product/**").hasRole("ADMIN") // ADMIN USER
				.anyRequest().authenticated().and().httpBasic();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	// for no pass encoder

	/*
	 * @Bean public PasswordEncoder passwordEncoder() { return
	 * NoOpPasswordEncoder.getInstance(); }
	 */

}
