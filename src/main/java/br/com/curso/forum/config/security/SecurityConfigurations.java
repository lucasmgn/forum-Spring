package br.com.curso.forum.config.security;


import br.com.curso.forum.config.security.service.AutenticacaoService;
import br.com.curso.forum.config.security.service.TokenService;
import br.com.curso.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Profile(value = {"prod", "test"}) //adicionando mais de um profile
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
    * Na versão 2.7 do spring ficou sendo utilizado a classe dessa forma
    *
    * Tudo o que for ".antMatchers(HttpMethod.GET, "/topicos").permitAll()", será permitido no método GET
    * não vai exigir autentificação
    *
    *".anyRequest().authenticated()", que todas as outras requisições precisam estar autenticadas, tem (acesso restrito)
    *
    * Csrf é uma abreviação para "cross-site request forgery", sendo um tipo de ataque hacker que acontece em aplicações web.
    * Como a autenticação é via token, a api está livre desse tipo de ataque
    *
    * ".sessionCreationPolicy(SessionCreationPolicy.STATELESS)", que a politica de sessao é por token
    *
    * ".addFilterBefore" para adicionar o filtro antes do UsernamePasswordAuthenticationFilter.class
    *
    * remover o .permitAll() da url actuator, quando estiver em produção (expõe informações impoerantes)
    *
    * .antMatchers(HttpMethod.DELETE, "/topicos/*").hasRole("MODERADOR"), só pode deletar um topico se tiver
    * o perfil moderador
    * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/topicos").permitAll()
                .antMatchers(HttpMethod.GET,"/topicos/*").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.DELETE, "/topicos/*").hasRole("MODERADOR")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    //Configuração para utilizar o swagger
    //Static resources configuration (css, js, img, etc.)
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/**.html",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/configuration/**",
                        "/swagger-resources/**");
    }
}
