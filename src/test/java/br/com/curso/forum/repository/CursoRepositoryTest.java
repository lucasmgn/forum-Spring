package br.com.curso.forum.repository;

import br.com.curso.forum.model.Curso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest //criando uma classe de test que vai testar um repository
//spring não substitua o banco de dados, o padrão é ele usar em memória
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    void deveriaCarregarUmCursoAoBuscarPeloSeuNome(){
        String nomeCurso = "HTML 5";

        Curso curso = new Curso("HTML 5", "Programação");

        em.persist(curso);

        Curso cursoCarregado = repository.findByNome(nomeCurso);

        Assertions.assertNotNull(cursoCarregado);
        Assertions.assertEquals(nomeCurso, curso.getNome());
    }

    @Test
    void naoDeveriaCarregarUmCursoQueNaoEstejaCadastrado(){
        String nomeCurso = "JPA";
        Curso curso = repository.findByNome(nomeCurso);

        Assertions.assertNull(curso);
    }
}
