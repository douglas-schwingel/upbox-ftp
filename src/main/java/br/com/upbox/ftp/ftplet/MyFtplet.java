package br.com.upbox.ftp.ftplet;

import br.com.upbox.ftp.server.InMemoryUserManager;
import org.apache.ftpserver.ftplet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;

public class MyFtplet extends DefaultFtplet {
    private static final Logger logger = LoggerFactory.getLogger(MyFtplet.class);
    private static final Marker marker = MarkerFactory.getMarker("my-ftplet");

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
    public FtpletResult beforeCommand(FtpSession session, FtpRequest request) throws FtpException, IOException {
//        TODO terminar os filtros e comandos
        JFileChooser chooser = new JFileChooser();
        FileFilter filter = chooser.getAcceptAllFileFilter();
        chooser.setFileFilter(filter);
        return super.beforeCommand(session, request);
    }
}
