package com.sman1balung.www.smabacofinal142;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JadwalActivity extends AppCompatActivity {
    Button buttonTest;
    TextView textTest;
    TextView textViewJSON;
    String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        buttonTest = (Button) findViewById(R.id.buttonTest);
        textTest = (TextView) findViewById(R.id.textTest);

        buttonTest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_LONG).show();
                finish();
                //Toast.makeText(Jadwal2Activity.ma,"Update Berhasil bos",Toast.LENGTH_LONG).show();
                textTest.setText("berhasil");

            }
        });
    }
    public void getJSON(View view){

        new BackgroundTask().execute();
    }
    class BackgroundTask extends AsyncTask<Void,Void,String>{
        String jsonUrl;
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            jsonUrl = "http://180.251.93.149:88/rusnal/www/android%20sync/SubjectFullForm.php";
            pd = new ProgressDialog(JadwalActivity.this);
            pd.setTitle("loadign");
            pd.setMessage("tunggu");
            pd.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textview = (TextView)findViewById(R.id.textTestjson);
            textview.setText(result);
            pd.dismiss();
            if(result==null){Toast.makeText(getApplicationContext(),"tidak sukses",Toast.LENGTH_SHORT).show();}
        }

        @Override
        protected String doInBackground(Void... Voids) {


            try {
                URL url = new URL(jsonUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {

                    stringBuilder.append(JSON_STRING + "\n");

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


    };







}
