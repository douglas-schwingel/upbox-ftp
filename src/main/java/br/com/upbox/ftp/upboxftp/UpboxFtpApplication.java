package br.com.upbox.ftp.upboxftp;

import org.apache.ftpserver.ftplet.FtpException;
import org.springframework.boot.SpringApplication;

import java.io.IOException;

//@SpringBootApplication
//@EnableWebMvc
public class UpboxFtpApplication {

	public static void main(String[] args) throws FtpException, IOException {
		SpringApplication.run(UpboxFtpApplication.class, args);
		Runner.run();
	}

}
