package com.sman1balung.www.smabacofinal142;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

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
 * Created by SERVER 4 on 09/07/2019.
 */

public class fetchData extends AsyncTask<Void, Void, Void> {

    String data="";
    String dataparsed="";
    String singlepaarsed = "";
    String test;
    DataHelper dbcenter;
    SharedPreferences sp = MainActivity.msP;
    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL("https://sman1balung.sch.id/webapp/json_for_app.php");
            URL url2 = new URL("https://api.myjson.com/bins/w60lj");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while (line!=null){
                line=bufferedReader.readLine();
                data=data+line;
                test=data;
            }
            JSONArray jsonArray = new JSONArray(data);

            dbcenter = new DataHelper(Jadwal2Activity.ma);
           SQLiteDatabase db = dbcenter.getWritableDatabase();
            db.execSQL("delete from jadwal;");

            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                singlepaarsed = (String) jsonObject.get("nama");
                db.execSQL("INSERT INTO `jadwal` (`jadwal_id`, `hari`, `kelas`, `guru`, `jam`, `jamke`, `namaguru`, `mapel`) VALUES "+singlepaarsed+";");
                dataparsed = dataparsed +singlepaarsed+"\n";


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences.Editor editor = MainActivity.msP.edit();
        editor.putString(MainActivity.TEXT,MainActivity.versi_jadwal);
        editor.apply();

        return null;
    }




    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Jadwal2Activity.pd.dismiss();
        Toast.makeText(Jadwal2Activity.ma,"Update Berhasil bos",Toast.LENGTH_LONG).show();
        //Jadwal2Activity.text1.setText(dataparsed);
    }
    private void tambahIsi() {
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        //db.execSQL("delete from jadwal;");
        db.execSQL("INSERT INTO `jadwal` (`jadwal_id`, `hari`, `kelas`, `guru`, `jam`, `jamke`) VALUES (91, 'senin', 'XI IPA 1', '35', '1', 'j05');");
        //finish();
    }
}
