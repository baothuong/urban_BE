package com.urban.EmployeeManager.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Áp dụng cho tất cả các API bắt đầu bằng /api/
                .allowedOrigins("*")   // Cho phép tất cả các domain. Thay bằng domain cụ thể của frontend trong production.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false); // Đặt là true nếu bạn dùng session/cookie và cần gửi credentials
    }
}
