package br.com.upbox.ftp;

import br.com.upbox.ftp.server.UpBoxFTPServer;
import org.apache.ftpserver.ftplet.FtpException;

public class Runner {
    public static void main(String[] args) throws FtpException {
        UpBoxFTPServer ftp = new UpBoxFTPServer();
        ftp.setPort(2112);
        ftp.criarUsuario("teste", "teste");
        ftp.start();
    }
}
