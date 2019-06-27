//package br.com.upbox.ftp.acoes;
//
//import br.com.upbox.ftp.server.InMemoryUserManager;
//import org.apache.commons.net.ftp.FTPClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.Marker;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.io.IOException;
//
//public abstract class Acao {
//    private static final Logger logger = LoggerFactory.getLogger(Acao.class);
//
//
//    public Acao(FTPClient ftpClient) {
//    }
//
//    protected Acao() {
//    }
//
//    public ResponseEntity executa(String caminhoDoArquivo, String acao, Marker marker,
//                                  HttpStatus sucesso) {
//        boolean ehSucesso;
//        try {
//            ehSucesso = deveExecutarAcao(caminhoDoArquivo);
//        } catch (IOException e) {
//            logger.error(marker, "Erro ao executar {} arquivo no local {}", acao, caminhoDoArquivo);
//            ehSucesso = false;
//        }
//        return (ehSucesso) ? new ResponseEntity<>(sucesso) :
//                new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }
//
//    protected abstract boolean deveExecutarAcao(String caminhoDoArquvio) throws IOException;
//
//    private String getPathname(InMemoryUserManager userManager, String nomeArquivo, String owner) {
//        return userManager.userDir(owner) + "/" + nomeArquivo;
//    }
//
//}
