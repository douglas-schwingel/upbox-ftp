package br.com.upbox.ftp.utils;

import br.com.upbox.ftp.ftplet.MyFtplet;
import br.com.upbox.ftp.server.MyPasswordEncryptor;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.File;
import java.io.IOException;

public class UserManagerUtil {
    private static final Logger logger = LoggerFactory.getLogger(UserManagerUtil.class);
    private static final Marker marker = MarkerFactory.getMarker("UserManagerUtils");

    public static UserManager getUserManager() {
        PropertiesUserManagerFactory umf = new PropertiesUserManagerFactory();
        try {
            new File("myuser.properties").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        umf.setFile(new File("myuser.properties"));
        MyPasswordEncryptor passwordEncryptor = new MyPasswordEncryptor();
        umf.setPasswordEncryptor(passwordEncryptor);
        return umf.createUserManager();
    }

    public static void criaUsuario(FtpSession session) {
        User user = session.getUser();
        UserManager userManager = getUserManager();
        try {
            if (userManager.doesExist(user.getName())) {
                userManager.save(user);
                logger.info(marker, "Usuario {} não existe. Criando...", user.getName());
            }
        } catch (FtpException e) {
            e.printStackTrace();
        }
    }
}
