package br.com.upbox.ftp.controllers;

import br.com.upbox.ftp.acoes.InsereArquivo;
import br.com.upbox.ftp.acoes.RemoveArquivo;
import br.com.upbox.ftp.ftplet.MyFtplet;
import br.com.upbox.ftp.server.FtpConnectionFactory;
import br.com.upbox.ftp.server.InMemoryUserManager;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class FtpController {
    private static final Logger logger = LoggerFactory.getLogger(FtpController.class);
    private static final Marker marker = MarkerFactory.getMarker("ftpController");
    private static final String HOST_NAME = "localhost";

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

//    @PostMapping(value = "/insere")
//    public ResponseEntity insereArquivo(@RequestParam("arquivo") MultipartFile arquivo,
//                                        @RequestParam("owner") String owner,
//                                        @RequestParam("password")String password) throws IOException {
//        FTPClient ftpClient = new FtpConnectionFactory().conecta(owner,password, getPathname(owner));
//        ResponseEntity resposta = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
//        try {
//            logger.info(marker, "entrando no try");
//            resposta = new InsereArquivo(ftpClient, arquivo.getInputStream()).executa(getPathname(owner),
//                    "inserir", marker, HttpStatus.CREATED);
//        } catch (IOException e) {
//            logger.error(marker, "Não foi possível ler o arquivo");
//        }
//        try {
//            ftpClient.disconnect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return resposta;
//    }

//    @PostMapping(value = "/remove")
//    public ResponseEntity removeArquivo(@RequestParam("arquivo") String nomeArquivo,
//                                        @RequestParam("owner") String owner,
//                                        @RequestParam("password")String password) throws IOException {
//        FTPClient ftpClient = new FtpConnectionFactory().conecta(owner, password, getPathname(owner));
//        ResponseEntity resposta = new RemoveArquivo(ftpClient).executa(getPathname(nomeArquivo, owner), "remover",
//                marker, HttpStatus.OK);
//        ftpClient.disconnect();
//        return resposta;
//    }
//
//    @PostMapping(value = "/lista")
//    public ResponseEntity listaArquivos(@RequestParam("owner") String owner,
//                                        @RequestParam("password")String password) throws IOException {
//        FTPClient ftpClient = new FtpConnectionFactory().conecta(owner, password, getPathname(owner));
//        try {
//            String[] ftpFiles = ftpClient.listNames(getPathname(owner));
//            List<String> nomesArquivos = Arrays.asList(ftpFiles);
//            ftpClient.disconnect();
//            return new ResponseEntity<>(nomesArquivos, HttpStatus.OK);
//        } catch (IOException e) {
//            logger.error(marker, "Erro ao buscar arquivos de {}", owner);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

    private String getPathname(String nomeArquivo, String owner) {
        return userManager.userDir(owner) + "/" + nomeArquivo;
    }

    private String getPathname(String owner) {
        return userManager.userDir(owner);
    }

}
