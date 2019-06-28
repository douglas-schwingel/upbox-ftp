package br.com.upbox.ftp.utils;

import br.com.upbox.ftp.server.MyPasswordEncryptor;
import br.com.upbox.ftp.upboxftp.Runner;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.PasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.SaltedPasswordEncryptor;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        umf.setPasswordEncryptor(new ClearTextPasswordEncryptor());
        return umf.createUserManager();
    }

    public static void criaUsuario(String nome, String senha) {
        Map<String, String> map = salva(nome, senha);
        logger.info(marker, map.get("mensagem"), nome);
    }

    private static Map<String, String> salva(String nome, String senha) {
        UserManager userManager = getUserManager();
        BaseUser user = new BaseUser();
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);
        user.setName(nome);
        user.setPassword(senha);
        user.setHomeDirectory(getUserDir(nome));
        Map<String, String> map = new HashMap<>();
        String msg = "";
        try {
            if (!userManager.doesExist(nome)) {
                userManager.save(user);
                Runner.restart();
                msg = "Usuario {} não existe. Novo usuario criando com sucesso";
            } else {
                msg =  "Usuario {} já existe.";
            }
        } catch (FtpException e) {
            msg = "Usuario {} não pôde ser salvo.";
        }
        map.put("mensagem", msg);
        return map;
    }

    public static boolean naoExiste(String nome) {
        User userByName = null;
        try {
            userByName = getUserManager().getUserByName(nome);
        } catch (FtpException e) {
            logger.error(marker, "Erro em buscar por nome: {}", e.getMessage());
        }
        return userByName == null;
    }

    private static String getUserDir(String nome) {
        return System.getProperty("user.home") + "/upbox-files/" + nome;
    }
}
