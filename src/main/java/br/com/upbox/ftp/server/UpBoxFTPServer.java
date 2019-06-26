package br.com.upbox.ftp.server;

import br.com.upbox.ftp.ftplet.MyFtplet;
import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UpBoxFTPServer implements FTPServerConstants {
    private static final Logger logger = LoggerFactory.getLogger(FtpServer.class);
    private static Marker marker = MarkerFactory.getMarker("ftp-server");


    private FtpServerFactory serverFactory;
    private FtpServer server;
    private static int port;
    private InMemoryUserManager userManager;

    public UpBoxFTPServer() {
        port = 21;
        userManager = new InMemoryUserManager();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean start() throws FtpException {
        serverFactory = new FtpServerFactory();


        //          Habilitar FTPS
//        SslConfigurationFactory sslConfig = new SslConfigurationFactory();
//        sslConfig.setKeystoreFile(new File(KEYS_PATH));
//        sslConfig.setKeystorePassword(KEYSTORE_PASS);

        // Configurar listener
        ListenerFactory listenerFactory = new ListenerFactory();
        listenerFactory.setPort(port);
        listenerFactory.setIdleTimeout(60);
//        listenerFactory.setSslConfiguration(sslConfig.createSslConfiguration());
//        listenerFactory.setImplicitSsl(true);


        // Configurar conexão
        ConnectionConfigFactory connectionConfigFactory = new ConnectionConfigFactory();
        connectionConfigFactory.setAnonymousLoginEnabled(false);
        connectionConfigFactory.setMaxLogins(10);
        connectionConfigFactory.setMaxThreads(10);


        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File("myuser.properties"));
        MyPasswordEncryptor passwordEncryptor = new MyPasswordEncryptor();
        userManagerFactory.setPasswordEncryptor(passwordEncryptor);
        Map<String, Ftplet> map = new HashMap<>();
        map.put("myftplet", new MyFtplet());
        //Settando as configurações no ServerFactory
        serverFactory.setFtplets(map);
        serverFactory.addListener("default", listenerFactory.createListener());
        serverFactory.setConnectionConfig(connectionConfigFactory.createConnectionConfig());
        serverFactory.setUserManager(userManager);

        server = serverFactory.createServer();
        try {
            server.start();
        } catch (FtpException e) {
            logger.info(marker, "Servidor não startado: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public void stop() {
        if (server != null && !server.isStopped()) {
            server.stop();
            server = null;
        }
    }

    public void criarUsuario(String username, String password) {
        BaseUser user = new BaseUser();
        user.setName(username);
        if (password != null && password.length() > 0) user.setPassword(hashPassword(password));
        user.setHomeDirectory(System.getProperty("user.home") + "/upbox-files/" + username);
        user.setEnabled(true);
        userManager.setUser(user);
    }

    private String hashPassword(String password) {
        String salto = BCrypt.gensalt();
        return BCrypt.hashpw(password, salto);
    }

}
