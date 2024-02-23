package com.okjunkstore.beta.Helperclass.HomeAdapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.okjunkstore.beta.R;

import java.util.ArrayList;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.AdapterAllSlotsViewHolder> {

    ArrayList<slotHelperClass> slotTiming;

    int selectedItem = -1;
    public SlotAdapter(ArrayList<slotHelperClass> slotTiming) {

        this.slotTiming = slotTiming;
    }

    @NonNull
    @Override
    public AdapterAllSlotsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slot, parent, false);
        AdapterAllSlotsViewHolder lvh = new AdapterAllSlotsViewHolder(view);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAllSlotsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        slotHelperClass helperClass = slotTiming.get(position);
        holder.textView.setText(helperClass.getSlot());
        String tv = helperClass.getSlot();
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), tv, Toast.LENGTH_SHORT).show();
                holder.itemView.getElevation();

                selectedItem = position;
                notifyDataSetChanged();
            }
        });
        if (selectedItem == position){
            holder.textView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rectangle_fill));
            holder.textView.setTextColor(Color.WHITE);
        }else {
            holder.textView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rectangle_outline));
            holder.textView.setTextColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return slotTiming.size();
    }

    public class AdapterAllSlotsViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public AdapterAllSlotsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}
