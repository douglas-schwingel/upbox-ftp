package br.com.upbox.ftp.upboxftp;

import br.com.upbox.ftp.server.UpBoxFTPServer;
import org.apache.ftpserver.ftplet.FtpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Runner {
    private static final Logger logger = LoggerFactory.getLogger(Runner.class);
    private static final Marker marker = MarkerFactory.getMarker("runner");
    private static final int PORT = 2112;
    private static UpBoxFTPServer ftp;

    private Runner() {

    }

    public static void run() throws FtpException{
        ftp = new UpBoxFTPServer();
        ftp.setPort(PORT);
        boolean started = ftp.start();
        if(started) logger.info(marker, "Servidor startou na porta {}", PORT);

    }

    public static void restart() throws FtpException {
        ftp.stop();
        logger.warn(marker, "Servidor {}", "parado");
        ftp.start();
        logger.warn(marker, "Servidor {}", "restartou");

    }
}
