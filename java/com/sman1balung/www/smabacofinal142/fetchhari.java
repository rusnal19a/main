package com.sman1balung.www.smabacofinal142;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by SERVER 4 on 11/07/2019.
 */

public class fetchhari extends AsyncTask<Void, Void, Void> {
            String data="";
        String dataparsed="";
        String singlepaarsed = "";
        String singlepaarsedwaktu = "";
//        String test;
        DataHelper dbcenter;

    public static final String SHARED_PREFS ="sharedPrefs";
    public static final String TEXT ="text";
    @Override
    protected Void doInBackground(Void... params) {


        try {
        URL url = new URL("https://sman1balung.sch.id/webapp/json_for_app_time.php");

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line="";
        while (line!=null){
        line=bufferedReader.readLine();
        data=data+line;

        }
        JSONArray jsonArray = new JSONArray(data);

        dbcenter = new DataHelper(Jadwal2Activity.ma);
        //SQLiteDatabase db = dbcenter.getWritableDatabase();


        for (int i=0;i<jsonArray.length();i++){
        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

        singlepaarsed = (String) jsonObject.get("hari");
        singlepaarsedwaktu = (String) jsonObject.get("jam");
        MainActivity.versi_jadwal=(String) jsonObject.get("versi_jadwal");

        MainActivity.sekarang = Integer.valueOf(singlepaarsed);
        MainActivity.waktu = Integer.valueOf(singlepaarsedwaktu);
        dataparsed = dataparsed +singlepaarsed+"\n";


        }
        } catch (MalformedURLException e) {
        e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        } catch (JSONException e) {
        e.printStackTrace();
        }

        return null;
    }




    @Override
protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Jadwal2Activity.pd.dismiss();
    //Jadwal2Activity.spnhari.setSelection(Jadwal2Activity.sekarang);
        //Toast.makeText(MainActivity.this,"Hari Berhasil bos",Toast.LENGTH_LONG).show();
        //Jadwal2Activity.text1.setText(dataparsed);
        }

        }