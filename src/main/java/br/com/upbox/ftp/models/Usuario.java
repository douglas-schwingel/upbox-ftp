package br.com.upbox.ftp.models;

public class Usuario {

    private String username;
    private String senha;

    public String getUsername() {
        return username;
    }


    public String getSenha() {
        return senha;
    }

    public Usuario(String username, String senha) {
        this.username = username;
        this.senha = senha;
    }
}
