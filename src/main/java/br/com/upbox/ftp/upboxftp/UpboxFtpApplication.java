package br.com.upbox.ftp.upboxftp;

import org.apache.ftpserver.ftplet.FtpException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UpboxFtpApplication {

	public static void main(String[] args) throws FtpException {
		SpringApplication.run(UpboxFtpApplication.class, args);
		Runner.run();
	}

}
