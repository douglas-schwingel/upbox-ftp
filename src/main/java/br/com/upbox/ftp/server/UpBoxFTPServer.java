package br.com.upbox.ftp.server;

import br.com.upbox.ftp.utils.UserManagerUtil;
import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.listener.ListenerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.HashMap;
import java.util.Map;

public class UpBoxFTPServer {
    private static final Logger logger = LoggerFactory.getLogger(FtpServer.class);
    private static Marker marker = MarkerFactory.getMarker("ftp-server");


    private FtpServerFactory serverFactory;
    private FtpServer server;
    private static int port;

    public UpBoxFTPServer() {
        port = 2112;
    }


    public void setPort(int port) {
        this.port = port;
    }

    public boolean start() throws FtpException {
        serverFactory = new FtpServerFactory();


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
        map.put("myFtpler", new LoginFactory());

        serverFactory.addListener("default", listenerFactory.createListener());
        serverFactory.setFtplets(map);
        serverFactory.setConnectionConfig(connectionConfigFactory.createConnectionConfig());
        serverFactory.setUserManager(UserManagerUtil.getUserManager());

        server = serverFactory.createServer();
        try {
            server.start();
        } catch (FtpException e) {
            logger.info(marker, "Servidor não startado: {}", e.getMessage());
            return false;
        }
        return true;
    }

//    private UserManager getUserManager() {
//        PropertiesUserManagerFactory umf = new PropertiesUserManagerFactory();
//        try {
//            new File("myuser.properties").createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        umf.setFile(new File("myuser.properties"));
//        MyPasswordEncryptor passwordEncryptor = new MyPasswordEncryptor();
//        umf.setPasswordEncryptor(passwordEncryptor);
//        return umf.createUserManager();
//    }

    public void stop() {
        if(server != null && !server.isStopped()) {
            server.stop();
            server = null;
        }
    }

}
