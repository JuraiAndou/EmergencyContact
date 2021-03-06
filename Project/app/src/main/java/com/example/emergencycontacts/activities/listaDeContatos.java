package com.example.emergencycontacts.activities;

import androidx.annotation.NonNull;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencycontacts.R;
import com.example.emergencycontacts.objects.Contato;
import com.example.emergencycontacts.objects.User;
import com.example.emergencycontacts.utils.UIEducacionalPermissao;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class listaDeContatos extends AppCompatActivity implements UIEducacionalPermissao.NoticeDialogListener, BottomNavigationView.OnNavigationItemSelectedListener {

    ListView lv;
    BottomNavigationView bnv;
    User user;

    String numeroCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_contatos);

        bnv = findViewById(R.id.btmenu);
        bnv.setOnNavigationItemSelectedListener(this);
        bnv.setSelectedItemId(R.id.anvLigar);

        lv = findViewById(R.id.listView1);

        //Dados da Intent Anterior
        Intent quemChamou = this.getIntent();
        if (quemChamou != null) {
            Bundle params = quemChamou.getExtras();
            if (params != null) {
                //Recuperando o Usuario
                user = (User) params.getSerializable("usuario");
                if (user != null) {
                    setTitle("Contatos de Emerg??ncia de "+user.getNome());
                    atualizarListaDeContatos(user);
                    preencherListViewImagens(user);
                }
            }
        }

    }

    protected void atualizarListaDeContatos(User user){
        SharedPreferences recuperarContatos = getSharedPreferences("contatos", Activity.MODE_PRIVATE);

        int num = recuperarContatos.getInt("numContatos", 0);
        ArrayList<Contato> contatos = new ArrayList<Contato>();

        Contato contato;


        for (int i = 1; i <= num; i++) {
            String objSel = recuperarContatos.getString("contato" + i, "");
            if (objSel.compareTo("") != 0) {
                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(objSel.getBytes(StandardCharsets.ISO_8859_1.name()));
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
        user.setContatos(contatos);
    }
    protected  void preencherListViewImagens(User user){

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
            SimpleAdapter simpleAdapter = new SimpleAdapter(this,itemDataList,R.layout.list_view_layout_image, new String[]{"imageId","contato","abrevs"},new int[]{R.id.userImage, R.id.userTitle,R.id.userAbrev});
            lv.setAdapter(simpleAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (checarPermissaoPhone_SMD(contatos.get(i).getNum())) {
                        Uri uri = Uri.parse(contatos.get(i).getNum());
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
            lv.setAdapter(adaptador);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (checarPermissaoPhone_SMD(contatos.get(i).getNum())) {
                        Uri uri = Uri.parse(contatos.get(i).getNum());
                        Intent itLigar = new Intent(Intent.ACTION_CALL, uri);
                        startActivity(itLigar);
                    }
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected boolean checarPermissaoPhone_SMD(String numero){

        numeroCall=numero;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            return true;

        } else {

            if ( shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                String mensagem = "A aplica????o precisa acessar o telefone para discagem autom??tica. Uma janela de permiss??o ser?? solicitada";
                String titulo = "Permiss??o de acesso a chamadas";
                int codigo =1;
                UIEducacionalPermissao mensagemPermissao = new UIEducacionalPermissao(mensagem,titulo, codigo);
                mensagemPermissao.onAttach ((Context)this);
                mensagemPermissao.show(getSupportFragmentManager(), "primeiravez2");
            }else{
                String mensagem = "A aplica????o precisa acessar o telefone para discagem autom??tica. Uma janela de permiss??o ser?? solicitada";
                String titulo = "Permiss??o de acesso a chamadas II";
                int codigo =1;
                UIEducacionalPermissao mensagemPermissao = new UIEducacionalPermissao(mensagem,titulo, codigo);
                mensagemPermissao.onAttach ((Context)this);
                mensagemPermissao.show(getSupportFragmentManager(), "segundavez2");
            }
        }
        return false;
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2222:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Uri uri = Uri.parse(numeroCall);
                    Intent itLigar = new Intent(Intent.ACTION_CALL, uri);
                    startActivity(itLigar);
                } else {
                    String mensagem = "Seu aplicativo pode ligar diretamente, mas sem permiss??o n??o funciona. Se voc?? marcou n??o perguntar mais, voc?? deve ir na tela de configura????es para mudar a instala????o ou reinstalar o aplicativo  ";
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
        // Bot??es de navega????o
        if (item.getItemId() == R.id.anvPerfil) {
            //Abertura da Tela de alterar perfil
            Intent intent = new Intent(this, modificaUsuario.class);
            intent.putExtra("usuario", user);
            startActivityForResult(intent, 1111);
        }
        if (item.getItemId() == R.id.anvMudar) {
            //Abertura da Tela de modificar
            Intent intent = new Intent(this, modificarContatos.class);
            intent.putExtra("usuario", user);
            startActivityForResult(intent, 1112);
        }
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Bot??o de Mudar Perfil
        if (requestCode == 1111) {
            bnv.setSelectedItemId(R.id.anvLigar);
            user=atualizarUser();
            setTitle("Contatos Emerg??nciais de "+user.getNome());
            atualizarListaDeContatos(user);
            preencherListView(user);
        }
        //Bot??o de Mudar Contatos
        if (requestCode == 1112) {
            bnv.setSelectedItemId(R.id.anvLigar);
            atualizarListaDeContatos(user);
            preencherListView(user);
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
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onDialogPositiveClick(int codigo) {
        if (codigo==1){
            String[] permissions ={Manifest.permission.CALL_PHONE};
            requestPermissions(permissions, 2222);
        }
    }
}


