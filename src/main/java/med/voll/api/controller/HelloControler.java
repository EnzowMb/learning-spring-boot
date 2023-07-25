package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Esta clase é um controller da API Rest
@RequestMapping("/hello") //Este é o mapeamento
public class HelloControler {

    @GetMapping //Do tipo get, que vai retornar os dados!
    public String olaMundo() {
        return "Hello World Spring!";
    }

}
