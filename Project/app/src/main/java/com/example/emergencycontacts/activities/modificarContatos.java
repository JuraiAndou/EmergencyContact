package com.example.emergencycontacts.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emergencycontacts.R;
import com.example.emergencycontacts.objects.Contato;
import com.example.emergencycontacts.objects.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

public class modificarContatos extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
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

        try {
            ByteArrayOutputStream dt = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(dt);
            dt = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(dt);
            oos.writeObject(c);
            String contatoSerializado= dt.toString(StandardCharsets.ISO_8859_1.name());
            editorContato.putString("contato"+(num+1), contatoSerializado);
            editorContato.putInt("numContatos",num+1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("EmContacts", "Headshoot ~ Activity Lista de Contatos");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.v("EmContacts", "Kill Confirmed ~ Activity Lista de Contatos");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //add depois de ter add o menu
        return false;
    }
}

