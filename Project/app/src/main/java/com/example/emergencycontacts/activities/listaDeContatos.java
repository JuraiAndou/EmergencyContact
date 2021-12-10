package com.example.emergencycontacts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emergencycontacts.R;
import com.example.emergencycontacts.objects.User;

public class listaDeContatos extends AppCompatActivity {
    ListView listaCont;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_contatos);

        //Obtendo componente da lista da tela
        listaCont = (ListView) findViewById(R.id.list_contatosDoApp);

        //Recebendo o usuario da intent
        Intent intAnte = this.getIntent();
        if (intAnte != null){//vendo se houve um intent anterior
            Bundle date = intAnte.getExtras();
            if (date != null){//testando se o dado obtido não é nulo e obtendo usuario
                user = (User) date.getSerializable("usuario");
                if (user != null){//testando se o usuario não é nulo
                    setTitle("Contatos emergênciais de " + user.getNome());
                }
            }
        }
    }
}
