//package br.com.upbox.ftp.acoes;
//
//import org.apache.commons.net.ftp.FTPClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.Marker;
//import org.slf4j.MarkerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//public class InsereArquivo {
//    private static final Logger logger = LoggerFactory.getLogger(InsereArquivo.class);
//    private static final Marker marker = MarkerFactory.getMarker("insereArquivo");
//
//    private final FTPClient ftpClient;
//    private InputStream arquivoIS;
//
//    public InsereArquivo(FTPClient ftpClient, InputStream arquivoIS) {
//        this.ftpClient = ftpClient;
//        this.arquivoIS = arquivoIS;
//    }
//
//    public ResponseEntity executa(String caminhoDoArquivo, String acao, Marker marker, HttpStatus sucesso) {
//        boolean ehSucesso;
//        try {
//            ehSucesso = deveExecutarAcao(caminhoDoArquivo, arquivoIS);
//        } catch (IOException e) {
//            logger.error(marker, "erro no executa do InsereArquivo");
//            ehSucesso = false;
//        }
//        return (ehSucesso) ? new ResponseEntity<>(sucesso) :
//                new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }
//
//    protected boolean deveExecutarAcao(String caminhoDoArquvio, InputStream arquivo) throws IOException {
//        if (arquivo == null) {
//            logger.error(marker, "Arquivo é nulo por algum motivo");
//        }
//        logger.info(marker, "Entrando no deveExecutarAcao: {}", caminhoDoArquvio);
//        return ftpClient.storeFile(caminhoDoArquvio, arquivo);
//    }
//
//}