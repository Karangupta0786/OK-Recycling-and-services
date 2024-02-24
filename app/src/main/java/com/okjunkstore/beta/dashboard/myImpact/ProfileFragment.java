package com.okjunkstore.beta.dashboard.myImpact;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.okjunkstore.beta.R;
import com.okjunkstore.beta.OrderActivity;
import com.okjunkstore.beta.UserProfile;
import com.google.android.material.button.MaterialButton;

public class ProfileFragment extends Fragment {

    public ProfileFragment(){
    }

    MaterialButton profile, signup;
    Button how;

    Dialog dialog;

    TextView recycled,tree,water,oil,energy;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        recycled = view.findViewById(R.id.recycled_kg);
        tree = view.findViewById(R.id.tree_saved);
        water = view.findViewById(R.id.water_saved);
        energy = view.findViewById(R.id.energy_saved);
        oil = view.findViewById(R.id.oil_saved);

        profile = view.findViewById(R.id.login_btn);
        signup = view.findViewById(R.id.signup_btn);
        how = view.findViewById(R.id.how);
        dialog = new Dialog(getContext());

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent intent =new Intent(getActivity() , UserProfile.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(profile, "transition_login");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), pairs);
                startActivity(intent, options.toBundle());
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpDialoge();
            }
        });

        how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity() , HowWeWork.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(how, "transition_signup");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), pairs);
                startActivity(intent, options.toBundle());            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Recycled");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String recycle = snapshot.child("RecycledKg").getValue().toString();
                recycled.setText(recycle);

                String treeSaved = snapshot.child("TreeImpact").getValue().toString();
                tree.setText(treeSaved);
                String waterSaved = snapshot.child("ImpactWater").getValue().toString();
                water.setText(waterSaved);
                String energySaved = snapshot.child("ImpactEnergy").getValue().toString();
                energy.setText(energySaved);
                String oilSaved = snapshot.child("ImpactOil").getValue().toString();
                oil.setText(oilSaved);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
            private void openSignUpDialoge() {
        dialog.setContentView(R.layout.signup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        TextView btn = dialog.findViewById(R.id.just);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getContext(), OrderActivity.class));
            }
        });
        dialog.show();
    }
//    public void callLoginScreen(View view){
//        Intent intent = new Intent(getContext() , Login.class);
//        startActivity(intent);
//    }
//
//    public void callSignUpScreen(View view){
//        Intent intent = new Intent(getContext() , SingUp.class);
//        startActivity(intent);
//    }
}