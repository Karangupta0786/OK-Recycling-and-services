package com.okjunkstore.beta.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.okjunkstore.beta.FragmentsRetailerDashbord.RetailerBuyFragment;
import com.okjunkstore.beta.FragmentsRetailerDashbord.RetailerDashboardFragment;
import com.okjunkstore.beta.FragmentsRetailerDashbord.RetailerProfileFragment;
import com.okjunkstore.beta.FragmentsRetailerDashbord.RetailerSellFragment;
import com.okjunkstore.beta.NavigationDrawer.ServiceLocations;
import com.okjunkstore.beta.NavigationDrawer.TermsAndConditions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.okjunkstore.beta.R;
import com.okjunkstore.beta.SeePrices;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView title;
    ChipNavigationBar chipNavigationBar;

    //Drawer menu
    static final float END_SCALE = 0.7f;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    LinearLayout contentView;
    private long backPressed;
    private Toast backToast;

    static final int REQUEST_CALL = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CALL: {
                //if request is cancelled, th array is empty
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(DashboardActivity.this, "Permission is Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DashboardActivity.this, "Permission not Granted, We need to disable the Functionality", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        FirebaseMessaging.getInstance().subscribeToTopic("buy");

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layuot);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menu_icon);
        title = findViewById(R.id.activityTitle);
        contentView = findViewById(R.id.content);

        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RetailerDashboardFragment()).commit();
        chipNavigationBar.setItemSelected(R.id.bottom_nav_dashboard,true);
        bottomMenu();

        navigationDrawer();

        permission();

    }//OnCreat Method

    private void permission() {
        if (ContextCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(DashboardActivity.this,Manifest.permission.CALL_PHONE)){
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DashboardActivity.this);
                builder.setTitle("Request Permission");
                builder.setMessage("You should enable this permission to CALL_PHONE so we can to that and that...");
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(DashboardActivity.this,new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    }
                });
                builder.setPositiveButton("Grant Permission", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(DashboardActivity.this,new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    }
                });
                builder.show();

            }else{
                ActivityCompat.requestPermissions(DashboardActivity.this,new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

            }
        }else {
            //Permission has already been granted

        }
    }

    //Navigation Drawer Functions
    private void navigationDrawer() {

        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigaitonDrawer();
    }

    private void animateNavigaitonDrawer() {
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        drawerLayout.setScrimColor(Color.TRANSPARENT);
//        drawerLayout.setScrimColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset){

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_location:
                Intent intt = new Intent(getApplicationContext(), ServiceLocations.class);
                startActivity(intt);
                break;
            case R.id.nav_terms:
                Intent inte = new Intent(getApplicationContext(), TermsAndConditions.class);
                startActivity(inte);
                break;
            case R.id.nav_all_categories:
                Intent categoriesIntent = new Intent(getApplicationContext(), SeePrices.class);
                startActivity(categoriesIntent);
                break;
            case R.id.nav_email:
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto: okjunkstore@gmail.com"));
                startActivity(in);
                break;
            case R.id.nav_insta:
                gotoUrl("https://www.instagram.com/okjunkstore/");
                break;
            case R.id.nav_yt:
                gotoUrl("https://www.youtube.com/watch?v=GrXPJHfCEbc");
                break;
            case R.id.nav_rate_us:
                gotoUrl("https://play.google.com/store/apps/details?id=com.okjunkstore.beta");
                break;
            case R.id.nav_shop:
                Toast.makeText(DashboardActivity.this, "Currently we don't have any offline shops", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_share:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String shareBody = "Sell Your Unusable Junk, Download Now : https://play.google.com/store/apps/details?id=com.okjunkstore.beta&hl=en";
                String shareSub = "OK JUNK STORE - BUY & SELL";
                share.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                share.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(share,"Share OK Junk Store App"));
                break;
        }
        return true;
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    @Override
    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(RetailerDashboard.this).setIcon(R.drawable.warning_24);
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if (backPressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                backToast.cancel();
                return;
            } else {
            backToast = Toast.makeText(DashboardActivity.this, "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        /* else{
            builder.setTitle("EXIT");
            builder.setMessage("Are you sure you want to EXIT");
            builder.setCancelable(true);
            builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }*/
        backPressed = System.currentTimeMillis();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.bottom_nav_dashboard:
                        fragment = new RetailerDashboardFragment();
                        title.setText("OK Junk Store");
                        break;
                    case R.id.bottom_nav_sell:
                        fragment = new RetailerSellFragment();
                        title.setText("OK Junk Store");
                        break;
                    case R.id.bottom_nav_buy:
                        fragment = new RetailerBuyFragment();
                        title.setText("OK Junk Store");
                        permission();
                        break;
                    case R.id.bottom_nav_profile:
                        fragment = new RetailerProfileFragment();
                        title.setText("OK Services");
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
    }
}