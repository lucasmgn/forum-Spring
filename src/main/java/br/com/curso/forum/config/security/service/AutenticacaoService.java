package br.com.curso.forum.config.security.service;

import br.com.curso.forum.model.Usuario;
import br.com.curso.forum.repository.TopicoRepository;
import br.com.curso.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

//PAra dizer pro spring que essa service tem a lógica de autenticação
@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repository.findByEmail(username);
        if(usuario.isPresent()){
            return usuario.get(); //"usuario.get()" para pegar o usuario
        }
        throw new UsernameNotFoundException("Dados inválidos!");
    }
}
