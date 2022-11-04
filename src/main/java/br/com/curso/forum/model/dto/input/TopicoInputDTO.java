package br.com.curso.forum.model.dto.input;

import br.com.curso.forum.model.Curso;
import br.com.curso.forum.model.Topico;
import br.com.curso.forum.repository.CursoRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TopicoInputDTO {

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String titulo;
    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String mensagem;
    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String nomeCurso;

    public Topico converter(CursoRepository repository){
        Curso curso = repository.findByNome(nomeCurso);
        return new Topico(titulo, mensagem, curso);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }
}
