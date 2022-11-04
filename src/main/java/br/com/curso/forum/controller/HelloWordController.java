package br.com.curso.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWordController {

    //Seu chamar na porta 8080 ele chama o metodo
    @RequestMapping("/")
    @ResponseBody
    public String hello(){
        return "Hello World!";
    }

}
