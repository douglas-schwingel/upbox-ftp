package br.com.upbox.ftp.ftplet;

import org.apache.ftpserver.ftplet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.IOException;

public class UserFtplet implements Ftplet {
    private static final Logger logger = LoggerFactory.getLogger(UserFtplet.class);
    private static final Marker marker = MarkerFactory.getMarker("userFtplet");

    @Override
    public void init(FtpletContext ftpletContext) throws FtpException {
        logger.info(marker, "Iniciando o UserFtplet");
    }

    @Override
    public void destroy() {

    }

    @Override
    public FtpletResult beforeCommand(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return null;
    }

    @Override
    public FtpletResult afterCommand(FtpSession session, FtpRequest request, FtpReply reply) throws FtpException, IOException {
        return null;
    }

    @Override
    public FtpletResult onConnect(FtpSession session) throws FtpException, IOException {

        return null;
    }

    @Override
    public FtpletResult onDisconnect(FtpSession session) throws FtpException, IOException {
        return null;
    }
}
