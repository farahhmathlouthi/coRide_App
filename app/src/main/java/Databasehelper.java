import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Databasehelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="INFO_PERSONNEL";
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULLNAME = "fullname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_GENDER = "gender";




    public Databasehelper( @Nullable Context context) {
        super( context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_USERS+"(id INTEGER PRIMARY KEY AUTOINCREMENT , fullname TEXT , email TEXT , username TEXT ,address TEXT ,phone_number TEXT , gender TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS );

    }
    public boolean insertData (String fullname, String email ,String username ,String address ,String phone_number , String gender){
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULLNAME , fullname) ;
        values.put(COLUMN_EMAIL,email);
        values.put(COLUMN_USERNAME,username);
        values.put(COLUMN_ADDRESS,address);
        values.put(COLUMN_PHONE_NUMBER,phone_number) ;
        values.put(COLUMN_GENDER,gender);
        long var = db.insert(TABLE_USERS,null,values);
        if (var==-1){
            return false ;
        }else{
            return true ;
        }


    }



}
