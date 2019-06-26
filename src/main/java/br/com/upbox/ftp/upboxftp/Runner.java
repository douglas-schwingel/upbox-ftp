package br.com.upbox.ftp.upboxftp;

import br.com.upbox.ftp.server.UpBoxFTPServer;
import org.apache.ftpserver.ftplet.FtpException;

import java.io.IOException;

public class Runner {

    public static void run() throws FtpException, IOException {
        UpBoxFTPServer ftp = new UpBoxFTPServer();
        ftp.setPort(2112);
        ftp.start();
    }
}
