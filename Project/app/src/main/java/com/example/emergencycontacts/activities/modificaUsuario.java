package com.example.emergencycontacts.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emergencycontacts.R;
import com.example.emergencycontacts.objects.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class modificaUsuario extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    EditText edtUser, edtNome, edtSenha, edtEmail;

    Switch manterLogado;
    Button btMod;
    BottomNavigationView bnv;

    User user;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);
        getSupportActionBar().hide();

        bnv = findViewById(R.id.btmenu);
        bnv.setOnNavigationItemSelectedListener(this);
        bnv.setSelectedItemId(R.id.anvPerfil);

        edtNome = (EditText) findViewById(R.id.edt_nome3);
        edtUser = (EditText) findViewById(R.id.edt_login3);
        edtSenha = (EditText) findViewById(R.id.edt_senha3);
        edtEmail = (EditText) findViewById(R.id.edt_email3);
        manterLogado = (Switch) findViewById(R.id.swt_login);
        btMod = (Button) findViewById(R.id.bt_criar);



        //Recebendo o usuario da intent
        Intent intAnte = this.getIntent();
        if (intAnte != null) {//vendo se houve um intent anterior
            Bundle date = intAnte.getExtras();
            if (date != null) {//testando se o dado obtido não é nulo e obtendo usuario
                user = (User) date.getSerializable("usuario");


            }
        }

        //Colocando as info baseado no armazenamento interno
        if (user != null){
            edtNome.setText((user.getNome()));
            edtSenha.setText(user.getSenha());
            edtUser.setText(user.getUser());
            edtEmail.setText(user.getEmail());
            manterLogado.setChecked(user.isManterlogado());

        }

        //Moficiando valores do objeto Usuario
        btMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setNome(edtNome.getText().toString());
                user.setSenha(edtSenha.getText().toString());
                user.setUser(edtUser.getText().toString());
                user.setEmail(edtEmail.getText().toString());
                user.setManterlogado(manterLogado.isChecked());
                salvarMod(user);
            }
        });
    }

    //Craindo metodo para salvar usuario
    public void salvarMod (User u){
        SharedPreferences localUser =getSharedPreferences("userLocal", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = localUser.edit();

        //eviando as informação para o armazenamento
        editor.putString("nome", u.getNome());
        editor.putString("senha", u.getSenha());
        editor.putString("email", u.getEmail());
        editor.putString("user", u.getUser());
        editor.putBoolean("manterLogado", u.isManterlogado());

        editor.commit();
        Toast.makeText(modificaUsuario.this,"Modificações Salvas",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Checagem de o Item selecionado é o do perfil
        if (item.getItemId() == R.id.anvLigar) {
            //Abertura da Tela lista de contatos
            Intent intent = new Intent(this, listaDeContatos.class);
            intent.putExtra("usuario", user);
            startActivityForResult(intent, 1111);

        }
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
