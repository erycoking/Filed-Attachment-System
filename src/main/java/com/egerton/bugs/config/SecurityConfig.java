
package com.egerton.bugs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.egerton.bugs.Authentication.CustomLogoutSuccessHandler;
import com.egerton.bugs.Authentication.CustomSuccessHandler;
import com.egerton.bugs.Authentication.LoginAccessDeniedHandler;
import com.egerton.bugs.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity

public class SecurityConfig  extends WebSecurityConfigurerAdapter{


	private CustomLogoutSuccessHandler logoutSuccessHandler;
	private CustomUserDetailsService userDetailsService;
	private BCryptPasswordEncoder passwordEncoder;
	private CustomSuccessHandler successHandler;
	private LoginAccessDeniedHandler accessDeniedHandler;

    @Autowired
	public SecurityConfig(CustomLogoutSuccessHandler logoutSuccessHandler,
			CustomUserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder,
			CustomSuccessHandler successHandler, LoginAccessDeniedHandler accessDeniedHandler) {
		super();
		this.logoutSuccessHandler = logoutSuccessHandler;
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.successHandler = successHandler;
		this.accessDeniedHandler = accessDeniedHandler;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		 .userDetailsService(userDetailsService)
		 .passwordEncoder(passwordEncoder);
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
            .csrf().disable()
            .authorizeRequests()
				.antMatchers("/", "/register", "/student/register", "/staff/register", "/getDepartments", "/company/{companyId}", "/company",
						"/company/register", "/company/setPassword", "/company/password","/login", "/logout*", "/password/**").permitAll()
				.antMatchers("/staff/**").hasAnyAuthority("ROLE_STAFF", "ROLE_COORDINATOR", "ROLE_ADMIN", "ROLE_BUGS")
				.antMatchers("/student/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_ADMIN", "ROLE_BUGS")
				.antMatchers("/bugs/**").hasAnyAuthority( "ROLE_ADMIN", "ROLE_BUGS")
				.antMatchers("/company/**").hasAnyAuthority( "ROLE_COMPANY", "ROLE_ADMIN", "ROLE_BUGS")
				.anyRequest().authenticated()
				.and()
            .formLogin()
                .loginPage("/login")
                .successHandler(successHandler)
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/logout.html?logSucc=true")
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
             .exceptionHandling()

             .accessDeniedHandler(accessDeniedHandler);
	}


	@Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**");
    }


}

