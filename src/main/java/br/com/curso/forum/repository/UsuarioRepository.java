package br.com.curso.forum.repository;

import br.com.curso.forum.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Devolvendo como ption pq pode não ser encontrado um e-mail
    Optional <Usuario> findByEmail(String email);
}
