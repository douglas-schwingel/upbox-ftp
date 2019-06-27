package br.com.upbox.ftp.upboxftp;

import br.com.upbox.ftp.server.InMemoryUserManager;
import org.apache.ftpserver.ftplet.FtpException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

//@SpringBootApplication
//@EnableWebMvc
@ComponentScan(basePackageClasses = {InMemoryUserManager.class})
public class UpboxFtpApplication {

	public static void main(String[] args) throws FtpException, IOException {
		SpringApplication.run(UpboxFtpApplication.class, args);
		Runner.run();
	}

}
