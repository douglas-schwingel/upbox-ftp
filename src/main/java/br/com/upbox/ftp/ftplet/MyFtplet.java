//package br.com.upbox.ftp.ftplet;
//
//import br.com.upbox.ftp.utils.UserManagerUtil;
//import org.apache.ftpserver.ftplet.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.Marker;
//import org.slf4j.MarkerFactory;
//
//import java.io.IOException;
//
//public class MyFtplet implements Ftplet {
//    private static final Logger logger = LoggerFactory.getLogger(MyFtplet.class);
//    private static final Marker marker = MarkerFactory.getMarker("my-ftplet");
//
//    @Override
//    public void init(FtpletContext ftpletContext) throws FtpException {
//        logger.info(marker, "Criando o {}", MyFtplet.class.getName());
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//    @Override
//    public FtpletResult beforeCommand(FtpSession session, FtpRequest request) throws FtpException, IOException {
//        return null;
//    }
//
//    @Override
//    public FtpletResult afterCommand(FtpSession session, FtpRequest request, FtpReply reply) throws FtpException, IOException {
//        return null;
//    }
//
//    @Override
//    public FtpletResult onConnect(FtpSession session) throws FtpException, IOException {
//        UserManagerUtil.criaUsuario(session);
//        return null;
//    }
//
//    @Override
//    public FtpletResult onDisconnect(FtpSession session) throws FtpException, IOException {
//        return null;
//    }
//
//
////    @Override
////    public FtpletResult onConnect(FtpSession session) throws FtpException, IOException {
////        User user = session.getUser();
////        if (!userManager.doesExist(user.getName())) {
////            logger.info(marker, "Usuario {} não existe. Criando...", user.getName());
////            userManager.save(user);
////        }
////        logger.info(marker, "Entrando no connect com usuario {}", user.getName());
////        return null;
////    }
//
////    @Override
////    public FtpletResult onUploadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
////        User user = session.getUser();
////        String currentDir = session.getFileSystemView().getWorkingDirectory().getAbsolutePath();
////        String fileName = request.getArgument();
////
////        File file = new File(user.getHomeDirectory() + currentDir + fileName);
////        return super.onUploadStart(session, request);
////    }
//}
