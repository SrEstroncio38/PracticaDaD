package es.urjc.TicTakTicket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import es.urjc.TicTakTicket.Entities.UserRepositoryAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public UserRepositoryAuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/img/**", "/scss/**", "/vendor/**").permitAll();
	
		// Public pages
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/register").permitAll();
		http.authorizeRequests().antMatchers("/events").permitAll();
		http.authorizeRequests().antMatchers("/events/**").permitAll();
		
		// Private pages (all other pages)
		http.authorizeRequests().anyRequest().authenticated();
		
		// Login form
		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("username");
		http.formLogin().passwordParameter("password");
		http.formLogin().defaultSuccessUrl("/");
		http.formLogin().failureUrl("/error");
		
		// Logout
		http.logout().logoutUrl("/logout");
		http.logout().logoutSuccessUrl("/");
	
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Database authentication provider
		auth.authenticationProvider(authenticationProvider);
	}
	
}
