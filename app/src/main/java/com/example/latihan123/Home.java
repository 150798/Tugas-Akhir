package com.example.latihan123;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.latihan123.model.ResponseModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

// ini nggak masuk di on create karna ini nilai awal, supaya bisa diakses di function mana aja
public class Home extends AppCompatActivity {
    private FirebaseDatabase mDatabase;
    private final String TAG="Home1";
    private String urlMaster = ServerList.urlMaster;
    private String urlSimpanStatusSaklar = ServerList.urlSimpanStatusSaklar;
    private String valueS1="0";
    private String valueS2="0";
    private String valueS3="0";
    private String valueS4="0";
    private String valueS5="0";
    private String valueS6="0";
    private String valueS7="0";
    private Button btnperangkatA, btnperangkatB,btnperangkatC, btnperangkatD, btnperangkatE, btnallon, btnalloff;
    private SessionManager session;
    Timer timer = new Timer();
    private boolean isloading=false;
    FirebaseDatabase database;
    DatabaseReference responseperangkat;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // layout yang digunakan dari aktivotas ini.
        mDatabase = FirebaseDatabase.getInstance(); // ambil koneksi database firebasenya
        session=new SessionManager(this); // manggil session

        setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.iconhome);

        //mengambil data status saklar awal
        //getStatusSaklar(); //function

        //inisialisasi tombol yg ada di view
        // ini harus ada dalam in create
        btnperangkatA = (Button) findViewById(R.id.perangkatA);
        btnperangkatB = (Button) findViewById(R.id.perangkatB);
        btnperangkatC = (Button) findViewById(R.id.perangkatC);
        btnperangkatD = (Button) findViewById(R.id.perangkatD);
        btnperangkatE = (Button) findViewById(R.id.perangkatE);
        btnallon = (Button) findViewById(R.id.allon);
        btnalloff = (Button) findViewById(R.id.alloff);

        // ketika salah 1 tombol di klik dan sekalian ngubah di database
        btnperangkatA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jika saklar bernilai 1 maka update ke nilai 0, begitu pula sebaliknya
                if(valueS1.equals("0")){
                    valueS1="1";

                }
                else {
                    valueS1="0";
                }
                updateStatusSaklar(false );
                SimpanStatusSaklar("S1", valueS1);
            }
        });

        btnperangkatB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jika saklar bernilai 1 maka update ke nilai 0, begitu pula sebaliknya
                if(valueS2.equals("0")){
                    valueS2="1";

                }
                else {
                    valueS2="0";
                }
                updateStatusSaklar(false);
                SimpanStatusSaklar("S2", valueS2);

            }
        });

        btnperangkatC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jika saklar bernilai 1 maka update ke nilai 0, begitu pula sebaliknya
                if(valueS3.equals("0")){
                    valueS3="1";

                }
                else {
                    valueS3="0";
                }
                updateStatusSaklar(false);
                SimpanStatusSaklar("S3", valueS3);

            }
        });

        btnperangkatD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jika saklar bernilai 1 maka update ke nilai 0, begitu pula sebaliknya
                if(valueS4.equals("0")){
                    valueS4="1";

                }
                else {
                    valueS4="0";
                }
                updateStatusSaklar(false);
                SimpanStatusSaklar("S4", valueS4);

            }
        });

        btnperangkatE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jika saklar bernilai 1 maka update ke nilai 0, begitu pula sebaliknya
                if(valueS5.equals("0")){
                    valueS5="1";

                }
                else {
                    valueS5="0";
                }
                updateStatusSaklar(false);
                SimpanStatusSaklar("S5", valueS5);

            }
        });

        btnallon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jika saklar bernilai 1 maka update ke nilai 0, begitu pula sebaliknya
                valueS6 = "1";
                updateStatusSaklar(true);
                SimpanStatusSaklar("S6", valueS6);

            }
        });

        btnalloff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jika saklar bernilai 1 maka update ke nilai 0, begitu pula sebaliknya
                valueS6 = "0";
                updateStatusSaklar(true);
                SimpanStatusSaklar("S6", valueS6);

            }
        });
        //timer untuk mengambil data terbaru dari status skalar
        //isloading=false;
        //TimerTask updateStatusSaklarTask = new UpdateStatusSaklarTask();
        //timer.scheduleAtFixedRate(updateStatusSaklarTask, 0, 1000);

        //untuk manggil database dari firebase
        database = FirebaseDatabase.getInstance();
        responseperangkat = database.getReference("data2");
        Log.d(TAG, "onCreate: "+responseperangkat.toString());

        responseperangkat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ResponseModel responseModel = dataSnapshot.getValue(ResponseModel.class);
                Log.d(TAG, "onDataChange: "+responseModel.getA1());
                valueS1=responseModel.getA1();
                valueS2=responseModel.getA2();
                valueS3=responseModel.getA3();
                valueS4=responseModel.getA4();
                valueS5=responseModel.getA5();
                valueS6=responseModel.getA6();
                valueS7=responseModel.getA7();


                //untuk nyetting warnanya kalo di klik
                if (valueS1.equals("1")) {
                    btnperangkatA.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaranaktif));
                }
                else {
                    btnperangkatA.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaran));
                }

                //untuk nyetting warnanya kalo di klik
                if (valueS2.equals("1")) {
                    btnperangkatB.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaranaktif));
                }
                else {
                    btnperangkatB.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaran));
                }

                //untuk nyetting warnanya kalo di klik
                if (valueS3.equals("1")) {
                    btnperangkatC.setBackground                (getResources().getDrawable(R.drawable.backgroundlingkaranaktif));
                }
                else {
                    btnperangkatC.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaran));
                }

                //untuk nyetting warnanya kalo di klik
                if (valueS4.equals("1")) {
                    btnperangkatD.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaranaktif));
                }
                else {
                    btnperangkatD.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaran));
                }

                //untuk nyetting warnanya kalo di klik
                if (valueS5.equals("1")) {
                    btnperangkatE.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaranaktif));
                }
                else {
                    btnperangkatE.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaran));
                }

                Log.d(TAG, "Step3 database ke button S1: "+ valueS1);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getStatusSaklar(){
        isloading=true;
        //ambil semua data dr saklar
        new ApiVolley(this, new JSONObject(), "GET", urlMaster, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                isloading=false;
                try {
                    JSONObject respon = new JSONObject(result);
                    valueS1=respon.getString("S1");
                    valueS2=respon.getString("S2");
                    valueS3=respon.getString("S3");
                    valueS4=respon.getString("S4");
                    valueS5=respon.getString("S5");
                    valueS6=respon.getString("S6");
                    valueS7=respon.getString("S7");

                    //untuk nyetting warnanya kalo di klik
                    if (valueS1.equals("1")) {
                        btnperangkatA.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaranaktif));
                    }
                    else {
                        btnperangkatA.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaran));
                    }

                    //untuk nyetting warnanya kalo di klik
                    if (valueS2.equals("1")) {
                        btnperangkatB.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaranaktif));
                    }
                    else {
                        btnperangkatB.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaran));
                    }

                    //untuk nyetting warnanya kalo di klik
                    if (valueS3.equals("1")) {
                        btnperangkatC.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaranaktif));
                    }
                    else {
                        btnperangkatC.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaran));
                    }

                    //untuk nyetting warnanya kalo di klik
                    if (valueS4.equals("1")) {
                        btnperangkatD.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaranaktif));
                    }
                    else {
                        btnperangkatD.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaran));
                    }

                    //untuk nyetting warnanya kalo di klik
                    if (valueS5.equals("1")) {
                        btnperangkatE.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaranaktif));
                    }
                    else {
                        btnperangkatE.setBackground(getResources().getDrawable(R.drawable.backgroundlingkaran));
                    }

                    //untuk nyetting warnanya kalo di klik

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String result) {
            isloading=false;
            }
        });
    }

    private void updateStatusSaklar (boolean all)
    {
        Log.d(TAG, "Step1 button ke database S1: "+ valueS1);
        if (all) {
            valueS1 = valueS6;
            valueS2 = valueS6;
            valueS3 = valueS6;
            valueS4 = valueS6;
            valueS5 = valueS6;
        }
        JSONObject jBody = new JSONObject();
        try {
            jBody.put("S1", valueS1);
            jBody.put("S2", valueS2);
            jBody.put("S3", valueS3);
            jBody.put("S4", valueS4);
            jBody.put("S5", valueS5);
            jBody.put("S6", valueS6);
            jBody.put("S7", valueS7);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new ApiVolley(Home.this, jBody, "PUT", urlMaster, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.d(TAG, "onSuccess: " + result);
                try {
                    JSONObject response=new JSONObject(result); 
                    Log.d(TAG, "Step2 respon S1: "+ response.getString("S1"));
                    valueS1=response.getString("S1");
                    valueS2=response.getString("S2");
                    valueS3=response.getString("S3");
                    valueS4=response.getString("S4");
                    valueS5=response.getString("S5");
                    valueS6=response.getString("S6");
                    valueS7=response.getString("S7");


                    //untuk nyetting warnanya kalo di klik
                    //Log.d(TAG, "Step3 database ke button S1: "+ valueS1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //if (!isloading) getStatusSaklar();
            }

            @Override
            public void onError(String result) {

                Log.d(TAG, "onSuccess: " + result);
            }
        });

    }


    private void SimpanStatusSaklar (String saklar, String status) //harus diisikan nilai status dan saklarnya
    {
        JSONObject jBody = new JSONObject(); //jBody --> parameter dan value yg dibawa ketika url dieksekusi
        String user=session.getUsername();
        try {
            jBody.put("saklar", saklar);
            jBody.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new ApiVolley(Home.this, jBody, "GET", urlSimpanStatusSaklar+"?saklar="+saklar+"&status="+status+"&user="+user, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.d(TAG, "onSuccess: " + result);
                try {
                    JSONObject hasil=new JSONObject(result);
                    String status = hasil.getString("status");
                    String message = hasil.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {

                Log.d(TAG, "onSuccess: " + result);
            }
        });

    }

    class UpdateStatusSaklarTask extends TimerTask {


        public void run() {
            //calculate the new position of myBall
            // menjalankan task/kerjaan apa waktu timer belangsung
            if (!isloading) getStatusSaklar();
        }
    }


}
