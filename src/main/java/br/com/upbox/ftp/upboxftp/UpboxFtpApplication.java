package br.com.upbox.ftp.upboxftp;

import br.com.upbox.ftp.controllers.FtpController;
import br.com.upbox.ftp.ftplet.MyFtplet;
import br.com.upbox.ftp.models.UsuarioDTO;
import br.com.upbox.ftp.server.InMemoryUserManager;
import org.apache.ftpserver.ftplet.FtpException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackageClasses = {FtpController.class, InMemoryUserManager.class, MyFtplet.class})
public class UpboxFtpApplication {

	public static void main(String[] args) throws FtpException, IOException {
		Runner.run();
		SpringApplication.run(UpboxFtpApplication.class, args);
	}

}
