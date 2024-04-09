package com.example.corideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //Variable
    Animation topanimation, buttom;
    ImageView image ;
    TextView logo,slogan ;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to remove the status bar ..
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mainasma2);
        //Animation
        topanimation= AnimationUtils.loadAnimation(this,R.animator.animation);
        buttom= AnimationUtils.loadAnimation(this,R.animator.buttom);
        // set animation
        image=findViewById(R.id.greenlogo);
        image.setAnimation(buttom);

    }
}