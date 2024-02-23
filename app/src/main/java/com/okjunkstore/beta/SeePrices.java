package com.okjunkstore.beta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SeePrices extends AppCompatActivity {
    TextView washingMachine,washingMachine2,washingMachine5,fridge,fridge2,fridge5,books,books5,books10,plastics,plastics5,plastics10,steels,steels5,steels10,irons,irons5,irons10,al,cu,brass;
    TextView ac,ac2,ac22,fan,fan2,fan5;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_prices);

        ac = findViewById(R.id.ac_1_ton);
        ac2 = findViewById(R.id.ac_2_ton);
        ac22 = findViewById(R.id.ac_2_ton_2);

        fan = findViewById(R.id.fan);
        fan2 = findViewById(R.id.fan2);
        fan5 = findViewById(R.id.fan5);

        washingMachine = findViewById(R.id.wm);
        washingMachine2 = findViewById(R.id.wm2);
        washingMachine5 = findViewById(R.id.wm5);

        fridge = findViewById(R.id.fridge);
        fridge2 = findViewById(R.id.fridge2);
        fridge5 = findViewById(R.id.fridge5);

        books = findViewById(R.id.books);
        books5 = findViewById(R.id.books50);
        books10 = findViewById(R.id.books10);

        plastics = findViewById(R.id.plastic);
        plastics5 = findViewById(R.id.plastic50);
        plastics10 = findViewById(R.id.plastic10);

        steels = findViewById(R.id.steel_d);
        steels5 = findViewById(R.id.steel_d50);
        steels10 = findViewById(R.id.steel_d10);

        irons = findViewById(R.id.iron_d);
        irons5 = findViewById(R.id.iron_d50);
        irons10 = findViewById(R.id.iron_d10);

        al = findViewById(R.id.al);
        cu = findViewById(R.id.cu_d);
        brass = findViewById(R.id.brass);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Prices");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                           /*Books*/
                String book = snapshot.child("Book").getValue().toString();
                books.setText(book);
                String book50 = snapshot.child("Book5").getValue().toString();
                books5.setText(book50);
                String book100 = snapshot.child("Book10").getValue().toString();
                books10.setText(book100);
                                           /*FAN*/
                String fan1p = snapshot.child("ElecFan").getValue().toString();
                fan.setText(fan1p);
                String fan2p = snapshot.child("ElecFan2").getValue().toString();
                fan2.setText(fan2p);
                String fan5p = snapshot.child("ElecFan5").getValue().toString();
                fan5.setText(fan5p);
                                           /*A.C.*/
                String ac1ton = snapshot.child("ElecAC").getValue().toString();
                ac.setText(ac1ton);
                String ac2ton = snapshot.child("ElecAC2").getValue().toString();
                ac2.setText(ac2ton);
                String ac2ton2 = snapshot.child("ElecAC22").getValue().toString();
                ac22.setText(ac2ton2);
                                        /*WashingMachine*/
                String washing = snapshot.child("ElecWashing").getValue().toString();
                washingMachine.setText(washing);
                String washing2 = snapshot.child("ElecWashing2").getValue().toString();
                washingMachine2.setText(washing2);
                String washing5 = snapshot.child("ElecWashing5").getValue().toString();
                washingMachine5.setText(washing5);
                                        /*Refrigerator*/
                String refri = snapshot.child("ElecFridge").getValue().toString();
                fridge.setText(refri);
                String refri2 = snapshot.child("ElecFridge2").getValue().toString();
                fridge2.setText(refri2);
                String refri5 = snapshot.child("ElecFridge5").getValue().toString();
                fridge5.setText(refri5);
                                             /*Plastic*/
                String plastic = snapshot.child("plastic").getValue().toString();
                plastics.setText(plastic);
                String plastic5 = snapshot.child("plastic5").getValue().toString();
                plastics5.setText(plastic5);
                String plastic100 = snapshot.child("plastic10").getValue().toString();
                plastics10.setText(plastic100);
                                              /*Steel*/
                String steel = snapshot.child("steel").getValue().toString();
                steels.setText(steel);
                String steel50 = snapshot.child("steel5").getValue().toString();
                steels5.setText(steel50);
                String steel100 = snapshot.child("steel10").getValue().toString();
                steels10.setText(steel100);
                                            /*IRON*/
                String iron = snapshot.child("iron").getValue().toString();
                irons.setText(iron);
                String iron50 = snapshot.child("iron5").getValue().toString();
                irons5.setText(iron50);
                String iron100 = snapshot.child("iron10").getValue().toString();
                irons10.setText(iron100);
                                           /*Alloys*/
                String aluminim = snapshot.child("alloAlum").getValue().toString();
                al.setText(aluminim);
                String copper = snapshot.child("alloCopper").getValue().toString();
                cu.setText(copper);
                String bras = snapshot.child("alloBrass").getValue().toString();
                brass.setText(bras);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SeePrices.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}