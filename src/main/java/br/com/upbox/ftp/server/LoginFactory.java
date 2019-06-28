package br.com.upbox.ftp.server;

import br.com.upbox.ftp.utils.UserManagerUtil;
import org.apache.ftpserver.ftplet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.IOException;

public class LoginFactory implements Ftplet {
    private static final Logger logger = LoggerFactory.getLogger(LoginFactory.class);
    private static Marker marker = MarkerFactory.getMarker("loginFactory");
    private String nome;
    private String password;
    private boolean naoExiste;


    @Override
    public void init(FtpletContext ftpletContext) throws FtpException {
        logger.info(marker, "Inciando o LoginFacotry");
    }

    @Override
    public void destroy() {

    }

    @Override
    public FtpletResult beforeCommand(FtpSession session, FtpRequest request) throws FtpException, IOException {
        String command = request.getCommand();
        logger.info(marker, "Comando recebido {} no LoginFactory", command);
        if(UserManagerUtil.deletaUsuario(request, command)) return null;
        if (UserManagerUtil.criaNovoUsuarioSeNaoExistir(request, command)) return null;
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
