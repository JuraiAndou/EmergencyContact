package com.example.emergencycontacts.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.emergencycontacts.R;
import com.example.emergencycontacts.objects.User;

public class checagemLogin extends AppCompatActivity {
    //Iniciação das variaveis da tela
    EditText edtUser, edtSenha;
    Button btEntrar, btCadas;

    //Variavies para verificação das caixas de texto
    boolean fTUser, fTSenha = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checagem_login);

        //User user = new User()

       // Intent intent = new Intent(this, ListaDeContatos.class);
       // intent.putExtra( "user", user);
       // startActivity(intent);

        //Configura o btn cadastro
        btCadas = findViewById(R.id.bt_cadastro);
        btCadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActNovoUsuario();
            }
        });


    }

    public void openActNovoUsuario(){
        Intent intent = new Intent(this, novoUsuario.class);
        startActivity(intent);
    }

    //metodo para montar o objeto do usuario baseado nos dados armazenado
    private User carregarObUser(){
        User user= null;

        //Carregando dados salvo no armazenamento
        SharedPreferences userSaved = getSharedPreferences("userLocal", Activity.MODE_PRIVATE);
        String nomeSalvo = userSaved.getString("nome", "");
        String senhaSalva = userSaved.getString("senha", "");
        String emailSalvo = userSaved.getString("email", "");
        String userSalvo = userSaved.getString("user", "");
        boolean manterLogado = userSaved.getBoolean("ManterLogado", false);

        //criando objeto do usuario
        user = new User (nomeSalvo,senhaSalva, emailSalvo, userSalvo, manterLogado);

        return user;
    }

    private boolean isManterLogado(){
        //Pega somente o dado para saber se o usario deseja ficar logado
        SharedPreferences userSaved = getSharedPreferences("userLocal", Activity.MODE_PRIVATE);
        boolean manterLogado = userSaved.getBoolean("manterLogado", false);

        return manterLogado;
    }
}