package pt.kivo.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Define quem pode aceder a cada página
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/login",
                                "/css/**",
                                "/images/**",
                                "/icons/**"
                        ).permitAll()

                        .requestMatchers(
                                "/produtos", "/produtos/**",
                                "/categorias", "/categorias/**",
                                "/subcategorias", "/subcategorias/**"
                        ).permitAll()

                        // As restantes páginas exigem login
                        .anyRequest().authenticated()
                )

                // Utiliza a página de login
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )

                // Mantém os POST da API a funcionar sem token CSRF
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/produtos", "/produtos/**",
                                "/categorias", "/categorias/**",
                                "/subcategorias", "/subcategorias/**"
                        )
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder,
            @Value("${KIVO_ADMIN_PASSWORD}") String password) {

        // Primeira conta de teste do Kivo
        UserDetails utilizador = User.withUsername("lojateste@kivo.pt")
                .password(passwordEncoder.encode(password))
                .roles("GERENTE")
                .build();

        return new InMemoryUserDetailsManager(utilizador);
    }

}
