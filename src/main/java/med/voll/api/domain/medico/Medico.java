package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

//A classe medico é uma entidade JPA - Java Persistence API
@Table(name = "medicos")
@Entity(name = "Medico")
//INTRODUÇÃO DO LOMBOK - Com isso não precisamos ficar digitando todos os codigos abaixo dentro da classe, igual era antigamente
//O Lombok faz a geração em tempo de compilção automatica
@Getter
@NoArgsConstructor //Construtor sem argumentos, a JPA EXIGE!
@AllArgsConstructor
@EqualsAndHashCode(of = "id") //Passar o hash code em cima do id, não em todos os atributos!
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Vai gerar uma id automaticamente no banco de dados
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded //Embeddable Attribute - JPA
    private Endereco endereco;
    private Boolean ativo;

    public Medico(DadosCadastroMedico dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if(dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
