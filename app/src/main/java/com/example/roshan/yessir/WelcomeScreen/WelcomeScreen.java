package com.example.roshan.yessir.WelcomeScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.roshan.yessir.Firebase.LoginActivity;
import com.example.roshan.yessir.MainActivity;
import com.example.roshan.yessir.R;


public class WelcomeScreen extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        imageView=(ImageView) findViewById(R.id.imageView);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        imageView.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
                                           @Override
                                           public void onAnimationStart(Animation animation) {

                                           }

                                           @Override
                                           public void onAnimationEnd(Animation animation) {
                                               finish();
                                               startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                           }

                                           @Override
                                           public void onAnimationRepeat(Animation animation) {

                                           }
                                       }

        );

    }
}
