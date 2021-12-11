package com.example.emergencycontacts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        getSupportActionBar().hide();

        //Verificação para saber se existe usuario pra se manter logado
        if (isManterLogado()) {
            User user = carregarObUser();

            Intent init = new Intent(checagemLogin.this, listaDeContatos.class);
            init.putExtra("usuario", user);
            startActivity(init);
            finish();

        }else{
            //obtendo os componesntes
            edtUser = (EditText) findViewById(R.id.edt_login1);
            edtSenha = (EditText) findViewById(R.id.edt_senha1);
            btEntrar = (Button) findViewById(R.id.bt_entrar);
            btCadas = (Button) findViewById(R.id.bt_cadastro);

            //Logando
            btEntrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Obtendo valores do amazenamento interno
                    SharedPreferences userSaved = getSharedPreferences("userLocal", Activity.MODE_PRIVATE);
                    String loginSalvo = userSaved.getString("user",null);
                    String senhaSalva = userSaved.getString("senha",null);

                    //Verificando se os valores obtidos não sao nulos
                    if ((loginSalvo != null) && (senhaSalva != null)){
                        //Obtendo as strings dos componentes
                        String loginAtual = edtUser.getText().toString();
                        String senhaAtual = edtSenha.getText().toString();

                        //Comparação os valores do componentes com os internos
                        if ((loginSalvo.compareTo(loginAtual) == 0) && (senhaSalva.compareTo(senhaAtual) == 0)){
                            User user = carregarObUser();

                            //Criar lista de contatos

                            //Abri o app na tela de lista de contatos
                            Intent init = new Intent(checagemLogin.this, listaDeContatos.class);
                            init.putExtra("usuario", user);
                            startActivity(init);
                        }else {
                            //Fala quando o valor não é igual ao armazenado
                            Toast.makeText(checagemLogin.this, "Login e senha incorretos", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        //Fala quando o valor é nulo
                        Toast.makeText(checagemLogin.this, "Não há usuario salvo", Toast.LENGTH_LONG).show();
                    }
                }
            });
            //Configura o bt cadastro
            btCadas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(checagemLogin.this, novoUsuario.class);
                    startActivity(intent);
                }
            });
        }
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