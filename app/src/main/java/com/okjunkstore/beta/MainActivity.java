package com.okjunkstore.beta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;

    //variables
    Animation topanim,bottomanim;

    ImageView ok,truck;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        //Animations
        topanim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        ok = findViewById(R.id.imageView);
        truck = findViewById(R.id.truck);

        ok.setAnimation(topanim);
        truck.setAnimation(bottomanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, RetailerDashboard.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(ok,"logo_image");
//                pairs[1] = new Pair<View,String>(textView,"logo_name");
                ActivityOptions optons = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,optons.toBundle());
                finish();
            }
        },SPLASH_SCREEN);
    }

/*    @Override
    protected void onStart() {
        super.onStart();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, signup.class));
            finish();
        }
    }*/
}