package med.voll.api.Infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}") //Ele vai ler o atributo que ta EM APPLICATION.PROPERTIES
    private String secret;
    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret); //Deve passar um algoritimo e dentro do parametro dele, uma senha secreta SÓ SUA, para poder gerar o token
            return JWT.create()
                    .withIssuer("API Voll.med") //Qual ferramenta que foi responsavel pela geração do token, pode colocar outro nome para indentificar que é SEU
                    .withSubject(usuario.getLogin()) //Quem é o dono desse token, a pessoa(usuario) relacionada com esse token
                    .withClaim("id", usuario.getId()) //Pode passar o que vc quiser pra guardar dentro de algoritmo, como o ID, Perfil, etc
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    //Este metodo recebe um tokenJWT e ele verifica se esse token está valido e devolver o usuario que esta armazenado dentro do token
    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Voll.med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        //Esta dizendo que: A hora de agora + 2 horas ela vai expirar no instante da zona do tempo em -03:00 que é o fuso horario do Brasil!
    }

}
