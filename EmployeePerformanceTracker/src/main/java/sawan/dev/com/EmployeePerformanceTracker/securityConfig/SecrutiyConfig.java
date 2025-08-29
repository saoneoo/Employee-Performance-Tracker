package sawan.dev.com.EmployeePerformanceTracker.securityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecrutiyConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // --- User endpoints ---
                        .requestMatchers("/api/user").permitAll()

                        // --- Task endpoints ---
                        .requestMatchers("/api/task/*/approve").hasRole("MANAGER")   // only manager can approve
                        .requestMatchers("/api/task/**").hasAnyRole("EMPLOYEE", "MANAGER") // both can manage tasks

                        // --- Performance endpoints ---
                        .requestMatchers("/api/performance/**").hasRole("MANAGER")  // only manager can access performance

                        .requestMatchers("/api/reward/declare").hasRole("MANAGER") // only manager declare winner
                        .requestMatchers("/api/reward/**").authenticated()


                        // --- Fallback ---
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





}
