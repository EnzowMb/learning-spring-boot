package med.voll.api.domain.paciente;

//ESTE É O PADRÃO DTO - Data Transfer Object - DadosNome - classe record

public record DadosListagemPaciente(Long id, String nome, String email, String cpf) {

    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
