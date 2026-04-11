package lk.ijse.apigateway.config;

import lk.ijse.apigateway.security.GatewayJwtFilter;
import lk.ijse.apigateway.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(jwtSecret);
    }

    @Bean
    public FilterRegistrationBean<GatewayJwtFilter> gatewayJwtFilter(JwtUtil jwtUtil) {
        FilterRegistrationBean<GatewayJwtFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new GatewayJwtFilter(jwtUtil));
        bean.addUrlPatterns("/api/*"); // protect all /api/** routes except explicit skips in filter
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .anonymous(Customizer.withDefaults());

        return http.build();
    }
}
