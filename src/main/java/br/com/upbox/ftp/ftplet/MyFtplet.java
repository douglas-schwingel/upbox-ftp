package br.com.upbox.ftp.ftplet;

import br.com.upbox.ftp.server.InMemoryUserManager;
import org.apache.ftpserver.ftplet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class MyFtplet extends DefaultFtplet {
    private static final Logger logger = LoggerFactory.getLogger(MyFtplet.class);
    private static final Marker marker = MarkerFactory.getMarker("my-ftplet");

    private InMemoryUserManager userManager = new InMemoryUserManager();

    @Override
    public void init(FtpletContext ftpletContext) throws FtpException {
        logger.info(marker, "Iniciando o MyFtplet");
    }

    @Override
    public FtpletResult onLogin(FtpSession session, FtpRequest request) throws FtpException, IOException {
        User user = session.getUser();
        if (!userManager.doesExist(user.getName())) {
            logger.info(marker, "Usuario {} não existe. Criando...", user.getName());
            userManager.setUser(user.getName(), user.getPassword());
        }
        logger.info(marker, "Usuario {} encontrado. Loggando...", user.getName());
        return super.onLogin(session, request);
    }

    @Override
    public FtpletResult onConnect(FtpSession session) throws FtpException, IOException {
        User user = session.getUser();
        logger.info(marker, "Entrando no connect com usuario {}", user.getName());
        userManager.setUser(user.getName(), user.getPassword());
        return super.onConnect(session);
    }

    @Override
    public FtpletResult onDeleteStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return super.onDeleteStart(session, request);
    }

//    @Override
//    public FtpletResult onUploadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
//        User user = session.getUser();
//        String currentDir = session.getFileSystemView().getWorkingDirectory().getAbsolutePath();
//        String fileName = request.getArgument();
//
//        File file = new File(user.getHomeDirectory() + currentDir + fileName);
//        return super.onUploadStart(session, request);
//    }
}
