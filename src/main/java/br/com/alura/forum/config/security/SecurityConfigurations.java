package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import net.bytebuddy.matcher.AnnotationTypeMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	//Configurações de autenticação (Login, acesso)
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	}	
	
	//Configurações de autorização (url quem pode acessar..)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/topicos").permitAll()
		.antMatchers(HttpMethod.GET, "/topicos/*").permitAll();
	
	}
	
	//Configurações de recursos estaticos (js, css, img, etc.)
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}
}
