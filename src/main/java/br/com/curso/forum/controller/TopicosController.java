package br.com.curso.forum.controller;

import br.com.curso.forum.model.Topico;
import br.com.curso.forum.model.dto.TopicoDTO;
import br.com.curso.forum.model.dto.input.TopicoInputDTO;

import br.com.curso.forum.repository.CursoRepository;
import br.com.curso.forum.repository.TopicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDTO> listar(){
        List<Topico> topicos = topicoRepository.findAll();
        return TopicoDTO.converter(topicos);
    }

//    @GetMapping
//    public List<TopicoDTO> listarPorNome(String nomeCurso){
//        List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
//        return TopicoDTO.converter(topicos);
//    }

    //@RequestBody para pegar do corpo da requisição, não da (URL)
    @PostMapping
    public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoInputDTO topicoInputDTO, UriComponentsBuilder uriBuilder){
        Topico topico = topicoInputDTO.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDTO(topico));

    }

}
