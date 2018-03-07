package test.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) 
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AccessDecisionManager accessDecisionManager;
	@Autowired
	private FilterInvocationSecurityMetadataSource newSource;
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
//		super.configure(http);
		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll() // 都可以访问
//				.antMatchers("/users/**").hasRole("USER") // 需要相应的角色才能访问
//				.antMatchers("/admins/**").hasRole("ADMIN") // 需要相应的角色才能访问
				.and().formLogin() // 基于 Form 表单登录验证
				.loginPage("/login").failureUrl("/login-error") // 自定义登录界面
				.and().exceptionHandling().accessDeniedPage("/403"); // 处理异常，拒绝访问就重定向到
																		// 403
																		// 页面
		http.logout().logoutSuccessUrl("/"); // 成功登出后，重定向到 首页
		
		http.addFilterBefore(createFilterInterceptor(), FilterSecurityInterceptor.class);
	}
	
	protected FilterSecurityInterceptor createFilterInterceptor(){
		FilterSecurityInterceptor filterInerceptor=new FilterSecurityInterceptor();
		filterInerceptor.setAccessDecisionManager(accessDecisionManager);
		filterInerceptor.setSecurityMetadataSource(newSource);
		return filterInerceptor;
	}

//	@Bean
//	public UserDetailsService userDetailsService() {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(); // 在内存中存放用户信息
//		manager.createUser(User.withUsername("noukey").password("123456").roles("USER").build());
//		manager.createUser(User.withUsername("admin").password("123456").roles("USER", "ADMIN").build());
//		return manager;
//	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

}
