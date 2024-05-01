package com.example.corideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class page_info_personnel extends AppCompatActivity {
    EditText full, email, user, adresse, phone, gender;
    Button view;
    private DataBase databasehelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_info_personnel);
        databasehelper = new DataBase(this);

        full = findViewById(R.id.e1);
        email = findViewById(R.id.e2);
        user = findViewById(R.id.e3);
        adresse = findViewById(R.id.e4);
        phone = findViewById(R.id.e5);
        gender = findViewById(R.id.e6);
        view = findViewById(R.id.continueD);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databasehelper.getReadableDatabase();

                // Get data from user_info table
                Cursor cursorUserInfo = DataBase.getdataUserInfo(db);
                if (cursorUserInfo.moveToFirst()) {
                    String fullname = cursorUserInfo.getString(cursorUserInfo.getColumnIndex("fullname"));
                    String emailText = cursorUserInfo.getString(cursorUserInfo.getColumnIndex("email"));
                    full.setText(fullname);
                    email.setText(emailText);
                } else {
                    Toast.makeText(page_info_personnel.this, "No data available in user_info table", Toast.LENGTH_SHORT).show();
                }
                cursorUserInfo.close();

                // Get data from users table
                Cursor cursorUsers = DataBase.getdataUsers(db);
                if (cursorUsers.moveToFirst()) {
                    String username = cursorUsers.getString(cursorUsers.getColumnIndex("username"));
                    String address = cursorUsers.getString(cursorUsers.getColumnIndex("address"));
                    String phone_number = cursorUsers.getString(cursorUsers.getColumnIndex("phone_number"));
                    String genderText = cursorUsers.getString(cursorUsers.getColumnIndex("gender"));
                    user.setText(username);
                    adresse.setText(address);
                    phone.setText(phone_number);
                    gender.setText(genderText);
                } else {
                    Toast.makeText(page_info_personnel.this, "No data available in users table", Toast.LENGTH_SHORT).show();
                }
                cursorUsers.close();
            }
        });
    }
}
