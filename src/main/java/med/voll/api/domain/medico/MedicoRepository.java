package med.voll.api.domain.medico;

//ESTA INTERFACE REPOSITORY SERVE PARA FACILITAR A NOSSA VIDA AO INVES DE ESCREVER TODAS AQUELAS CLASSES DAO
// QUE NÃO UTILIZAM SPRING DATA OU OUTRO FRAMEWORKS

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//REPOSITORY - INTERFACES QUE O SPRING JÁ FORNECE A IMPLEMENTAÇÃO PRA GENTE
//Usando JpaRepository ganhamos todos os metodos de um CRUD - Create(save()), Read(findAll()), Update, Delete
public interface MedicoRepository extends JpaRepository<Medico, Long> //<O primeiro objeto é tipo da entidade que esse repository vai trabalha, O segundo objeto é o tipo do atributo da chave primaria dessa entidade(no caso o atributo de de ID é Long)>
{

    Page<Medico> findAllByAtivoTrue(Pageable paginacao); //ELE FAZ AUTOMATICAMENTE DE ACRODO COM A NOMECLATURA Q VC DIGITOU: FindAllByNomeatributoModificação
}
