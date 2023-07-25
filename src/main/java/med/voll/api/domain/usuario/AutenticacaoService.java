package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service //Esta dizendo para a spring que esta classe é um componente, quero que vc carregue ela para mim,
//, é um componente do tipo Serviço, excecuta o serviço de autenticação
public class AutenticacaoService implements UserDetailsService {
    //Esta interface vai dizer q esta uma classe que vai tratar do SERVIÇO(@Service) de AUTENTICAÇÃO(UserDetailsService)

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }



}
