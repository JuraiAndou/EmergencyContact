package com.example.emergencycontacts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emergencycontacts.R;
import com.example.emergencycontacts.objects.User;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class listaDeContatos extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Checagem de o Item selecionado é o do perfil
        /*if (item.getItemId() == R.id.anvPerfil) {
            //Abertura da Tela MudarDadosUsario
            Intent intent = new Intent(this, PerfilUsuario_Activity.class);
            intent.putExtra("usuario", user);
            startActivityForResult(intent, 1111);

        }*/
        // Checagem de o Item selecionado é o do perfil
        if (item.getItemId() == R.id.anvMudar) {
            //Abertura da Tela Mudar COntatos
            Intent intent = new Intent(this, modificarContatos.class);
            intent.putExtra("usuario", user);
            startActivityForResult(intent, 1112);

        }
        return true;
    }
}
