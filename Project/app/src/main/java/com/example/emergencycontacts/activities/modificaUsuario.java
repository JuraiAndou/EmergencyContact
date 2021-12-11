package com.example.emergencycontacts.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emergencycontacts.R;

public class modificaUsuario extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);
        getSupportActionBar().hide();
    }
}
