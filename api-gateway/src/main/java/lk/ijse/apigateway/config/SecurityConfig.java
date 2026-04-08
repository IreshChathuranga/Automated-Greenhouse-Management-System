package lk.ijse.apigateway.config;

import lk.ijse.apigateway.security.GatewayJwtFilter;
import lk.ijse.apigateway.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        bean.addUrlPatterns("/api/*"); // protect all /api/** routes
        bean.setOrder(1);
        return bean;
    }
}
