package pt.europeia.bibloteca.models;

import java.io.Serializable;

/**
 * Created by bruno on 13/10/2016.
 */

public class User implements Serializable {
    private int id;
    private String username;
    private String pass;
    private  String nome;

    public User(int id, String username, String pass, String nome) {
        this.id = id;
        this.username = username;
        this.pass = pass;
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
