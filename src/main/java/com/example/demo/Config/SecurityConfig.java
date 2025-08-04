package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {  //Sirve para encriptar la contraseña
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")) //Proteccion csrf
                .authorizeHttpRequests(auth -> auth //El usuario tiene que estar autentificado para accedes a las demas
                        .requestMatchers("/usuario/login", "/usuario/registro", "/css/**", "/js/**", "/img/**").permitAll() //A estas accede siempre
                        .requestMatchers("/usuario/panelcontrol").hasRole("Admin")  //A esta accede si el usuario tiene rol de admin
                        .requestMatchers("/usuario/panelcontrol/usuarios").hasRole("Admin") //Todas las urls que se queiren filtrar
                        .requestMatchers("/usuario/principal").hasRole("Usuario")   //A esta accede si el usuario es normal
                        .requestMatchers("/pelicula/mispeliculas").hasRole("Usuario")
                        .requestMatchers("/pelicula/agregarPeliculas").hasRole("Usuario")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/usuario/login")
                        .permitAll()
                        .successHandler(new CustomAuthenticationSuccessHandler())
                )
                .logout(logout -> logout    //Borra el id de sesion
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/usuario/login?logout")
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(3000)
                );

        return http.build();
    }
}
