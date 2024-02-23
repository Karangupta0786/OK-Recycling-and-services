package com.okjunkstore.beta.BuyFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.okjunkstore.beta.Helperclass.HomeAdapter.ItemsAdapter;
import com.okjunkstore.beta.Helperclass.HomeAdapter.ItemsData;
import com.okjunkstore.beta.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ElectronicsFragment extends Fragment {

    public ElectronicsFragment() {
        // Required empty public constructor
    }

    private RecyclerView electronics;
    private LinearLayout shimmerLinearLayoutE,electronicsNoData;
    private List<ItemsData> list1;
    private DatabaseReference reference,dbRef;

    ShimmerFrameLayout shimmerFrameLayoutElect;

    private ItemsAdapter itemsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_electronics, container, false);

        electronics = view.findViewById(R.id.electronics);

        shimmerFrameLayoutElect = view.findViewById(R.id.shimmer_view_container);
        shimmerLinearLayoutE = view.findViewById(R.id.shimmer_layout);
        electronicsNoData = view.findViewById(R.id.electronicsNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("Items");

        electronics();
//        otherUseful();

        return view;
    }
    private void electronics() {
        dbRef = reference.child("Electronics");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if (!snapshot.exists()){
                    shimmerLinearLayoutE.setVisibility(View.GONE);
                    electronicsNoData.setVisibility(View.VISIBLE);
                    electronics.setVisibility(View.GONE);
                }else{
                    shimmerLinearLayoutE.setVisibility(View.GONE);
                    electronicsNoData.setVisibility(View.GONE);
                    electronics.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        ItemsData data = snapshot1.getValue(ItemsData.class);
                        list1.add(data);
                    }

                    electronics.setHasFixedSize(true);
                    electronics.setLayoutManager(new LinearLayoutManager(getContext()));
                    itemsAdapter = new ItemsAdapter(list1,getContext(),"Electronics");
                    electronics.setAdapter(itemsAdapter);
                    shimmerFrameLayoutElect.stopShimmer();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onPause() {
        shimmerFrameLayoutElect.stopShimmer();
        super.onPause();
    }
    @Override
    public void onResume() {
        shimmerFrameLayoutElect.startShimmer();
        super.onResume();
    }
}