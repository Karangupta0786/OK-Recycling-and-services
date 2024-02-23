package com.okjunkstore.beta.Helperclass.HomeAdapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.okjunkstore.beta.ProductDetailActivity;
import com.okjunkstore.beta.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewAdapter> {

    private Context context;
    private List<ItemsData> list;
    String categoriesItem;

    public ItemsAdapter(List<ItemsData> list, Context context, String categoriesItem) {
        this.context = context;
        this.list = list;
        this.categoriesItem = categoriesItem;
    }

    @NonNull
    @Override
    public ItemsViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_layout, parent, false);
        return new ItemsViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewAdapter holder, int position) {
        ItemsData item = list.get(position);
//        holder.dat.setText(item.getDat());
//        holder.tim.setText(item.getTim());
        holder.itemName.setText(item.getItem());
        holder.stock.setText(item.getStock());
        holder.good.setText(item.getCondition());
        holder.aboutIt.setText(item.getAboutItem());

        try {
            if (item.getImage() != null)
                Picasso.get().load(item.getImage()).into(holder.itemImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:7905845935"));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                context.startActivity(intent);
            }
        });

        holder.ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.instagram.com/okjunkstore/");
            }
        });

     /*   holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("title",item.getItem());
                intent.putExtra("price",item.getAboutItem());
                intent.putExtra("cond",item.getCondition());
                intent.putExtra("stock",item.getStock());
                intent.putExtra("image",item.getImage());
                context.startActivity(intent);
            }
        });
*/
    }
    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        context.startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemsViewAdapter extends RecyclerView.ViewHolder {

        TextView dat, itemName, tim, stock, aboutIt,good;
        ImageView itemImage,buy,ig;


        public ItemsViewAdapter(@NonNull View itemView) {
            super(itemView);

            aboutIt = itemView.findViewById(R.id.aboutIt);
//            dat = itemView.findViewById(R.id.dat);
            buy = itemView.findViewById(R.id.buy);
            ig = itemView.findViewById(R.id.ig);
            itemName = itemView.findViewById(R.id.itemName);
            stock = itemView.findViewById(R.id.stock);
            itemImage = itemView.findViewById(R.id.itemImage);
            good = itemView.findViewById(R.id.good);
        }
    }
}
