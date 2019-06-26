package br.com.upbox.ftp.ftplet;

import br.com.upbox.ftp.server.InMemoryUserManager;
import org.apache.ftpserver.ftplet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class MyFtplet extends DefaultFtplet {
    private static final Logger logger = LoggerFactory.getLogger(MyFtplet.class);
    private static final Marker marker = MarkerFactory.getMarker("my-ftplet");

    @Autowired
    private InMemoryUserManager userManager;

    @Override
    public void init(FtpletContext ftpletContext) throws FtpException {
        logger.info(marker, "Iniciando o MyFtplet");
    }

    @Override
    public FtpletResult onLogin(FtpSession session, FtpRequest request) throws FtpException, IOException {
        logger.info(marker, "Usuario {} logado", session.getUser().getName());
        return super.onLogin(session, request);
    }

    @Override
    public FtpletResult onConnect(FtpSession session) throws FtpException, IOException {
        User user = session.getUser();
        logger.info(marker, "Entrando no connect com usuario {}", user.getName());
        userManager.setUser(user.getName(), user.getPassword());
        return super.onConnect(session);
    }
}
