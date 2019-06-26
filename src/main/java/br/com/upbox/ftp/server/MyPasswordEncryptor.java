package br.com.upbox.ftp.server;

import org.apache.ftpserver.usermanager.PasswordEncryptor;
import org.mindrot.jbcrypt.BCrypt;

public class MyPasswordEncryptor implements PasswordEncryptor {
    @Override
    public String encrypt(String password) {
        String salto = BCrypt.gensalt();
        return BCrypt.hashpw(password, salto);
    }

    @Override
    public boolean matches(String passwordToCheck, String storedPassword) {
        return BCrypt.checkpw(passwordToCheck, storedPassword);
    }
}
