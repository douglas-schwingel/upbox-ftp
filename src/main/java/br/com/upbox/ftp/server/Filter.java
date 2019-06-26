package br.com.upbox.ftp.server;

import org.apache.ftpserver.listener.nio.FtpLoggingFilter;

public class Filter extends FtpLoggingFilter {

    public Filter(String name) {
        super(name);
    }
}
