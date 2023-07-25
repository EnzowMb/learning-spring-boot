package med.voll.api.domain.paciente;

import med.voll.api.domain.endereco.Endereco;

//ESTE É O PADRÃO DTO - Data Transfer Object - DadosNome - classe record

public record DadosDetalhamentoPaciente(
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco) {

            public DadosDetalhamentoPaciente(Paciente paciente) {
                this(paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), paciente.getEndereco());
            }

}
