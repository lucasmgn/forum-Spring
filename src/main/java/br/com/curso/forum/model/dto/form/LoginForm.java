package br.com.curso.forum.model.dto.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

//Pegando os parametros que vem do cliente
public class LoginForm {

    private String email;
    private String senha;

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(email,senha);
    }
}
