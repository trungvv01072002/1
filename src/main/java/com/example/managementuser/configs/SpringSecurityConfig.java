package com.example.managementuser.configs;

import com.example.managementuser.securities.JwtAuthenticationEntryPoint;
import com.example.managementuser.securities.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration // DEFINE BEAN
@EnableWebSecurity
public class SpringSecurityConfig {
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    SpringSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter){
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean  // Define method bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    String [] authenticationEndPoint = new String[]{
            "/api/v1/users/login",
            "/api/v1/users/register",
            "/swagger-ui/**",
            "/v3/**"
    };

//    String [] whiteEndPoint = new String[]{
//
//    };

    String [] userEndPoint = new String[]{
            "/api/v1/users/profile/**",
            "/api/v1/users/search/**",
    };

    String [] adminEndPoint = new String[]{
            "/api/v1/users/update/**",
            "/api/v1/users/delete/**",
            "/api/v1/admin/user/**",
    };



    @Bean
    SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers(userEndPoint).hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
                    authorize.requestMatchers(adminEndPoint).hasAuthority("ROLE_ADMIN");
                    authorize.requestMatchers(authenticationEndPoint).permitAll();
//                    authorize.anyRequest().permitAll();
                })
                .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    // CONFIGURE AUTHENTICATION MANAGER BEAN , IT IS USED TO AUTHENTICATE USER
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // CONFIGURE CORS FOR ALL ORIGINS
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}