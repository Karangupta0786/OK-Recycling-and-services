package com.okjunkstore.beta.dashboard.home;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.okjunkstore.beta.NavigationDrawer.ServiceLocations;
import com.okjunkstore.beta.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.okjunkstore.beta.UserDashboard;

import java.util.ArrayList;
import java.util.List;

import soup.neumorphism.NeumorphCardView;

public class DashboardFragment extends Fragment {

//////////internet
    RelativeLayout internetNotLayout;
    TextView retry;
/////////internet

    static final int REQUEST_CALL = 1;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CALL:{
                //if request is cancelled, th array is empty
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext(), "Permission is Granted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Permission not Granted, We need to disable the Functionality", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    FloatingActionButton fab;
    ScrollView scrollView;
    SwitchMaterial switchMaterial;

    TextView sLoaction;

    ImageSlider mainSlider;

    NeumorphCardView seePrices;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        switchMaterial = v.findViewById(R.id.switch_theme);
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        seePrices = v.findViewById(R.id.see_prices);
        seePrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SeePrices.class));
            }
        });

/////   //   //internet
        internetNotLayout = v.findViewById(R.id.internet);
        retry = v.findViewById(R.id.retry);
        if (networkAvailable()){
            internetNotLayout.setVisibility(View.GONE);
        }else{
            internetNotLayout.setVisibility(View.VISIBLE);
        }
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (networkAvailable()){
                    internetNotLayout.setVisibility(View.GONE);
                }else{
                    internetNotLayout.setVisibility(View.VISIBLE);
                }
            }
        });
///  ///////////////internet

///////////Hideable Action Button
        fab = v.findViewById(R.id.fab);
        scrollView = v.findViewById(R.id.scroll);
        scrollView.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
//                if (i > 0 || i < 0 && i1 > 0 || i1 < 0 && fab.isShown())
//                    fab.hide();
            if (i > 0 || i1 > 0 || i2 > 0 || i3 > 0 && fab.isShown())
                fab.hide();
            else if(i < 0 || i1 < 0 || i2 < 0 || i3 < 0 && !fab.isShown())
                fab.show();
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity() , UserDashboard.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(fab, "transition_sell");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), pairs);
                startActivity(intent, options.toBundle());
            }
        });
///////////Hideable Action Button

        sLoaction = v.findViewById(R.id.slocation);
        sLoaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ServiceLocations.class));
            }
        });

        mainSlider = v.findViewById(R.id.slider);
        final List<SlideModel> remoteImages = new ArrayList<>();


        firebaseDatabase = FirebaseDatabase.getInstance();

        FirebaseDatabase.getInstance().getReference().child("Slider")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data: snapshot.getChildren())
                            remoteImages.add(new SlideModel(data.child("url").getValue().toString(), ScaleTypes.FIT));

                        mainSlider.setImageList(remoteImages,ScaleTypes.FIT);

                      /*  mainSlider.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onItemSelected(int i) {
                                Toast.makeText(getContext(),remoteImages.get(i).getTitle().toString(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(),remoteImages.get(i).getClass()));
                            }
                        });*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return v;
    }

    private boolean networkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (networkCapabilities != null) {
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    } else
                        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
                }
            } else {
                ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnectedOrConnecting();
            }
        }
        return false;
    }
}