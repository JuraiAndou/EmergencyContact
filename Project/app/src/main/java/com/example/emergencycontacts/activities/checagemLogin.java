package com.example.emergencycontacts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.emergencycontacts.R;

public class checagemLogin extends AppCompatActivity {
    //Iniciação das variaveis
    EditText edtUser, edtSenha;
    Button btEntrar, btCadas;

    //Variavies para verificação das caixas de texto
    Boolean fTUser, fTSenha = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checagem_login);

        //User user = new User()

       // Intent intent = new Intent(this, ListaDeContatos.class);
       // intent.putExtra( "user", user);
       // startActivity(intent);



    }
}