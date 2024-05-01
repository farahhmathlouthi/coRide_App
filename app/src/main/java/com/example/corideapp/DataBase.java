package com.example.corideapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="INFO_PERSONNEL";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_USER_INFO = "user_info";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULLNAME = "fullname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_GENDER = "gender";
    private static final String CREATE_TABLE_USER_INFO = "CREATE TABLE " + TABLE_USER_INFO + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_FULLNAME + " TEXT," +
            COLUMN_EMAIL + " TEXT," ;


    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USERNAME + " TEXT," +
            COLUMN_ADDRESS + " TEXT," +
            COLUMN_PHONE_NUMBER + " TEXT," +
            COLUMN_GENDER + " TEXT," +
            "user_info_id INTEGER," +  // Foreign key referencing the id column in user_info table
            " FOREIGN KEY (user_info_id) REFERENCES " + TABLE_USER_INFO + "(" + COLUMN_ID + "))";






    public DataBase( @Nullable Context context) {
        super( context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the user_info table with foreign key constraint
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER_INFO + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_FULLNAME + " TEXT," +
                COLUMN_EMAIL + " TEXT)");
        // Create the users table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " TEXT," +
                COLUMN_ADDRESS + " TEXT," +
                COLUMN_PHONE_NUMBER + " TEXT," +
                COLUMN_GENDER + " TEXT" +
                ")");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop both tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Recreate the tables
        onCreate(db);
    }
    public boolean insertUserInfoData(String fullname, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULLNAME, fullname);
        values.put(COLUMN_EMAIL, email);
        // Make sure to change this column name if it's different in your table
        long var = db.insert(TABLE_USER_INFO, null, values);
        if (var == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean insertUserData( String username, String address, String phone_number, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_PHONE_NUMBER, phone_number);
        values.put(COLUMN_GENDER, gender);
        long var = db.insert(TABLE_USERS, null, values);
        if (var == -1) {
            return false;
        } else {
            return true;
        }
    }
    public static Cursor getdataUserInfo(SQLiteDatabase db) {
        Cursor cursorUserInfo = db.rawQuery("SELECT * FROM TABLE_USER_INFO", null);
        return cursorUserInfo;
    }
    public static Cursor getdataUsers(SQLiteDatabase db){
        Cursor cursorUsers = db.rawQuery("SELECT * FROM TABLE_USER", null);
        return cursorUsers;

    }








}
