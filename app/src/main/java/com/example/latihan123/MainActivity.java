package com.example.latihan123;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText userid, password;
    private Button login;
    private final String TAG= "login";
    private SessionManager session;


    private String urllogin = "https://tugasakhir-ba915.firebaseio.com/login.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // membuat tulisan toolbar diatas
        setTitle("Control and Monitoring");
        getSupportActionBar().setSubtitle("Smart Office");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.iconhome);
        initlayout();
        session = new SessionManager(this);

        // kalau dia udah login, dia langsung menuju ke halaman menu.
        if(session.isLoggedIn()){

            Intent intent = new Intent(MainActivity.this,Menu.class);
            startActivity(intent);
        }
    }

    private void initlayout() {
        userid = (EditText)findViewById(R.id.userid);
        password = (EditText)findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // untuk nextpage
                /*Intent intent=new Intent(MainActivity.this,Home.class); // masuk ke activity Home
                startActivity(intent);*/

                Log.d(TAG, "mulai");
                String isiuserid=userid.getText().toString();
                String isipassword=password.getText().toString();
                login(isiuserid, isipassword);
            }
        });
    }
    //STARTNYA BIKIN WEBSERVICE
    private void login(final String username, final String password){
        new ApiVolley(MainActivity.this, new JSONObject(), "GET", ServerList.urlLogin+"?username=" + username + "&password=" + password, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    Log.d(TAG, "done");
                    JSONObject respon= new JSONObject(result);
                    Toast.makeText(MainActivity.this,respon.getString("message"), Toast.LENGTH_LONG).show();

                    if(respon.getString("status").equals("200")){


                        // kao statusnya 200 nanti yg dilakukan --> dia menyimpan username ke session.
                        session.createLoginSession(username);
                        // Fungsinya untuk ganti halaman.
                        Intent intent=new Intent(MainActivity.this,Menu.class);
                        intent.putExtra("userid", username);
                        intent.putExtra("password", password);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String result) {

            }
        });
    }
}
