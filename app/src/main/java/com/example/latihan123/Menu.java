package com.example.latihan123;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Button satu, dua, tiga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Main Menu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.iconhome);


        satu = findViewById(R.id.satu);
        satu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(Menu.this, Home.class);
                startActivity(pindah);
            }}
        );


        dua = findViewById(R.id.dua);
        dua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(Menu.this, HistoryUpdate.class);
                startActivity(pindah);
            }}
        );

        tiga = findViewById(R.id.tiga);
        tiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(Menu.this, MainActivity.class);
                startActivity(pindah);
            }}
        );



    }
}
