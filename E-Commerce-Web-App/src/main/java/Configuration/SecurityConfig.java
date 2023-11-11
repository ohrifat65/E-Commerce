package Configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.RequestMapping;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequestMapping("admin/user")
public class SecurityConfig {

	@Bean
	static public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
PasswordEncoder pEncoder;
	@Value("${spring.security.user-query}")
	private String userQuery;

	@Value("${spring.security.roles-query}")
	private String roleQuery;

	@Autowired
	@Qualifier("datasource")
	private DataSource datasource;


//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails normalUser = User.builder().username("rifat").password(passwordEncoder().encode("password")).roles("NORMAL")
//				.build();
//
//		UserDetails adminUser = User.builder().username("admin").password(passwordEncoder().encode("password")).roles("ADMIN")
//				.build();
//
////		InMemoryUserDetailsManager inMemoryUserDetailsManager=new InMemoryUserDetailsManager(normalUser,adminUser);
////		return inMemoryUserDetailsManager;
//
//		return new InMemoryUserDetailsManager(normalUser, adminUser);
//
//	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	    public AuthenticationManager authenticationManagerBean(HttpSecurity httpSecurity) throws Exception {
	        AuthenticationManagerBuilder builder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
	        builder
	        .jdbcAuthentication()
	        .usersByUsernameQuery(userQuery)
	        .authoritiesByUsernameQuery(roleQuery)
	        .dataSource(datasource)
	        .passwordEncoder(pEncoder);
	        return builder.build();
	   }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf()
			.disable()
			.authorizeHttpRequests()
			.requestMatchers("/admin/products")
			.hasRole("ADMIN")
//			.requestMatchers("/admin/categories")
//			.hasRole("NORMAL").requestMatchers("/admin/products/add")
//			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin().loginPage("/login")
			.permitAll()
			.and()
			.logout()
	        .permitAll();

		return http.build();
	}
}
