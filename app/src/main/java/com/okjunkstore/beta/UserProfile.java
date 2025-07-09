package com.okjunkstore.beta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.okjunkstore.beta.Helperclass.HomeAdapter.ownerOrderData;
import com.okjunkstore.beta.Helperclass.HomeAdapter.OwnerOrderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserProfile extends AppCompatActivity {

    private RecyclerView houseOwners, apartmentOwners, shopOwners, schoolOwners;
    private LinearLayout houseNoData, apartmentNoData, shopNoData, schoolNoData;
    private List<ownerOrderData> list1,list2,list3,list4;
    private DatabaseReference reference,dbRef;

    private OwnerOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_user_profile);

        houseOwners = findViewById(R.id.house_recycler);
        apartmentOwners = findViewById(R.id.apartment_recycler);
        shopOwners = findViewById(R.id.shop_recycler);
        schoolOwners = findViewById(R.id.school_recycler);

        houseNoData = findViewById(R.id.houseNoData);
        apartmentNoData = findViewById(R.id.apartmentNoData);
        shopNoData = findViewById(R.id.shopNoData);
        schoolNoData = findViewById(R.id.schoolNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("Notice");

        list1 = new ArrayList<>();
        apartmentOwnerData();
        shopOwnerData();
        schoolOwnerData();
        houseOwnerData();

    }

    private void houseOwnerData() {
        dbRef = reference.child("House");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list1 = new ArrayList<>();
                if (!snapshot.exists()){
                    houseNoData.setVisibility(View.VISIBLE);
                    houseOwners.setVisibility(View.GONE);
                }else{
                    houseNoData.setVisibility(View.GONE);
                    houseOwners.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        ownerOrderData data = snapshot1.getValue(ownerOrderData.class);
                        list1.add(data);
                    }

                    // Define the format of your dates
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");

                    // Sort using a custom comparator
                    Collections.sort(list1, new Comparator<ownerOrderData>() {
                        public int compare(ownerOrderData d1, ownerOrderData d2) {
                            try {
                                return sdf.parse(d1.getDate()).compareTo(sdf.parse(d2.getDate()));
                            } catch (ParseException e) {
                                throw new IllegalArgumentException("Invalid date format", e);
                            }
                        }
                    });

                    Collections.reverse(list1);

                    houseOwners.setHasFixedSize(true);
                    houseOwners.setLayoutManager(new LinearLayoutManager(UserProfile.this));
                    adapter = new OwnerOrderAdapter(list1,UserProfile.this);
                    houseOwners.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void apartmentOwnerData() {
        dbRef = reference.child("Apartment");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if (!snapshot.exists()){
                    apartmentNoData.setVisibility(View.VISIBLE);
                    apartmentOwners.setVisibility(View.GONE);
                }else{

                    apartmentNoData.setVisibility(View.GONE);
                    apartmentOwners.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        ownerOrderData data = snapshot1.getValue(ownerOrderData.class);
                        list1.add(data);
                    }
                    apartmentOwners.setHasFixedSize(true);
                    apartmentOwners.setLayoutManager(new LinearLayoutManager(UserProfile.this));
                    adapter = new OwnerOrderAdapter(list2,UserProfile.this);
                    apartmentOwners.setAdapter(adapter);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void shopOwnerData() {
        dbRef = reference.child("Shop");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if (!snapshot.exists()){
                    shopNoData.setVisibility(View.VISIBLE);
                    shopOwners.setVisibility(View.GONE);
                }else{
                    shopNoData.setVisibility(View.GONE);
                    shopOwners.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        ownerOrderData data = snapshot1.getValue(ownerOrderData.class);
                        list1.add(data);
                    }
                    shopOwners.setHasFixedSize(true);
                    shopOwners.setLayoutManager(new LinearLayoutManager(UserProfile.this));
                    adapter = new OwnerOrderAdapter(list3,UserProfile.this);
                    shopOwners.setAdapter(adapter);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void schoolOwnerData() {
        dbRef = reference.child("School");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4 = new ArrayList<>();
                if (!snapshot.exists()){
                    schoolNoData.setVisibility(View.VISIBLE);
                    schoolOwners.setVisibility(View.GONE);
                }else{
                    schoolNoData.setVisibility(View.GONE);
                    schoolOwners.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        ownerOrderData data = snapshot1.getValue(ownerOrderData.class);
                        list1.add(data);
                    }
                    schoolOwners.setHasFixedSize(true);
                    schoolOwners.setLayoutManager(new LinearLayoutManager(UserProfile.this));
                    adapter = new OwnerOrderAdapter(list4,UserProfile.this);
                    schoolOwners.setAdapter(adapter);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}