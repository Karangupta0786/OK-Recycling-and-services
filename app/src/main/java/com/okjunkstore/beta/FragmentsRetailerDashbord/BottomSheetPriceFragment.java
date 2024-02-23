package com.okjunkstore.beta.FragmentsRetailerDashbord;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.okjunkstore.beta.R;

public class BottomSheetPriceFragment extends BottomSheetDialogFragment {


    public BottomSheetPriceFragment() {
        // Required empty public constructor
    }

    TextView tv1, tv1r, tv2, tv2r, tv3, tv3r, tv4, tv4r, tv5, tv5r;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_price, container, false);

        tv1 = view.findViewById(R.id.tv1);
        tv1r = view.findViewById(R.id.tv1r);
        tv2 = view.findViewById(R.id.tv2);
        tv2r = view.findViewById(R.id.tv2r);
        tv3 = view.findViewById(R.id.tv3);
        tv3r = view.findViewById(R.id.tv3r);
        tv4 = view.findViewById(R.id.tv4);
        tv4r = view.findViewById(R.id.tv4r);
        tv5 = view.findViewById(R.id.tv5);
        tv5r = view.findViewById(R.id.tv5r);


        String tv1S = this.getArguments().getString("plasti");
        String tv1rS = this.getArguments().getString("plasticR");
        String tv2S = this.getArguments().getString("i5");
        String tv2rS = this.getArguments().getString("i5R");
        String tv3S = this.getArguments().getString("i10");
        String tv3rS = this.getArguments().getString("i10R");
        String tv4S = this.getArguments().getString("bplastic");
        String tv4rS = this.getArguments().getString("bplasticRate");
        String tv5S = this.getArguments().getString("bplasti");
        String tv5rS = this.getArguments().getString("bplasticRat");

        tv1.setText(tv1S);
        tv1r.setText(tv1rS);
        tv2.setText(tv2S);
        tv2r.setText(tv2rS);
        tv3.setText(tv3S);
        tv3r.setText(tv3rS);
        tv4.setText(tv4S);
        tv4r.setText(tv4rS);
        return  view;
    }
}