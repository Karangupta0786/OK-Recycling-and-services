package com.okjunkstore.beta.Helperclass.HomeAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.okjunkstore.beta.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OwnerOrderAdapter extends RecyclerView.Adapter<OwnerOrderAdapter.OwnerOrderViewAdapter> {

    private Context context;
    private List<ownerOrderData> list;

    public OwnerOrderAdapter(List<ownerOrderData> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public OwnerOrderViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_order_layout,parent,false);
        return new OwnerOrderViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerOrderViewAdapter holder, int position) {

        ownerOrderData item = list.get(position);
        holder.ownerName.setText(item.getOwner());
        holder.orderDate.setText(item.getDate());
        holder.orderTime.setText(item.getTime());
        try {
            if(item.getImage() != null)
            Picasso.get().load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

      /*  holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Update Teacher", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OwnerOrderViewAdapter extends RecyclerView.ViewHolder {

         TextView orderDate, ownerName, orderTime;
//         Button update;
         ImageView imageView;

        public OwnerOrderViewAdapter(@NonNull View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderTime = itemView.findViewById(R.id.orderTime);
            ownerName = itemView.findViewById(R.id.ownerName);
//            update = itemView.findViewById(R.id.teacherUpdate);
            imageView = itemView.findViewById(R.id.ownerImage);
        }
    }

}
