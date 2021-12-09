package com.example.emergencycontacts.objects;

import java.io.Serializable;

public class Contato implements Serializable, Comparable {
    //inicilização das variaveis
    String nome, num;

    //BIN METHODS
    //Nome
    public String getNome(){
        return nome;
    }
    public void setNome(String n){
        this.nome = n;
    }

    //Numero
    public String getNum (){
        return num;
    }
    public void setNum(String nb) {
        this.num = nb;
    }

    @Override
    public int compareTo(Object o) {
        Contato c = (Contato) o;
        return this.getNome().compareTo(c.getNome());
    }
}
