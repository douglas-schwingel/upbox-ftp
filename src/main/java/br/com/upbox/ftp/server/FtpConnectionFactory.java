package br.com.upbox.ftp.server;

//
//public class FtpConnectionFactory {
//    private static final Logger logger = LoggerFactory.getLogger(FtpConnectionFactory.class);
//    private static final Marker marker = MarkerFactory.getMarker("ftpConnectionFactory");
//
//    private FTPClient ftpClient;
//
//    public FTPClient conecta(String username, String password, String workDir) throws IOException {
//        ftpClient = new FTPClient();
//        ftpClient.connect("127.0.0.1", 2112);
//        ftpClient.login(username, password);
//        ftpClient.changeWorkingDirectory(workDir);
//        return ftpClient;
//    }
//
//    public void disconecta() throws IOException {
//        if (ftpClient.isConnected()) {
//            ftpClient.disconnect();
//        }
//        System.out.println("Não conectado");
//    }
//}
