package br.com.curso.forum.repository;

import br.com.curso.forum.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNome(String nome);
}
