package com.sman1balung.www.smabacofinal142;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Jadwal2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    /*
    CREATE TABLE `jadwal` (
  `jadwal_id` int(5) NOT NULL,
  `hari` varchar(20) DEFAULT NULL,
  `kelas` varchar(10) DEFAULT NULL,
  `guru` varchar(50) DEFAULT NULL,
  `jam` varchar(20) DEFAULT NULL,
  `jamke` varchar(5) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

ALTER TABLE `jadwal`
  MODIFY `jadwal_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7589;
    * */
    public static Spinner spnhari;
    Spinner spnkelas;
    ImageView btnGuru;
    public static ImageView btnUpdate;
    ListView lstPelajaran;
    DataHelper dbcenter;
    protected Cursor cursor;
    protected Cursor cursor2;
    String[] daftar;
    String[] mapel;
    String[] gurunya;
    String[] controlwaktu;
    String[] daftar_kelas;
    public static TextView text1;
    public static Jadwal2Activity ma;
    public static ProgressDialog pd;
    public static Integer sekarang;
    public static Integer waktu;

    String[] hari ={"Senin","Selasa","Rabu","Kamis","Jumat","Sabtu","Minggu"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal2);

        fetchhari fhari = new fetchhari();
        fhari.execute();

        spnhari = (Spinner) findViewById(R.id.spnhari);
        spnkelas = (Spinner)findViewById(R.id.spnkelas);
        btnGuru = (ImageView) findViewById(R.id.btnGuru) ;
        ImageView btnUpdate2 = (ImageView) findViewById(R.id.btnUpdate);

        lstPelajaran = (ListView)findViewById(R.id.lstPelajaran) ;
        text1 = (TextView)findViewById(R.id.ll2) ;

        if(MainActivity.versi_jadwal==null){
            MainActivity.versi_jadwal="zzzz";
        }

        MainActivity.msP = getSharedPreferences(MainActivity.SHARED_PREFS,MODE_PRIVATE);
        MainActivity.versi_jadwal_device = MainActivity.msP.getString(MainActivity.TEXT,"");

        text1.setText(MainActivity.versi_jadwal+" dvc:"+MainActivity.versi_jadwal_device);
        if(MainActivity.versi_jadwal.equals(MainActivity.versi_jadwal_device)){
            btnUpdate2.setVisibility(View.GONE);
            //text1.setText("sama");
        }else{

        }
        //text1.setText(MainActivity.versi_jadwal+" "+MainActivity.versi_jadwal_device);
        //Calendar calendar = Calendar.getInstance();



        ma = this;

        ArrayAdapter<String> adapterHari = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,hari);
        spnhari.setAdapter(adapterHari);
        if(MainActivity.sekarang==null){sekarang=0;}else{sekarang=MainActivity.sekarang;}
        if(MainActivity.waktu==null){waktu=0;}else{waktu=MainActivity.waktu;}
        spnhari.setSelection(sekarang);


        spnhari.setOnItemSelectedListener(this);
        spnkelas.setOnItemSelectedListener(this);


        btnGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListGuruActivity.class);
                startActivity(intent);
            }
        });

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData("x");
            }
        });
        dbcenter = new DataHelper(this);

        buatKelas();
        //tambahIsi();

        
        refresthList();
    }

    private void saveData(String x) {
        MainActivity.msP = getSharedPreferences(MainActivity.SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = MainActivity.msP.edit();
        editor.putString(MainActivity.TEXT,x);
        editor.apply();

        Toast.makeText(this,"berhasil",Toast.LENGTH_SHORT).show();
    }

    public void dialogEvent(View view) {
        btnUpdate = (ImageView)findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder altdia= new AlertDialog.Builder(Jadwal2Activity.this);
                altdia.setMessage("Lanjutkan Update?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bukaProgresdialog();
                                fetchData proses = new fetchData();
                                proses.execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = altdia.create();
                alert.setTitle("Update Jadwal");
                alert.show();
            }
        });


    }

    private void bukaProgresdialog() {
        pd = new ProgressDialog(this);
        pd.setMax(100);
        pd.setMessage("Loading....");
        pd.setTitle("Download");
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
    }

    private void buatKelas() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT COUNT(*) AS `Rows`, `kelas` FROM `jadwal` GROUP BY `kelas` ORDER BY `kelas`", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
        }


        spnkelas.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        //finish();
    }
    private void tambahIsi() {
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        db.execSQL("INSERT INTO `jadwal` (`jadwal_id`, `hari`, `kelas`, `guru`, `jam`, `jamke`) VALUES (789, 'Senin', 'XI IPA 1', '35', '1', 'j05');");
        //finish();
    }

    private void refresthList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        String hari_slc = String.valueOf(spnhari.getSelectedItemPosition());
        String kelas_slc = spnkelas.getSelectedItem().toString();
        cursor = db.rawQuery("SELECT * FROM jadwal where hari='"+hari_slc+"' and kelas='"+kelas_slc+"'", null);
        daftar = new String[cursor.getCount()];
        mapel = new String[cursor.getCount()];
        gurunya = new String[cursor.getCount()];
        controlwaktu = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = "("+cursor.getString(4).toString()+") jam ke "+cursor.getString(5).toString();
            mapel[cc] = cursor.getString(7);
            gurunya[cc] = cursor.getString(6);
            controlwaktu[cc] = cursor.getString(4);
        }


        //lstPelajaran.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        MyAdapter madaptor = new MyAdapter(this, daftar, mapel, gurunya ){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View row = super.getView(position, convertView, parent);
                TextView lst = (TextView) row.findViewById(R.id.teksmp2);
                int ww = waktu;
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


                if(posisi && spnhari.getSelectedItemPosition()==MainActivity.sekarang && MainActivity.sekarang!=null){
                    row.setBackgroundColor(Color.parseColor("#89FF7C"));

                    //lst.setText("="+String.valueOf(getPosition(""))+"=");

                }else {
                   // row.setBackgroundColor(Color.parseColor("#ffff00"));
                }
                return  row;
            }
        };
        lstPelajaran.setAdapter(madaptor);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = parent.getItemAtPosition(position).toString();
        //String item_kls = parent.getSelectedItem().toString();
        int holdz = spnkelas.getSelectedItemPosition();
        int hold = spnhari.getSelectedItemPosition();
        //text1.setText(new String(String.valueOf(hold)));
        //text1.setText(new String(String.valueOf(holdz)));

        refresthList();
        //Toast.makeText(getApplicationContext(),item,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}
class MyAdapter extends ArrayAdapter<String>{
    Context context;
    String judul[];
    String subjudul[];
    String subjudul2[];

    public MyAdapter(@NonNull Context context, String judul[], String subjudul[], String subjudul2[]) {
        //super(context, resource);
        super(context, R.layout.row, R.id.lstPelajaran, judul);
        this.context = context;
        this.judul = judul;
        this.subjudul = subjudul;
        this.subjudul2 = subjudul2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater li = (LayoutInflater)getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = li.inflate(R.layout.row,parent,false);
        TextView judulnya = (TextView) row.findViewById(R.id.teksmp1);
        TextView subnya = (TextView) row.findViewById(R.id.teksmp2);
        TextView sub2nya = (TextView) row.findViewById(R.id.teksmp3);

        judulnya.setText(judul[position]);
        subnya.setText(subjudul[position]);
        sub2nya.setText(subjudul2[position]);

        return row;
    }


}