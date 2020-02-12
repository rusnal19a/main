package com.sman1balung.www.smabacofinal142;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class ListGuruActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText edtGuru;
    ListView lstGuru;
    String[] hari ={"Senin","Selasa","Rabu","Kamis","Jumat","Sabtu","Minggu"};
    ArrayAdapter adapterGuru;
    DataHelper dbcenter;
    String[] daftar;
    String[] daftar_temp;
    protected Cursor cursor;
    ListView lstmapel;
    String[] daftarmapel;
    String[] daftarmapel2;
    String[] daftarmapel3;
    String[] controlwaktu;
    String namaguruku;
    View lyoutmapel;
    String halaman="guru";
    TextView textjadwal;
    Spinner spnjammengajar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_guru);

        edtGuru = (EditText)findViewById(R.id.edtGuru);
        lstGuru = (ListView)findViewById(R.id.lstGuru);
        lstmapel = (ListView)findViewById(R.id.lstmapel);
        lyoutmapel = findViewById(R.id.lyoutpelajaran);
        textjadwal = (TextView) findViewById(R.id.texjadwal);
        spnjammengajar = (Spinner)findViewById(R.id.spnjammengajar);


        spnjammengajar.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,hari));
        spnjammengajar.setSelection(Jadwal2Activity.sekarang);

        spnjammengajar.setOnItemSelectedListener(this);

        dbcenter = new DataHelper(this);
        buatGuru();;
        adapterGuru = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 ,daftar);

        lstGuru.setAdapter(adapterGuru);


        lstGuru.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //daftar_temp = adapterGuru.get;
                //Toast.makeText(ListGuruActivity.this, adapterGuru.getItem(position).toString(),Toast.LENGTH_SHORT).show();

                edtGuru.onEditorAction(EditorInfo.IME_ACTION_DONE);
                namaguruku = adapterGuru.getItem(position).toString();
                textjadwal.setText("Jadwal mengajar "+namaguruku);
                lyoutmapel.setVisibility(View.VISIBLE);
                halaman="mapel";

                refreshList();
            }
        });
        refreshList();
        //lstmapel.setAdapter(adapterGuru);

        edtGuru.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ListGuruActivity.this.adapterGuru.getFilter().filter(s);
                adapterGuru.notifyDataSetChanged();
                //Toast.makeText(ListGuruActivity.this, adapterGuru.getItem(1).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    @Override
    public void onBackPressed() {
       if (halaman == "guru") {
            super.onBackPressed();
       } else {
            lyoutmapel.setVisibility(View.GONE);
//
            halaman = "guru";

        }
//        ;
    }

    private void refreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
//        String hari_slc = String.valueOf(spnhari.getSelectedItemPosition());
//        String kelas_slc = spnkelas.getSelectedItem().toString();
            String hariku=String.valueOf(spnjammengajar.getSelectedItemPosition());

        cursor = db.rawQuery("SELECT * FROM jadwal where namaguru='"+namaguruku+"' and hari='"+hariku+"' ", null);
        daftarmapel = new String[cursor.getCount()];
        daftarmapel2 = new String[cursor.getCount()];
        daftarmapel3 = new String[cursor.getCount()];
        controlwaktu = new String[cursor.getCount()];
//        mapel = new String[cursor.getCount()];
//        gurunya = new String[cursor.getCount()];
//        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            controlwaktu[cc] = cursor.getString(4);
            //String[] controlwaktux = controlwaktu[cc].split("-");
            daftarmapel[cc] = (cc+1)+". ("+cursor.getString(4).toString()+") jam ke "+cursor.getString(5).toString();
            daftarmapel2[cc] = cursor.getString(2).toString();

//            mapel[cc] = cursor.getString(7);
//            gurunya[cc] = cursor.getString(6);
        }
//
        MyAdapter madaptor = new MyAdapter(this, daftarmapel,daftarmapel2,daftarmapel3 ){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View row = super.getView(position, convertView, parent);
                //TextView lst = findViewById(getTaskId());
                int ww = Jadwal2Activity.waktu;
                Boolean posisi;
                String[] controlpecah= controlwaktu[position].split("-");
                String waktuawal = controlpecah[0].replaceAll("\\D+","").replace(".","");
                String waktuakhir = controlpecah[1].replaceAll("\\D+","").replace(".","");
                int angkaawal = Integer.parseInt(waktuawal.toString())*1;
                int angkaakhir = Integer.parseInt(waktuakhir.toString())*1;
                if(ww>angkaawal && ww<=angkaakhir){
                    posisi = true;
                }else{
                    posisi = false;
                }


                if(posisi && spnjammengajar.getSelectedItemPosition()==MainActivity.sekarang && MainActivity.sekarang!=null){

                        row.setBackgroundColor(Color.parseColor("#89FF7C"));
                        //lst.settext



                    //lst.setText("="+String.valueOf(getPosition(""))+"=");

                }else {
                    // row.setBackgroundColor(Color.parseColor("#ffff00"));
                }
                return  row;
            }
        };
        lstmapel.setAdapter(madaptor);
        //lstmapel.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftarmapel));
//        MyAdapter madaptor = new MyAdapter(this, daftar, mapel, gurunya );
//        lstPelajaran.setAdapter(madaptor);

    }

    private void buatGuru() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT COUNT(*) AS `Rows`, `namaguru` FROM `jadwal` GROUP BY `namaguru` ORDER BY `namaguru`", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
        }
//
//
        //lstGuru.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
//        //finish();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        refreshList();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
