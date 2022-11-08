package br.com.curso.forum.config.security;


import br.com.curso.forum.config.security.service.AutenticacaoService;
import br.com.curso.forum.config.security.service.TokenService;
import br.com.curso.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

//    deve ser utilizada apenas na lógica de autenticação via username/password, para a geração do token.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(); //gerando hash da senha
    }

    /*
    * Tudo o que for ".antMatchers(HttpMethod.GET, "/topicos").permitAll()", será permitido no método GET
    * não vai exigir autentificação
    *
    *".anyRequest().authenticated()", que todas as outras requisições precisam estar autenticadas, tem (acesso restrito)
    *
    * Csrf é uma abreviação para "cross-site request forgery", que é um tipo de ataque hacker que acontece em aplicações web.
    * Como a autenticação é via token, a api está livre desse tipo de ataque
    *
    * ".sessionCreationPolicy(SessionCreationPolicy.STATELESS)", que a politica de sessao é por token
    *
    * ".addFilterBefore" para adicionar o filtro antes do UsernamePasswordAuthenticationFilter.class
    * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/topicos").permitAll()
                .antMatchers(HttpMethod.GET,"/topicos/*").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
