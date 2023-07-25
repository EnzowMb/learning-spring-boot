package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.Infra.security.DadosTokenJWT;
import med.voll.api.Infra.security.TokenService;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired //O spring security não sabe injetar um objeto AuthenticationManager, nos temos que configurar isso na classe Security Configuration
    private AuthenticationManager manager; //classe do proprio Spring que dispara o processo de autenticação por de baixo dos panos

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha()); //Como se fosse um DTO do proprio Spring
        var authentication =  manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        //authentication.getPrincipal() -> Pega o usuario Logado!

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

}
