 package com.example.latihan123;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

 public class HistoryUpdate extends AppCompatActivity {

     private Activity activity;
     private final String formatDate = "yyyy-MM-dd";
     private final String formatTimestamp = "yyyy-MM-dd HH:mm:ss";

     private TextView tvDari,tvSampai;
     private ImageView ivDone;
     private ListView lvHistory;
     private String isiDari = "", isiSampai = "";
     private AdapterHistory adapter;
     private List<DataHistory> listHistory = new ArrayList<>();
     private String TAG = "HistoryUpdate";

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_update);

        // Daftarkan UI dari layout (xml)
        tvDari = (TextView) findViewById(R.id.tv_dari);
        tvSampai = (TextView) findViewById(R.id.tv_sampai);
        ivDone = (ImageView) findViewById(R.id.done);
        lvHistory = (ListView) findViewById(R.id.lvhistory);
        activity = this;

        // Inisialisasi nilai awal dari date untuk hari ini
        isiDari = new SimpleDateFormat(formatDate).format(new Date());
        isiSampai = new SimpleDateFormat(formatDate).format(new Date());

        tvDari.setText(isiDari);
        tvSampai.setText(isiSampai);

        // Membuat listview dapat menampilkan isi data
         adapter = new AdapterHistory(activity, listHistory);
         lvHistory.setAdapter(adapter);

        initEvent();

        // memanggil data dari webweservice
         initData();
    }

     private void initEvent() {

         // untuk memunculkan datepicker ketika tvdati di klik
         tvDari.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 final Calendar customDate;
                 SimpleDateFormat sdf = new SimpleDateFormat(formatDate);

                 Date dateValue = null;

                 try {
                     dateValue = sdf.parse(isiDari);
                 } catch (ParseException e) {
                     e.printStackTrace();
                 }

                 customDate = Calendar.getInstance();
                 final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                         customDate.set(Calendar.YEAR,year);
                         customDate.set(Calendar.MONTH,month);
                         customDate.set(Calendar.DATE,date);

                         SimpleDateFormat sdFormat = new SimpleDateFormat(formatDate, Locale.US);
                         isiDari = sdFormat.format(customDate.getTime());
                         tvDari.setText(isiDari);
                     }
                 };

                 SimpleDateFormat yearOnly = new SimpleDateFormat("yyyy");
                 new DatePickerDialog(activity ,date , Integer.parseInt(yearOnly.format(dateValue)),dateValue.getMonth(),dateValue.getDate()).show();
             }
         });

         // untuk memunculkan datepicker ketika tvsampai di klik
         tvSampai.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 final Calendar customDate;
                 SimpleDateFormat sdf = new SimpleDateFormat(formatDate);

                 Date dateValue = null;

                 try {
                     dateValue = sdf.parse(isiSampai);
                 } catch (ParseException e) {
                     e.printStackTrace();
                 }

                 customDate = Calendar.getInstance();
                 final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                         customDate.set(Calendar.YEAR,year);
                         customDate.set(Calendar.MONTH,month);
                         customDate.set(Calendar.DATE,date);

                         SimpleDateFormat sdFormat = new SimpleDateFormat(formatDate, Locale.US);
                         isiSampai = sdFormat.format(customDate.getTime());
                         tvSampai.setText(isiSampai);
                     }
                 };

                 SimpleDateFormat yearOnly = new SimpleDateFormat("yyyy");
                 new DatePickerDialog(activity ,date , Integer.parseInt(yearOnly.format(dateValue)),dateValue.getMonth(),dateValue.getDate()).show();
             }
         });

         ivDone.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 initData();
             }
         });
     }

     private void initData() //harus diisikan nilai status dan saklarnya
     {
         new ApiVolley(HistoryUpdate.this, new JSONObject(), "GET", ServerList.urlHistoryUpdate +"?dari="+isiDari+"&sampai="+isiSampai, new ApiVolley.VolleyCallback() {
             @Override
             public void onSuccess(String result) {

                 Log.d(TAG, "onSuccess: " + result);
                 try {
                     JSONObject hasil=new JSONObject(result);
                     String status = hasil.getString("status");
                     String message = hasil.getString("message");
                     listHistory.clear();

                     if(status.equals("200")){
                         // ini untuk mengambil list data ('data') dari webservice
                         JSONArray jArrayData = hasil.getJSONArray("data");
                         for(int i = 0 ; i < jArrayData.length() ; i ++){
                             // mengambil data dari list per barisnya
                             JSONObject jData = jArrayData.getJSONObject(i);
                             // untuk menambahkan 1 object ke list object('Data History')
                             listHistory.add(
                                     //membuat 1 object baru yg namanya data history, kemudian langsung memasukkan nilai ke dalam objectnya.
                                     new DataHistory(
                                             jData.getString("id")
                                             , jData.getString("saklar")
                                             , jData.getString("updated_at")
                                             , jData.getString("updated_by")
                                             , jData.getString("status")
                                     )
                             );

                         }
                     }

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

                 // untuk merefresh adapter
                 adapter.notifyDataSetChanged();
             }

             @Override
             public void onError(String result) {

                 Log.d(TAG, "onError: " + result);
             }
         });

     }

 }
