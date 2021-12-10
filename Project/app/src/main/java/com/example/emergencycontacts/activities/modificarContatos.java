package com.example.emergencycontacts.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emergencycontacts.R;
import com.example.emergencycontacts.objects.Contato;
import com.example.emergencycontacts.objects.User;

public class modificarContatos extends AppCompatActivity {
    boolean fTuser = true;
    EditText edtPesquisa;
    ListView listaContCelular;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_contatos);

        //Iniciando componetes
        edtPesquisa = (EditText) findViewById(R.id.edt_pesquisar);
        listaContCelular = (ListView) findViewById(R.id.list_contatoDoCelular);

        //Recebendo o usuario da intent
        Intent intAnte = this.getIntent();
        if (intAnte != null){//vendo se houve um intent anterior
            Bundle date = intAnte.getExtras();
            if (date != null){//testando se o dado obtido não é nulo e obtendo usuario
                user = (User) date.getSerializable("usuario");
                setTitle("Adiconar contatos emergênciais");
            }
        }
    }

    public void salvarContato (Contato c){
        //Craindo armazenamento interno para o contato
        SharedPreferences userContato = getSharedPreferences("contatos", Activity.MODE_PRIVATE);
        int num = userContato.getInt("numContatos", 0);
        SharedPreferences.Editor editorContato = userContato.edit();



    }
}

