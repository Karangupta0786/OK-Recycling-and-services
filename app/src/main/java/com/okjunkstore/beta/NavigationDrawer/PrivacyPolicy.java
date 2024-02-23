package com.okjunkstore.beta.NavigationDrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.okjunkstore.beta.R;

public class PrivacyPolicy extends AppCompatActivity {

    WebView privacyWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        privacyWeb = findViewById(R.id.privacyWeb);

        privacyWeb.loadUrl("https://ok-5.flycricket.io/privacy.html");

    }
}