package med.voll.api.Infra.security;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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

@Configuration
@EnableWebSecurity //Indica para o spring que vamos personalizar as confirações de segurança da web, em vez de stateful, para usar stateless
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //Configura processos de autenticação e autorização
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    req.anyRequest().authenticated();
                }).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        //csrf(Cross-Site Request Forgery) - esta desabilidando a proteção contra esses ataques porque o proprio JWT utilizando tokens já faz essa proteção pra gente - o proprio token já é a proteção
        //sessionManagement() - como vai ser o gerenciamento da sessão
        //sessionCreationPolicy() - qual a politica de criação da sessão - Nesse caso para Stateless
        //authorizeHttpRequests - configura como vai ser a autorização das requisições
        //requestMatchers - metodo onde configura URL, se ela vai ser liberada ou bloqueada(ONDE PERMITIR QUE O USUARIO NÃO PRECISA ESTAR LOGADO PARA FAZER LOGIN!!! )
        //(HttpMethod.POST, - significa que a requisição de login em especifico é do tipo POST
        //"/login").permitAll() - se vier uma requisição para url /login e VIER COM O METODO POST(HttpMethod.POST) LIBERE TUDO, se checa se o usuario ta logado, até pq n faz sentido
        //anyRequest().authenticated() - em qualquer outra requisição, deve se fazer a autenticação
        //.addFilterBefore(Chamar este filtro antes, desse filtro)
    }

    @Bean //Ensina pro spring como que cria um objeto que eu vou injetar em algum lugaar ou que ele vai usar internamente
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //Ensinando para o Spring Security que é pra utilizar esse hash de senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
