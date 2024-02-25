package com.okjunkstore.beta.dashboard.buy.BuyFragments;

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

public class BookFragment extends Fragment {


    public BookFragment() {
        // Required empty public constructor
    }

    private RecyclerView bok;
    private LinearLayout shimmerLinearLayoutBooks,booksNoData;
    private List<ItemsData> list3;
    private DatabaseReference reference,dbRef;

    ShimmerFrameLayout shimmerFrameLayoutBooks;

    private ItemsAdapter itemsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        bok = view.findViewById(R.id.goodBooks);

        shimmerFrameLayoutBooks = view.findViewById(R.id.shimmer_view_container_books);
        shimmerLinearLayoutBooks = view.findViewById(R.id.shimmer_layout_books);
        booksNoData = view.findViewById(R.id.booksNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("Items");

        bok();
        return view;
    }
    private void bok() {
        dbRef = reference.child("Good Books");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if (!snapshot.exists()){
                    shimmerLinearLayoutBooks.setVisibility(View.GONE);
                    booksNoData.setVisibility(View.VISIBLE);
                    bok.setVisibility(View.GONE);
                }else{
                    shimmerLinearLayoutBooks.setVisibility(View.GONE);
                    booksNoData.setVisibility(View.GONE);
                    bok.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        ItemsData data = snapshot1.getValue(ItemsData.class);
                        list3.add(data);
                    }
                    bok.setHasFixedSize(true);
                    bok.setLayoutManager(new LinearLayoutManager(getContext()));
                    itemsAdapter = new ItemsAdapter(list3,getContext(),"Good Books");
                    bok.setAdapter(itemsAdapter);
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
        shimmerFrameLayoutBooks.stopShimmer();
        super.onPause();
    }
    @Override
    public void onResume() {
        shimmerFrameLayoutBooks.startShimmer();
        super.onResume();
    }
}