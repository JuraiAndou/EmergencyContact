package com.example.emergencycontacts.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emergencycontacts.R;
import com.example.emergencycontacts.objects.User;

public class novoUsuario extends AppCompatActivity {

    //Variavel para teste de primeira vez no campo de texto (Frist Try = fT)
    boolean fTUser,fTNome, fTEmail, fTSenha = true;

    //Iniciando variaveis da tela
    EditText edtNome, edtUser, edtSenha, edtEmail;
    Switch swManterLogado;
    Button btCriar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);
        getSupportActionBar().hide();

        //Atribuindo as informações
        edtUser = (EditText) findViewById(R.id.edt_login2);
        edtNome = (EditText) findViewById(R.id.edt_nome2);
        edtSenha = (EditText) findViewById(R.id.edt_senha2);
        edtEmail = (EditText) findViewById(R.id.edt_email2);

        swManterLogado = (Switch) findViewById(R.id.swt_login);

        btCriar = (Button) findViewById(R.id.bt_criar);


        //Evento pra recuparar os valores do componetes
        btCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inicilizanaod as variaceis para os componentes
                String nomeLocal, userLocal, senhaLocal, emailLocal;
                boolean manterLogado;

                //obtendo valor dos componentes
                nomeLocal = edtNome.getText().toString();
                userLocal = edtUser.getText().toString();
                senhaLocal = edtSenha.getText().toString();
                emailLocal = edtEmail.getText().toString();
                manterLogado = swManterLogado.isChecked();

                //Criando o armazenamento interno do user
                SharedPreferences localUser = getSharedPreferences( "userLocal", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = localUser.edit();

                //armazenando os dados
                editor.putString("nome", nomeLocal);
                editor.putString("user", userLocal);
                editor.putString("email", emailLocal);
                editor.putString("senha", senhaLocal);
                editor.putBoolean("manterLogado", manterLogado);

                //Locando na memoria interna do aparelho
                editor.commit();

                //salva o contato e continua o app para a tela de contatos
                User user =new User(nomeLocal,userLocal,senhaLocal,emailLocal,manterLogado);


                Intent intent = new Intent(novoUsuario.this, listaDeContatos.class);
                intent.putExtra("usuario",user);
                startActivity(intent);

                finish();
            }
        });
    }

}
