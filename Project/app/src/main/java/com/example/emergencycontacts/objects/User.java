package com.example.emergencycontacts.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    //inicialização das variaveis
    String nome, senha, email, user;
    boolean manterlogado = false;
    ArrayList<Contato> contatos;

    //constructor
    public User (String n, String u, String s, String e, boolean manter){
        this.nome = n;
        this.senha = s;
        this.user = u;
        this.email = e;
        this.manterlogado = manter;
        this.contatos = new ArrayList<Contato>();
    }

    //BIN METHODS
    public User(){
        this.contatos = new ArrayList<Contato>();
    }
    //nome
    public String getNome(){
        return nome;
    }
    public void setNome(String n){
        this.nome = n;
    }

    //Senha
    public String getSenha(){
        return senha;
    }
    public void setSenha(String s){
        this.senha = s;
    }

    //Login
    public String getUser(){
        return user;
    }
    public void setUser(String u){
        this.user = u;
    }

    //Email
    public String getEmail(){
        return email;
    }
    public void setEmail(String e){
        this.email= e;
    }

    //ManterLogin
    public boolean isManterlogado(){
        return manterlogado;
    }
    public void setManterlogado(boolean manter){
        this.manterlogado = manter;
    }

    //Contatos
    public ArrayList<Contato> getContatos (){
        return contatos;
    }

    public void setContatos (ArrayList<Contato> c){
        this.contatos = c;
    }
}

