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

public class OtherItemsFragment extends Fragment {

    public OtherItemsFragment() {
        // Required empty public constructor
    }

    private RecyclerView otherUseful;
    private LinearLayout shimmerLinearLayoutO,otherNoData;
    private List<ItemsData> list2;
    private DatabaseReference reference,dbRef;

    ShimmerFrameLayout shimmerFrameLayoutOther;

    private ItemsAdapter itemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_items, container, false);

        otherUseful = view.findViewById(R.id.otherUseful);

        shimmerFrameLayoutOther = view.findViewById(R.id.shimmer_view_container_other);
        shimmerLinearLayoutO = view.findViewById(R.id.shimmer_layout_other);
        otherNoData = view.findViewById(R.id.otherUsefulNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("Items");

        otherUseful();
        return view;
    }
    private void otherUseful() {
        dbRef = reference.child("Other Useful Items");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if (!snapshot.exists()){
                    shimmerLinearLayoutO.setVisibility(View.GONE);
                    otherNoData.setVisibility(View.VISIBLE);
                    otherUseful.setVisibility(View.GONE);
                }else{
                    shimmerLinearLayoutO.setVisibility(View.GONE);
                    otherNoData.setVisibility(View.GONE);
                    otherUseful.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        ItemsData data = snapshot1.getValue(ItemsData.class);
                        list2.add(data);
                    }
                    otherUseful.setHasFixedSize(true);
                    otherUseful.setLayoutManager(new LinearLayoutManager(getContext()));
                    itemsAdapter = new ItemsAdapter(list2,getContext(),"Other Useful Items");
                    otherUseful.setAdapter(itemsAdapter);
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
        shimmerFrameLayoutOther.stopShimmer();
        super.onPause();
    }
    @Override
    public void onResume() {
        shimmerFrameLayoutOther.startShimmer();
        super.onResume();
    }
}