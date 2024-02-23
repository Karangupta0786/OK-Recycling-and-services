package com.okjunkstore.beta.NavigationDrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.okjunkstore.beta.R;

public class ServiceLocations extends AppCompatActivity {

    //Variables
    Button tnC;
    ImageView image;
    TextView logo_name, Slogo_Text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_locations);

        //Hooks to all xml elements in activity_service_locations.xml
        logo_name = findViewById(R.id.logo_name);
        Slogo_Text = findViewById(R.id.slogan_name);
        image = findViewById(R.id.logo_image);
        tnC = findViewById(R.id.tnc);

    }//onCreat Method end


    public void goto_tnc(View view){
        Intent intent = new Intent(ServiceLocations.this, TermsAndConditions.class);

        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(image, "logo_image");
        pairs[1] = new Pair<View, String>(logo_name, "logo_name");
        pairs[2] = new Pair<View, String>(Slogo_Text, "logo_desc");
        pairs[3] = new Pair<View, String>(tnC, "button_tran");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ServiceLocations.this, pairs);
        startActivity(intent, options.toBundle());
        finish();
    }
}