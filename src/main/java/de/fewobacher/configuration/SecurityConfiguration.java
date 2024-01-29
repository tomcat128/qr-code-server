package de.fewobacher.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.StringUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration
{
    @Getter
    @AllArgsConstructor
    static class Credentials
    {
        String username;
        String password;
    }

    @Bean
    Credentials credentials(@Value("${security.basic-auth.username}") String username,
                            @Value("${security.basic-auth.password}") String password)
    {
        return new Credentials(username, password);
    }

    @Bean
    public static PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, Credentials credentials) throws Exception
    {
        http.csrf().disable();

        if (StringUtils.hasText(credentials.getUsername()) && StringUtils.hasText(credentials.getPassword()))
        {
            http.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
        } else {
            // If not all required user details are provided all requests are permitted
            http.authorizeRequests().anyRequest().permitAll();
        }

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(Credentials credentials)
    {
        UserDetails admin = User.builder()
                .username(credentials.getUsername())
                .password(passwordEncoder().encode(credentials.getPassword()))
//                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

}
