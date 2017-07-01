package utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Alejandro on 30/06/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/apestegui.alejandro.com.a12_sqlite_12/databases/";
    private static String DB_NAME = "TIENDA.sqlite";

    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DataBaseHelper(Context myContext) {
        super(myContext, DB_NAME, null, 1);
        this.myContext = myContext;
    }

    public void createDataBase() throws IOException {
        boolean dbExixts = checkDataBase();

        if(dbExixts){
            // do nothing, database already exists
        }
        else{
            this.getReadableDatabase();
            try{
                copyDataBase();
            }catch (IOException e){
                throw  new Error("Error copying database");
            }
        }
    }

    private void copyDataBase() throws IOException {

        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH+DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    private boolean checkDataBase() {
        SQLiteDatabase checkBD = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkBD = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
            //database doesn't exist
        }
        return checkBD != null ? true : false;
    }

    public SQLiteDatabase openDataBase() throws SQLException{
        if(myDataBase==null || !myDataBase.isOpen()){
            //la abro
            String myPath = DB_PATH+DB_NAME;
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }

        return myDataBase;
    }
    public synchronized void close(){
        if(myDataBase!=null){
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
