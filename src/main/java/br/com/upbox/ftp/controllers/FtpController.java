package br.com.upbox.ftp.controllers;

import br.com.upbox.ftp.server.InMemoryUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class FtpController {

    @Autowired
    private InMemoryUserManager userManager;

    @PostMapping(value = "/cria_usuario", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity criaUsuario(@RequestParam("username") String username,
                                                  @RequestParam("password") String password) {

        if (userManager.setUser(username, password)) {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @PostMapping(value = "/lista_arquivos")
//    public String listaArquivos(String username)
}
