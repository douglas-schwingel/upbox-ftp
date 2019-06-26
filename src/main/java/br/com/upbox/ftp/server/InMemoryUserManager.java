package br.com.upbox.ftp.server;

import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserManager implements UserManager {

    private BaseUser user;

    public void setUser(BaseUser _user) {
        user = _user;
        if(user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
            List<Authority> autorizacoes = new ArrayList<>();
            autorizacoes.add(new WritePermission());
            autorizacoes.add(new ConcurrentLoginPermission(10, 10));
            user.setAuthorities(autorizacoes);
        }
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

    private boolean verificaPassword(String password) {
        return BCrypt.checkpw(password, user.getPassword());
    }
}
