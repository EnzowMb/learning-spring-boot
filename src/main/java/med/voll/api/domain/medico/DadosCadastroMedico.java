package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

//ESTE É O PADRÃO DTO - Data Transfer Object - DadosNome - classe record

public record DadosCadastroMedico(
        //USANDO O BEAN VALIDATION
        @NotBlank(message = "Nome é obrigatório") //Biblioteca do bean validation, verifica se o campo não está vazio e ele tambem verifica se não ta null, fazendo a função do @NotNull
        String nome,
        @NotBlank(message = "{email.obrigatorio}")
        @Email(message = "{email.invalido}") //VEM DO PACOTE DO BEAN VALIDATION, VERIFICA SE O EMAIL TEM @, DOMINIO, ETC
        String email,

        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}") //Passa uma expressão regular, uma regra que deve seguir!! Igual o patter em html
        String crm,
        @NotNull //Não é NotBlank porque não é uma string
        Especialidade especialidade,
        @NotNull
        @Valid //Esta dizendo para o bean validation que dentro deste DTO DadosCadastroMedico um dos atributos é um outro DTO que nele vai ter anotações do bean validation
        DadosEndereco endereco) {
}
