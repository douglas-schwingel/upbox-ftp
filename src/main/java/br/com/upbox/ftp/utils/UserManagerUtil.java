package br.com.upbox.ftp.utils;

import br.com.upbox.ftp.upboxftp.Runner;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
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
    private static final String USER = "USER";
    private static final String PASS = "PASS";
    private static String nome;
    private static boolean naoExiste;

    private UserManagerUtil() {

    }

    public static UserManager getUserManager() {
        PropertiesUserManagerFactory umf = new PropertiesUserManagerFactory();
        try {
            new File("myuser.properties").createNewFile();
        } catch (IOException e) {
            logger.error(marker, "Erro ao criar aquivo myuser.properties: {}", e.getMessage());
        }
        umf.setFile(new File("myuser.properties"));
        umf.setPasswordEncryptor(new ClearTextPasswordEncryptor());
        return umf.createUserManager();
    }

    private static void criaUsuario(String nome, String senha) throws FtpException {
        Map<String, String> map = salva(nome, senha);
        logger.info(marker, map.get("mensagem"), nome);
        Runner.restart();
    }

    private static Map<String, String> salva(String nome, String senha) {
        UserManager userManager = getUserManager();
        BaseUser user = new BaseUser();
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);
        user.setName(nome);
        user.setPassword(senha);
        String pastaUsuario = getUserDir(nome);
        user.setHomeDirectory(pastaUsuario);
        try {
            logger.info(marker, "Criando diretorio {}", pastaUsuario);
            Runtime.getRuntime().exec("mkdir " + pastaUsuario);
        } catch (IOException e) {
            logger.error(marker, "Erro ao salvar usuario {}", nome);
        }
        Map<String, String> map = new HashMap<>();
        String msg = "";
        try {
            if (!userManager.doesExist(nome)) {
                userManager.save(user);
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

    private static boolean verificaSeNaoExiste(String nome) {
        User userByName = null;
        try {
            userByName = getUserManager().getUserByName(nome);
        } catch (FtpException e) {
            logger.error(marker, "Erro em buscar por nome: {}", e.getMessage());
        }
        return userByName == null;
    }

    private static String getUserDir(String nome) {
        return System.getProperty("user.dir") + "/upbox-files/" + nome;
    }

    public static void deletaUsuario(FtpRequest request) throws FtpException {
            String argument = request.getArgument();
            String[] split = argument.split(".delete");
            logger.info(marker, "Entrando no delete usuario: {}", split[0]);
            UserManagerUtil.getUserManager().delete(split[0]);
    }

    public static boolean criaNovoUsuarioSeNaoExistir(FtpRequest request, String command) {
        if (command.contains(USER)){
            logger.info(marker, "Entrou no if do {}", USER);
            nome = request.getArgument();
            naoExiste = UserManagerUtil.verificaSeNaoExiste(nome);
            return true;
        }
        if (command.contains(PASS) && naoExiste) {
            logger.info(marker, "Entrou no if do {}", PASS);
            String password = request.getArgument();
            try {
                UserManagerUtil.criaUsuario(nome, password);
            } catch (FtpException e) {
                logger.error(marker, "Erro ao criar usuario {}", nome);
            }
            naoExiste = false;
        }
        return false;
    }
}
