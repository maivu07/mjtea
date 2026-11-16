package fi.haagahelia.mjtea;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**").permitAll() // CSS ai cũng tải được
                        .anyRequest().authenticated()) // còn lại phải đăng nhập
                .formLogin(formlogin -> formlogin
                        .defaultSuccessUrl("/ingredientlist", true).permitAll()) // login xong về trang kho
                .logout(logout -> logout.permitAll()) // cho phép đăng xuất
                // Tắt CSRF cho phép nút delete dạng link GET
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    // Dùng BCrypt để xác thực mật khẩu đã hash
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}