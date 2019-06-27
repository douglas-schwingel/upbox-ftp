package br.com.upbox.ftp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

//@Controller
public class FtpController {
    private static final Logger logger = LoggerFactory.getLogger(FtpController.class);
    private static final Marker marker = MarkerFactory.getMarker("ftpController");

//    @Autowired
//    private InMemoryUserManager userManager;

//    @Autowired
//    private MyFtplet myFtplet;

//    @PostMapping(value = "/cria_usuario", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity criaUsuario(@RequestParam("username") String username,
//                                                  @RequestParam("password") String password) {
//        if (userManager.setUser(username, password)) {
//            return new ResponseEntity<>(null, HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

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
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
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

//    private String getPathname(String nomeArquivo, String owner) {
//        return userManager.userDir(owner) + "/" + nomeArquivo;
//    }
//
//    private String getPathname(String owner) {
//        return userManager.userDir(owner);
//    }

}
