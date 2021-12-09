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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);

        //Atribuindo as informações
        edtUser = (EditText) findViewById(R.id.edt_login);
        edtNome = (EditText) findViewById(R.id.edt_nome);
        edtSenha = (EditText) findViewById(R.id.edt_senha);
        edtEmail = (EditText) findViewById(R.id.edt_email);

        swManterLogado = (Switch) findViewById(R.id.swt_login);

        btCriar = (Button) findViewById(R.id.bt_criar);

        //Eventos de limpar componentes
        //Usuario
        edtUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (fTUser){
                    fTUser=false;
                    edtUser.setText("");
                }
                return false;
            }
        });

        //Nome
        edtNome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (fTNome){
                    fTNome=false;
                    edtNome.setText("");
                }
                return false;
            }
        });

        //Senha
        edtSenha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (fTSenha){
                    fTSenha=false;
                    edtSenha.setText("");
                }
                return false;
            }
        });

        //Email
        edtEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (fTEmail){
                    fTEmail=false;
                    edtEmail.setText("");
                }
                return false;
            }
        });

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


                //Ainda n fiz essa atividade entao ta comentada
               // Intent intent=new Intent(NovoUsuario_Activity.this, AlterarContatos_Activity.class);
                //intent.putExtra("usuario",user);
               // startActivity(intent);


                finish();
            }
        });
    }

}
