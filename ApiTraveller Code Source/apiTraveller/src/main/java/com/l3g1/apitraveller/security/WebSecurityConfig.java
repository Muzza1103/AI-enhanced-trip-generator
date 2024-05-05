package com.l3g1.apitraveller.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.l3g1.apitraveller.jwt.AuthEntryPointJwt;
import com.l3g1.apitraveller.jwt.AuthTokenFilter;
import com.l3g1.apitraveller.service.impl.UserDetailsServiceImpl;
// Configuration class for Web Security
@Configuration
// EnableGlobalMethodSecurity annotation enables Spring Security global method security
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    // Autowired instance of UserDetailsServiceImpl to provide user details
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    // Autowired instance of AuthEntryPointJwt to handle unauthorized access
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    // Bean declaration for authenticationJwtTokenFilter
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    // Bean declaration for authenticationProvider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // Set userDetailsService and passwordEncoder for the authentication provider
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    // Bean declaration for authenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Bean declaration for passwordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean declaration for SecurityFilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configure HTTP security
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/auth/**","/tripSuggestion/**","/suggestion/**","/suggestionJSON/**","/country/**","/city/**","/ai/**").permitAll() // Permit certain paths without authentication
                .anyRequest().authenticated(); // Require authentication for any other request

        // Set authenticationProvider for HTTP security
        http.authenticationProvider(authenticationProvider());
        // Add AuthTokenFilter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}