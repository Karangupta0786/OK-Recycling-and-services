package com.okjunkstore.beta.dashboard.sell;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.okjunkstore.beta.R;
import com.okjunkstore.beta.UserDashboard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import soup.neumorphism.NeumorphCardView;

public class SellFragment extends Fragment {

    static final int REQUEST_CALL = 1;
    FloatingActionButton call;
    FloatingActionButton sellActivity;

    NeumorphCardView cardCall, cardSell;

    TextView books,plastics,steels,irons,bikes,cars,electronics;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RelativeLayout electronicPrice,newsPrice,steelPrice,plasticPrice,ironPrice,alloysPrices;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sell, container, false);

        call = v.findViewById(R.id.callUs);
        sellActivity = v.findViewById(R.id.post);
        sellActivity.setOnClickListener(view1 -> {
//            Toast.makeText(getContext(), "Please Do not apply Dark Theme", Toast.LENGTH_LONG).show();
            Intent intent =new Intent(getActivity() , UserDashboard.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(sellActivity, "transition_sell");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), pairs);
            startActivity(intent, options.toBundle());
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(),Manifest.permission.CALL_PHONE)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Request Permission");
                    builder.setMessage("You should enable this permission to CALL_PHONE so we can to that and that...");
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        }
                    });
                    builder.setPositiveButton("Grant Permission", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        }
                    });
                    builder.show();

                    }else{
                        ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

                    }
                }else {
                    //Permission has already been granted

                }

               Intent intent = new Intent(Intent.ACTION_CALL);
               intent.setData(Uri.parse("tel:7905845935"));
               if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                   return;
               }
               startActivity(intent);
            }

        });


        cardCall = v.findViewById(R.id.card_call);
        cardCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(),Manifest.permission.CALL_PHONE)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Request Permission");
                        builder.setMessage("You should enable this permission to CALL_PHONE so we can to that and that...");
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                            }
                        });
                        builder.setPositiveButton("Grant Permission", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                            }
                        });
                        builder.show();

                    }else{
                        ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

                    }
                }else {
                    //Permission has already been granted

                }

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:7905845935"));
                if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                startActivity(intent);
            }

        });
        cardSell = v.findViewById(R.id.card_sell_now);
        cardSell.setOnClickListener(view1 -> {
//            Toast.makeText(getContext(), "Please Do not apply Dark Theme", Toast.LENGTH_LONG).show();
            Intent intent =new Intent(getActivity() , UserDashboard.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(cardSell, "transition_sell");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), pairs);
            startActivity(intent, options.toBundle());
        });


////////////////////PRICE
////////////////////PRICE
        electronicPrice = v.findViewById(R.id.electronicPrice);
        plasticPrice = v.findViewById(R.id.plasticPrice);
        newsPrice = v.findViewById(R.id.newspaperPrice);
        ironPrice = v.findViewById(R.id.ironPrice);
        alloysPrices = v.findViewById(R.id.alloys_prices);
        steelPrice = v.findViewById(R.id.steelPrice);
        plasticPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BottomSheetPriceFragment bottomSheetPriceFragment = new BottomSheetPriceFragment();
//                bottomSheetPriceFragment.show(getActivity().getSupportFragmentManager(), bottomSheetPriceFragment.getTag());

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Prices");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Bundle bundle = new Bundle();
                        String plastic = "Plastic";
                        String plasticRate = snapshot.child("plastic").getValue().toString();
                        String plastic5 = "Plastic 50Kg+";
                        String plastic5Rate = snapshot.child("plastic5").getValue().toString();
                        String plastic10 = "Plastic 100Kg+";
                        String plastic10Rate = snapshot.child("plastic10").getValue().toString();
                        String blackPlastic = "Black/Hard Plastic";
                        String blackPlasticRate = "Rs.5/Kg";

                        bundle.putString("plasti", plastic );
                        bundle.putString("plasticR", plasticRate);
                        bundle.putString("i5", plastic5 );
                        bundle.putString("i5R", plastic5Rate);
                        bundle.putString("i10", plastic10);
                        bundle.putString("i10R", plastic10Rate);
                        bundle.putString("bplastic", blackPlastic );
                        bundle.putString("bplasticRate", blackPlasticRate );
                        BottomSheetPriceFragment fragInfo = new BottomSheetPriceFragment();
                        fragInfo.show(getActivity().getSupportFragmentManager(),fragInfo.getTag());
                        fragInfo.setArguments(bundle);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        electronicPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Prices");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Bundle bundle = new Bundle();
                        String washing = "Washing Machine";
                        String washingRate = snapshot.child("ElecWashing").getValue().toString();
                        String fridge = "Fridge";
                        String fridgeRate = snapshot.child("ElecFridge").getValue().toString();
                        String tv = "T.V.";
                        String tvRate = snapshot.child("ElecTv").getValue().toString();

                        bundle.putString("plasti", washing );
                        bundle.putString("plasticR", washingRate);
                        bundle.putString("i5", tv );
                        bundle.putString("i5R", tvRate);
                        bundle.putString("i10", fridge );
                        bundle.putString("i10R", fridgeRate );
                        BottomSheetPriceFragment fragInfo = new BottomSheetPriceFragment();
                        fragInfo.show(getActivity().getSupportFragmentManager(),fragInfo.getTag());
                        fragInfo.setArguments(bundle);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        newsPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Prices");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Bundle bundle = new Bundle();
                        String book = "Book/Paper/Cartoon";
                        String bookRate = snapshot.child("Book").getValue().toString();
                        String book5 = "Books/Paper/Cartoon 50Kg+";
                        String book5Rate = snapshot.child("Book5").getValue().toString();
                        String book10 = "Books/Paper/Cartoon 100Kg+";
                        String book10Rate = snapshot.child("Book10").getValue().toString();
                        String saariBox = "Saari Boxes/Cards";
                        String saariBoxRate = "Rs.5/Kg";

                        bundle.putString("plasti", book );
                        bundle.putString("plasticR", bookRate);
                        bundle.putString("i5", book5 );
                        bundle.putString("i5R", book5Rate);
                        bundle.putString("i10", book10);
                        bundle.putString("i10R", book10Rate);
                        bundle.putString("bplastic", saariBox );
                        bundle.putString("bplasticRate", saariBoxRate );
                        BottomSheetPriceFragment fragInfo = new BottomSheetPriceFragment();
                        fragInfo.show(getActivity().getSupportFragmentManager(),fragInfo.getTag());
                        fragInfo.setArguments(bundle);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        steelPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Prices");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Bundle bundle = new Bundle();
                        String steel = "Steel";
                        String steelRate = snapshot.child("steel").getValue().toString();
                        String steel5 = "Steel 50Kg+";
                        String steel5Rate = snapshot.child("steel5").getValue().toString();
                        String steel10 = "Steel 100Kg+";
                        String steel10Rate = snapshot.child("steel10").getValue().toString();

                        bundle.putString("plasti", steel);
                        bundle.putString("plasticR", steelRate);
                        bundle.putString("i5", steel5 );
                        bundle.putString("i5R", steel5Rate);
                        bundle.putString("i10", steel10);
                        bundle.putString("i10R", steel10Rate);
                        BottomSheetPriceFragment fragInfo = new BottomSheetPriceFragment();
                        fragInfo.show(getActivity().getSupportFragmentManager(),fragInfo.getTag());
                        fragInfo.setArguments(bundle);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        ironPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Prices");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Bundle bundle = new Bundle();
                        String iron = "Iron";
                        String ironRate = snapshot.child("iron").getValue().toString();
                        String iron5 = "Iron 50Kg+";
                        String iron5Rate = snapshot.child("iron5").getValue().toString();
                        String iron10 = "Iron 100Kg+";
                        String iron10Rate = snapshot.child("iron10").getValue().toString();
                        String tin = "Tin Shades/Cooler Body/Drum";
                        String tinRate = "Rs.15/Kg";

                        bundle.putString("plasti", iron);
                        bundle.putString("plasticR", ironRate);
                        bundle.putString("i5", iron5 );
                        bundle.putString("i5R", iron5Rate);
                        bundle.putString("i10", iron10);
                        bundle.putString("i10R", iron10Rate);
                        bundle.putString("bplastic", tin );
                        bundle.putString("bplasticRate", tinRate );
                        BottomSheetPriceFragment fragInfo = new BottomSheetPriceFragment();
                        fragInfo.show(getActivity().getSupportFragmentManager(),fragInfo.getTag());
                        fragInfo.setArguments(bundle);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        alloysPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Prices");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Bundle bundle = new Bundle();
                        String iron = "Brass(पीतल)";
                        String ironRate = snapshot.child("alloBrass").getValue().toString();
                        String iron5 = "Copper";
                        String iron5Rate = snapshot.child("alloCopper").getValue().toString();
                        String iron10 = "Aluminium";
                        String iron10Rate = snapshot.child("alloAlum").getValue().toString();
//                        String tin = "Tin Shades/Cooler Body/Drum";
//                        String tinRate = "Rs.15/Kg";

                        bundle.putString("plasti", iron);
                        bundle.putString("plasticR", ironRate);
                        bundle.putString("i5", iron5 );
                        bundle.putString("i5R", iron5Rate);
                        bundle.putString("i10", iron10);
                        bundle.putString("i10R", iron10Rate);
//                        bundle.putString("bplastic", tin );
//                        bundle.putString("bplasticRate", tinRate );
                        BottomSheetPriceFragment fragInfo = new BottomSheetPriceFragment();
                        fragInfo.show(getActivity().getSupportFragmentManager(),fragInfo.getTag());
                        fragInfo.setArguments(bundle);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
////////////////////PRICE
////////////////////PRICE

        books = v.findViewById(R.id.boo);
        plastics = v.findViewById(R.id.plastics);
        steels = v.findViewById(R.id.steels);
        irons = v.findViewById(R.id.irons);
        bikes = v.findViewById(R.id.bikes);
        cars = v.findViewById(R.id.cars);
        electronics = v.findViewById(R.id.electronicsP);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Prices");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String elect = snapshot.child("Electronics").getValue().toString();
                electronics.setText(elect);

                String book = snapshot.child("Book").getValue().toString();
                books.setText(book);

                String plastic = snapshot.child("plastic").getValue().toString();
                plastics.setText(plastic);

                String steel = snapshot.child("steel").getValue().toString();
                steels.setText(steel);

                String iron = snapshot.child("iron").getValue().toString();
                irons.setText(iron);

                String bike = snapshot.child("Bike").getValue().toString();
                bikes.setText(bike);

                String car = snapshot.child("Car").getValue().toString();
                cars.setText(car);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
       }
}