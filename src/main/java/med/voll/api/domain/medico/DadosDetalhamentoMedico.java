package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.Endereco;

//ESTE É O PADRÃO DTO - Data Transfer Object - DadosNome - classe record

public record DadosDetalhamentoMedico(
        Long id,
        String nome,
        String email,
        String crm,

        String telefone,
        Especialidade especialidade,
        Endereco endereco
) {

    public DadosDetalhamentoMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
    }

}
