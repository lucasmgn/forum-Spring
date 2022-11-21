package br.com.curso.forum.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@Profile("dev") //para usar essa classe apenas quando for no ambiente de desenvolvimento
public class DevSecurityConfigurations {

    /*
    * Na versão 2.7 do spring ficou sendo utilizado a classe dessa forma
    *
    * No profile Dev, não tem autorização e autenticação, permite todas urls
    * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/**").permitAll()
                .and()
                .csrf().disable();

        return http.build();
    }
}
