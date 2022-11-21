package br.com.curso.forum.controller;

import br.com.curso.forum.model.Topico;
import br.com.curso.forum.model.dto.DetalhesTopicoDTO;
import br.com.curso.forum.model.dto.TopicoDTO;
import br.com.curso.forum.model.dto.form.AtualizacaoTopicoForm;
import br.com.curso.forum.model.dto.form.TopicoForm;

import br.com.curso.forum.repository.CursoRepository;
import br.com.curso.forum.repository.TopicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(path = "/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    /*
    * Utilizando paginação, @RequestParam parametro de URL
    * Para fazer paginação no spring.data
    * Caso eu não informe nenhum parametro para ordenação ou paginação
    * posso deixar o Default @PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10)
    * Cahce é para utilizar em tabelas mais estáticas, que geralmente não atualiza
    * */
    @GetMapping
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoDTO> listar(@RequestParam(required = false) String nomeCurso, Pageable paginacao) {

        if(nomeCurso == null){
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return TopicoDTO.converter(topicos);
        }else{
            Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
            return TopicoDTO.converter(topicos);
        }
    }

    //@PathVariable avisando que o id é uma variavel do path, tem que ser o mesmo nome
    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTopicoDTO> buscarTopicoDTO(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id); // ele n joga exception se não encontrar
        if(topico.isPresent()){
            return ResponseEntity.ok(new DetalhesTopicoDTO(topico.get()));
        }

        return ResponseEntity.notFound().build();
    }

    //@RequestBody para pegar do corpo da requisição, não da (URL)
    //@CacheEvict Invalidando o cache "listaDeTopicos" e limpando todos os registros,
    // já que houve cadastro ou modificação nos endpoints
    @PostMapping
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
        Topico topico = topicoForm.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDTO(topico));
    }

    @PutMapping("/{id}")
    @Transactional // para comitar as mudanças
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm topicoAtualizacaoForm) {

        Optional<Topico> optional = topicoRepository.findById(id); // ele n joga exception se não encontrar
        if(optional.isPresent()){
            Topico topico = topicoAtualizacaoForm.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDTO(topico));
        }
        return ResponseEntity.notFound().build();
    }

    //Quando for chamado, o spring irá verificar, implementação de segurança na classe SecurityConfigurations
    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()){
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
