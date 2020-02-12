package com.sman1balung.www.smabacofinal142;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by SERVER 4 on 09/07/2019.
 */

public class DataHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "smabaco.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql = "CREATE TABLE `jadwal` (\n" +
                "  `jadwal_id` int(5) NOT NULL,\n" +
                "  `hari` varchar(20) DEFAULT NULL,\n" +
                "  `kelas` varchar(10) DEFAULT NULL,\n" +
                "  `guru` varchar(50) DEFAULT NULL,\n" +
                "  `jam` varchar(20) DEFAULT NULL,\n" +
                "  `jamke` varchar(5) DEFAULT NULL,\n" +
                "  `namaguru` varchar(50) DEFAULT NULL,\n" +
                "  `mapel` varchar(50) DEFAULT NULL\n" +
                ") ; ALTER TABLE `jadwal`\n" +
                "  ADD PRIMARY KEY (`jadwal_id`);ALTER TABLE `jadwal`\n" +
                "  MODIFY `jadwal_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7589;";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO `jadwal` (`jadwal_id`, `hari`, `kelas`, `guru`, `jam`, `jamke`, `namaguru`, `mapel`) VALUES\n" +
                "(12, '0', 'X MIPA 2', '', '07.00 - 07.45', '1','Silahkan update jadwal terlebih dahulu','');";
        db.execSQL(sql);



    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
