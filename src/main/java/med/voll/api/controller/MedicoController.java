package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
public class MedicoController {


    //MECANISMO DE INJEÇÃO DE DEPENDENCIAS
    @Autowired //Indica para a spring que ela deve instanciar esse atributo pra gente, já que ele reconhece essa interface repository, ela consegue criar esse objeto e passar pro controle tudo AUTOMATICAMENTE
    private MedicoRepository repository;
    @PostMapping //Spring, se chegar uma requisição do tipo POST na URL de medicos é para vc chamar este metodo
    @Transactional //Vai fazer uma transação ativa com o banco de dados
    //O @Valid é pedir pra o spring integrar com o Bean validation e executar as validações em cima deste DTO
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) { //O RequestBody serve para dizer q ele está vindo do corpo da requisição, ou seja, o arquivo json
        var medico = new Medico(dados);
        repository.save(medico); //Metodo para fazer o insert na tabela do banco de dados

        //uri Representa o endereço, o spring vai criar o cabeçalho location baseado nesse uri
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        //o path é o caminho que ele vai usar
        //buildAndExpand é qual id vai usar a partir do caminho do path
        //O spring utiliza UriComponentsBuilder que encapsula o endereço da uri

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
        //Este metodo created devolve um codigo 201(arquivo txt explicando na pasta!
        //body() qual a informação quer devolver no corpo da resposta.
    }

    @GetMapping
    //Deve ser Page por causa da paginação
    //O @PageableDefault é pra atribuir uma paginação padrão | size - quantidade de elementos na pagina | page - qual a pagina inicial q vai aparecer
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable paginacao) { //Pageable é um requsição para fazer paginação, devemos passar como parametro no findAll pq existe uma sobrecarga q recebe ela!
        //Aqui vamos usar o stream para trabalhar com coleções de dados de forma mais eficiente e expressiva, podendo utilizar o filtragem, mapeamento, redução, ordenação, ect | nessa caso usamos o map para mudar o JpaRepository do respository para DadosLstagemMedico e já instanciando ele pela primeira vez!
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); //Este metodo findAll faz uma lista
        return ResponseEntity.ok(page);
    }


    @PutMapping //Seria um mapeamento para atualizar, funciona como o U do CRUD
    @Transactional //Como vai fazer uma escrita no banco de dados, precisa do @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        //Não precisa fazer mais nada porque td esse trecho de codigo vai rodar em uma transação(@Transactional) e
        //ele atualiza os dados quandoa transação for comitada, ela percebe q teve mudança nos atributos e a JPA faz o update sozinho

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}") //Se for escrito /3, é para chamar o metodo excluir do nosso MedicoController
    // Um parametro /{} significa q ele é dinamico ai da um nome pra ele
    @Transactional //Como vai fazer uma escrita no banco de dados, precisa do @Transactional
    public ResponseEntity excluir(@PathVariable Long id) { //O @PathVariable significa q deve usar esse id como parametro do DeleteMapping
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

}
