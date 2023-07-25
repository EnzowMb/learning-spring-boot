package med.voll.api.Infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    //Garante que o Spring vai executar essa classe apenas uma unica vez para cada requisição
    //Para validar o token é apenas uma vez para cada requisição

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("CHAMANDO FILTRO");

        var tokenJWT = recuperarToken(request);

        if(tokenJWT != null) {
            var subject  = tokenService.getSubject(tokenJWT);
            var usuario = repository.findByLogin(subject);

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            //ESSE USERNAMEPASSOWRD... É COMO SE FOSSE UM DTO DO SPRING QUE REPRESENTA UM USUARIO LOGADO NO SISTEMA
            //PARAMETROS -> (Objeto usuario, null, qual é a permisao desse usuario)

            //VAI DIZER PARA A SPRING QUE ESTE USUARIO ESTA LOGADO!!
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("LOGADO NA REQUISIÇÃO");
        }

        filterChain.doFilter(request, response); //ESTE CHAMA OS PROXIMOS FILTROS NA APLICAÇÃO
        //Se não tiver mais nenhum filtro, ele vai pros controllers
        //Para interromper é so n chamar o filterChain.doFilter

    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization"); //Pega o cabeçalho e da um nome de Authorization
        if(authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "").trim(); //Tira o nome do prefixo padrão de tokens JWT e coloca nada
        }

        return null;
    }
}
