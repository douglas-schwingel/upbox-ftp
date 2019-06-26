package br.com.upbox.ftp.models;

import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDTO {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioDTO.class);
    private static Marker marker = MarkerFactory.getMarker("usuarioDto");

    @Autowired
    private UserManager userManager;

    public void criarUsuario(String username, String password) {
        BaseUser user = new BaseUser();
        user.setName(username);
        if (password != null && password.length() > 0) user.setPassword(hashPassword(password));
        user.setHomeDirectory(System.getProperty("user.home") + "/upbox-files/" + username);
        user.setEnabled(true);
        try {
            userManager.save(user);
        } catch (FtpException e) {
            logger.error(marker, "Erro {} ao adiciona o usuario {}", e.getMessage(), user.getName());
        }
    }

    private String hashPassword(String password) {
        String salto = BCrypt.gensalt();
        return BCrypt.hashpw(password, salto);
    }
}
