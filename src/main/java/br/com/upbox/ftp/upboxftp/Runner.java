package br.com.upbox.ftp.upboxftp;

import br.com.upbox.ftp.server.UpBoxFTPServer;
import org.apache.ftpserver.ftplet.FtpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Runner {
    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    private ServerSocket socket;

    public Runner() {
        this.socket = new ServerSocket(2121);
    }

    public void escuta() throws IOException {
        Socket servidor = socket.accept();
    }

    public static void run() throws FtpException, IOException {

        UpBoxFTPServer ftp = new UpBoxFTPServer();
        ftp.setPort(2112);
        ftp.start();
    }
}
