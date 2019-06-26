package br.com.upbox.ftp.server;

import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryUserManager implements UserManager {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryUserManager.class);
    private static final Marker marker = MarkerFactory.getMarker("userManager");

    private BaseUser user;

    private UserManager userManager;

    public InMemoryUserManager() {
        userManager = criaUserManager();
    }

    @Bean
    private UserManager criaUserManager() {
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        try {
            new File("myuser.properties").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        userManagerFactory.setFile(new File("myuser.properties"));
        MyPasswordEncryptor passwordEncryptor = new MyPasswordEncryptor();
        userManagerFactory.setPasswordEncryptor(passwordEncryptor);
        return userManagerFactory.createUserManager();
    }

    public boolean setUser(String nome, String password) {
        user = new BaseUser();
        user.setName(nome);
        user.setPassword(hashPassword(password));
        if (!Files.exists(Path.of(userDir(nome)))) {
            Runtime.getRuntime().exec("mkdir" + )
        }
        user.setHomeDirectory(userDir(nome));
        user.setEnabled(true);
        if(user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
            List<Authority> autorizacoes = new ArrayList<>();
            autorizacoes.add(new WritePermission());
            autorizacoes.add(new ConcurrentLoginPermission(10, 10));
            user.setAuthorities(autorizacoes);
        }
        try {
            userManager.save(user);
            logger.info(marker, "Usuario {} criado com sucesso", user.getName());
            return true;
        } catch (FtpException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String userDir(String nome) {
        return System.getProperty("user.home") + "/upbox-files/" + nome;
    }


    @Override
    public User getUserByName(String username) throws FtpException {
        return user;
    }

    @Override
    public String[] getAllUserNames() throws FtpException {
        return new String[]{user.getName()};
    }

    @Override
    public void delete(String username) throws FtpException {
    }

    @Override
    public void save(User user) throws FtpException {

    }

    @Override
    public boolean doesExist(String username) throws FtpException {
        return (user.getName().equals(username));
    }

    @Override
    public User authenticate(Authentication authentication) throws AuthenticationFailedException {
        if (authentication != null && authentication instanceof UsernamePasswordAuthentication) {
            UsernamePasswordAuthentication userAuth = (UsernamePasswordAuthentication) authentication;
            if(user.getName().equals(userAuth.getUsername()) && verificaPassword(userAuth.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public String getAdminName() throws FtpException {
        return user.getName();
    }

    @Override
    public boolean isAdmin(String username) throws FtpException {
        return (user.getName().equals(username));
    }

    private String hashPassword(String password) {
        String salto = BCrypt.gensalt();
        return BCrypt.hashpw(password, salto);
    }

    private boolean verificaPassword(String password) {
        return BCrypt.checkpw(password, user.getPassword());
    }
}
