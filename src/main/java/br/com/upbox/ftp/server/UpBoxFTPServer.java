package br.com.upbox.ftp.server;

import br.com.upbox.ftp.ftplet.MyFtplet;
import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpBoxFTPServer implements FTPServerConstants {
    private static final Logger logger = LoggerFactory.getLogger(FtpServer.class);
    private static Marker marker = MarkerFactory.getMarker("ftp-server");


    private FtpServerFactory serverFactory;
    private FtpServer server;
    private static int port;

    @Autowired
    private UserManager userManager;

    public UpBoxFTPServer() {
        port = 21;
    }


    public void setPort(int port) {
        this.port = port;
    }

    public boolean start() throws FtpException, IOException {
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


        Map<String, Ftplet> map = new HashMap<>();
        map.put("myftplet", new MyFtplet());
//        Settando as configurações no ServerFactory
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

//    public void criarUsuario(String username, String password) {
//        BaseUser user = new BaseUser();
//        user.setName(username);
//        if (password != null && password.length() > 0) user.setPassword(hashPassword(password));
//        user.setHomeDirectory(System.getProperty("user.home") + "/upbox-files/" + username);
//        user.setEnabled(true);
//        try {
//            userManager.save(user);
//        } catch (FtpException e) {
//            logger.error(marker, "Erro {} ao adiciona o usuario {}", e.getMessage(), user.getName());
//        }
//    }



}
