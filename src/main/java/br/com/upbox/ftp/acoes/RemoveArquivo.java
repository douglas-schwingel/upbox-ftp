package br.com.upbox.ftp.acoes;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

public class RemoveArquivo extends Acao {

    FTPClient ftpClient;

    public RemoveArquivo(FTPClient ftpClient) {
        super(ftpClient);
    }

    @Override
    protected boolean deveExecutarAcao(String caminhoDoArquvio) throws IOException {
        return ftpClient.deleteFile(caminhoDoArquvio);
    }
}
