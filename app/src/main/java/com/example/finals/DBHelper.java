package com.example.finals;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public static final String mpassword = "WtR9Gz8vvUoKpM";

    public DBHelper(Context context) {
        super(context, "PasswordManager.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table pinTable(pin TEXT primary key)");
        sqLiteDatabase.execSQL("create Table credentialsTable(id INTEGER primary key autoincrement, title TEXT, username TEXT, password TEXT, notes TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists pinTable");
        sqLiteDatabase.execSQL("drop Table if exists credentialsTable");
    }

    public Boolean insertPIN(String pin) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase(mpassword);
        ContentValues contentValues = new ContentValues();
        contentValues.put("pin", pin);
        long result = sqLiteDatabase.insert("pinTable", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean insertData(String title, String username, String password, String notes){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase(mpassword);
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("notes",   notes);
        long result = sqLiteDatabase.insert("credentialsTable", null, contentValues);
        if (result == -1) {
            return false;
        }else {
            return true;
        }
    }

    void updateData(String id, String title, String username, String password, String notes){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase(mpassword);
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("notes",   notes);
        Log.d("Test", id);

        long result = sqLiteDatabase.update("credentialsTable", contentValues, "id = ?", new String[]{id});
        if (result == -1){
            //Toast.makeText(context, "Failed to update", Toast.LENGTH_LONG).show();
            Log.d("Test", "failed");
        }else{
            Log.d("Test", "Successful");
        }
    }

    public Boolean deleteTuple(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase(mpassword);
        long result = sqLiteDatabase.delete("credentialsTable", "id = ?", new String[] {id});
        if (result == -1){
            return false;
        }else{
            return true;
        }

    }

    Cursor readAllData(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase(mpassword);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from credentialsTable order by title COLLATE NOCASE", null);
        return cursor;
    }

/*    Cursor idCursor(String title, String username, String password){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase(mpassword);
        Cursor cursor = sqLiteDatabase.rawQuery("select id from credentialsTable where title = ? and username = ? and password = ?", new String[] {title, username, password});
        return cursor;
    }*/

    Cursor passwordnotesCursor(String id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase(mpassword);
        Cursor cursor = sqLiteDatabase.rawQuery("select password, notes from credentialsTable where id = ?", new String[] {id});
        return cursor;
    }

/*    Cursor notesCursor(String id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase(mpassword);
        Cursor cursor = sqLiteDatabase.rawQuery("select notes from credentialsTable where id = ?", new String[] {id});
        return cursor;
    }*/

    public Boolean verifyPIN(String pin) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase(mpassword);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from pinTable where pin = ?", new String[]{pin});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkPIN() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase(mpassword);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from pinTable", null);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
