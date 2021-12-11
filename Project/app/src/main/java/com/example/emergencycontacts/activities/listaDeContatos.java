package com.example.emergencycontacts.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.emergencycontacts.R;
import com.example.emergencycontacts.objects.Contato;
import com.example.emergencycontacts.objects.User;
import com.example.emergencycontacts.utils.UIEducacionalPermissao;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class listaDeContatos extends AppCompatActivity implements UIEducacionalPermissao.NoticeDialogListener, BottomNavigationView.OnNavigationItemSelectedListener {
    ListView listaCont;
    User user;
    BottomNavigationView bnv;

    String numCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_contatos);

        //carrega o menu inferior
        bnv = findViewById(R.id.btmenu);
        bnv.setOnNavigationItemSelectedListener(this);
        bnv.setSelectedItemId(R.id.anvLigar);


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
                    preencherListViewImagens(user);
                }
            }
        }
    }

    protected void atualizarListaDeContatos(User user){
        SharedPreferences recContatos = getSharedPreferences("contatos", Activity.MODE_PRIVATE);
        int num = recContatos.getInt("numContatos", 0);
        ArrayList<Contato> contatos = new ArrayList<Contato>();

        Contato contato;

        for (int i = 0; i < num; i++) {
            String objSel = recContatos.getString("contato" + i, "");
            if(objSel.compareTo("") != 0){
                try {
                    ByteArrayInputStream bis =
                            new ByteArrayInputStream(objSel.getBytes(StandardCharsets.ISO_8859_1.name()));
                    ObjectInputStream oos = new ObjectInputStream(bis);
                    contato = (Contato) oos.readObject();

                    if (contato != null) {
                        contatos.add(contato);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Log.v("EmContacts","contatos:"+contatos.size());
        user.setContatos(contatos);
    }

    private void preencherListViewImagens(User user) {
        final ArrayList<Contato> contatos = user.getContatos();
        Collections.sort(contatos);
        if (contatos != null) {
            String[] contatosNomes, contatosAbrevs;
            contatosNomes = new String[contatos.size()];
            contatosAbrevs= new String[contatos.size()];
            Contato c;
            for (int j = 0; j < contatos.size(); j++) {
                contatosAbrevs[j] =contatos.get(j).getNome().substring(0, 1);
                contatosNomes[j] =contatos.get(j).getNome();
            }
            ArrayList<Map<String,Object>> itemDataList = new ArrayList<Map<String,Object>>();;

            for(int i =0; i < contatos.size(); i++) {
                Map<String,Object> listItemMap = new HashMap<String,Object>();
                listItemMap.put("imageId", R.drawable.ic_action_ligar_list);
                listItemMap.put("contato", contatosNomes[i]);
                listItemMap.put("abrevs",contatosAbrevs[i]);
                itemDataList.add(listItemMap);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(this,itemDataList,R.layout.list_view_layout_image,
                    new String[]{"imageId","contato","abrevs"},new int[]{R.id.userImage, R.id.userTitle,R.id.userAbrev});

            listaCont.setAdapter(simpleAdapter);


            listaCont.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                    if (checarPermissaoPhone_SMD(contatos.get(i).getNum())) {

                        Uri uri = Uri.parse(contatos.get(i).getNum());
                        //  Intent itLigar = new Intent(Intent.ACTION_DIAL, uri);
                        Intent itLigar = new Intent(Intent.ACTION_CALL, uri);
                        startActivity(itLigar);
                    }


                }
            });

        }
    }

    protected void preencherListView(User user) {

        final ArrayList<Contato> contatos = user.getContatos();

        if (contatos != null) {
            final String[] nomesSP;
            nomesSP = new String[contatos.size()];
            Contato c;
            for (int j = 0; j < contatos.size(); j++) {
                nomesSP[j] = contatos.get(j).getNome();
            }

            ArrayAdapter<String> adaptador;

            adaptador = new ArrayAdapter<String>(this, R.layout.list_view_layout, nomesSP);

            listaCont.setAdapter(adaptador);


            listaCont.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (checarPermissaoPhone_SMD(contatos.get(i).getNum())) {

                        Uri uri = Uri.parse(contatos.get(i).getNum());
                        //   Intent itLigar = new Intent(Intent.ACTION_DIAL, uri);
                        Intent itLigar = new Intent(Intent.ACTION_CALL, uri);
                        startActivity(itLigar);
                    }


                }
            });
        }//fim do IF do tamanho de contatos
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected boolean checarPermissaoPhone_SMD(String numero){

        numCall=numero;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED){

            Log.v ("SMD","Tenho permissão");

            return true;

        } else {

            if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){

                Log.v ("SMD","Primeira Vez");


                String mensagem = "Nossa aplicação precisa acessar o telefone para discagem automática. Uma janela de permissão será solicitada";
                String titulo = "Permissão de acesso a chamadas";
                int codigo =1;
                UIEducacionalPermissao mensagemPermissao = new UIEducacionalPermissao(mensagem,titulo, codigo);

                mensagemPermissao.onAttach ((Context)this);
                mensagemPermissao.show(getSupportFragmentManager(), "primeiravez2");

            }else{
                String mensagem = "Nossa aplicação precisa acessar o telefone para discagem automática. Uma janela de permissão será solicitada";
                String titulo = "Permissão de acesso a chamadas II";
                int codigo =1;

                UIEducacionalPermissao mensagemPermissao = new UIEducacionalPermissao(mensagem,titulo, codigo);
                mensagemPermissao.onAttach ((Context)this);
                mensagemPermissao.show(getSupportFragmentManager(), "segundavez2");
                Log.v ("SMD","Outra Vez");

            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2222:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "VALEU", Toast.LENGTH_LONG).show();
                    Uri uri = Uri.parse(numCall);
                    //   Intent itLigar = new Intent(Intent.ACTION_DIAL, uri);
                    Intent itLigar = new Intent(Intent.ACTION_CALL, uri);
                    startActivity(itLigar);

                } else {
                    Toast.makeText(this, "SEU FELA!", Toast.LENGTH_LONG).show();

                    String mensagem = "Seu aplicativo pode ligar diretamente, mas sem permissão não funciona. Se você marcou não perguntar mais, você deve ir na tela de configurações para mudar a instalação ou reinstalar o aplicativo  ";
                    String titulo = "Porque precisamos telefonar?";
                    UIEducacionalPermissao mensagemPermisso = new UIEducacionalPermissao(mensagem, titulo, 2);
                    mensagemPermisso.onAttach((Context) this);
                    mensagemPermisso.show(getSupportFragmentManager(), "segundavez");
                }
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Checagem de o Item selecionado é o do perfil
        if (item.getItemId() == R.id.anvPerfil) {
            //Abertura da Tela MudarDadosUsario
            Intent intent = new Intent(this, modificaUsuario.class);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Caso seja um Voltar ou Sucesso selecionar o item Ligar

        if (requestCode == 1111) {//Retorno de Mudar Perfil
            bnv.setSelectedItemId(R.id.anvLigar);
            user=atualizarUser();
            setTitle("Contatos de Emergência de "+user.getNome());
            atualizarListaDeContatos(user);
            // preencherListViewImagens(user);
            preencherListView(user); //Montagem do ListView
        }

        if (requestCode == 1112) {//Retorno de Mudar Contatos
            bnv.setSelectedItemId(R.id.anvLigar);
            atualizarListaDeContatos(user);
            //preencherListViewImagens(user);
            preencherListView(user); //Montagem do ListView
        }



    }

    private User atualizarUser() {
        User user = null;
        SharedPreferences temUser= getSharedPreferences("usuarioPadrao", Activity.MODE_PRIVATE);
        String loginSalvo = temUser.getString("login","");
        String senhaSalva = temUser.getString("senha","");
        String nomeSalvo = temUser.getString("nome","");
        String emailSalvo = temUser.getString("email","");
        boolean manterLogado=temUser.getBoolean("manterLogado",false);

        user=new User(nomeSalvo,loginSalvo,senhaSalva,emailSalvo,manterLogado);
        return user;
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onDialogPositiveClick(int codigo) {

        if (codigo==1){
            String[] permissions ={Manifest.permission.CALL_PHONE};
            requestPermissions(permissions, 2222);

        }
    }
}
