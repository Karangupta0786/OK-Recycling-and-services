package com.okjunkstore.beta.NavigationDrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.okjunkstore.beta.R;

public class TermsAndConditions extends AppCompatActivity {

    Button callSL;
    ImageView image;
    TextView logo_name, slogo_Text, policy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_terms_and_conditions);

        //Hooks
        callSL = findViewById(R.id.sl_screen);
        image = findViewById(R.id.logo_image);
        logo_name = findViewById(R.id.logo_name);
        slogo_Text = findViewById(R.id.slogan_name);
        policy = findViewById(R.id.privacyp);

        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TermsAndConditions.this,PrivacyPolicy.class));
            }
        });

        callSL.setOnClickListener(view -> {
            Intent intent = new Intent(TermsAndConditions.this, ServiceLocations.class);

            Pair[] pairs = new Pair[4];
            pairs[0] = new Pair<View, String>(image, "logo_image");
            pairs[1] = new Pair<View, String>(logo_name, "logo_name");
            pairs[2] = new Pair<View, String>(slogo_Text, "logo_desc");
            pairs[3] = new Pair<View, String>(callSL, "button_tran");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TermsAndConditions.this, pairs);
            startActivity(intent, options.toBundle());
            finish();
        });
    }
}