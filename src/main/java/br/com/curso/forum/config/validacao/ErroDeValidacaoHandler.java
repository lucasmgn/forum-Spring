package br.com.curso.forum.config.validacao;

import br.com.curso.forum.config.validacao.model.ErroDeFormulario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDeFormulario> handle(MethodArgumentNotValidException exception){

        List<ErroDeFormulario> erroDeFormularios = new ArrayList<>();

        //Listando todos os erros
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(e -> {
            //Pegando a mensagem no idioma correto
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroDeFormulario erro = new ErroDeFormulario(e.getField(), mensagem);
            erroDeFormularios.add(erro);
        });
        return erroDeFormularios;
    }
}
