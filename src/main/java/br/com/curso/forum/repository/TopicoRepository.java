package br.com.curso.forum.repository;

import br.com.curso.forum.model.Topico;
import br.com.curso.forum.model.dto.TopicoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository <Topico, Long>{

    /*
    Esse underline vai dizer para o Spring que Curso vai ser um relacionamento e lá dentro do relacionamento é
    que tem um atributo Nome. Agora ele sabe que não é o atributo CursoNome. É o relacionamento Curso e,
    dentro dele, Nome. Se você quisesse filtrar pelo atributo, é só não colocar o underline.
    O Spring monta a query
    find by entidade Curso a propriedade nome
     */
    List<Topico> findByCursoNome(String nomeCurso);


    //Se eu não quiser utilizar o findBy, eu terei que construir a minha prorpria query
    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
    List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
}
