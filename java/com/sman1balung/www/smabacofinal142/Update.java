package com.sman1balung.www.smabacofinal142;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Update extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbcenter;
    SQLiteDatabase db;
    public static Update updc ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        updc=this;
        dbcenter= new DataHelper(this);
        tambahIsi();
        fetchData proses = new fetchData();
        proses.execute();

    }
    private void tambahIsi() {
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        db.execSQL("delete from jadwal;");
        //finish();
    }

}
