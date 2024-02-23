package com.okjunkstore.beta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.okjunkstore.beta.Helperclass.HomeAdapter.ProductImageAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    ViewPager productImageViewPager;
    TabLayout viewPagerIndicator;
    TextView title,price,condition,stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productImageViewPager = findViewById(R.id.product_images);
        viewPagerIndicator = findViewById(R.id.viewpager_indicator);

        title = findViewById(R.id.product_title);
        price = findViewById(R.id.product_price);
        condition = findViewById(R.id.condition_item);
        stock = findViewById(R.id.item_stock);
        String productTitle = getIntent().getStringExtra("title");
        title.setText(productTitle);
        String productPrice = getIntent().getStringExtra("price");
        price.setText(productPrice);
        String productCondition = getIntent().getStringExtra("cond");
        condition.setText(productCondition);
        String productStock = getIntent().getStringExtra("stock");
        stock.setText(productStock);
        String productImage = getIntent().getStringExtra("image");

        List<Integer> productImages = new ArrayList<>();
        productImages.add(R.drawable.washingmachine);
        productImages.add(R.drawable.apple);
        productImages.add(R.drawable.okt);
        ProductImageAdapter productImageAdapter = new ProductImageAdapter(productImages);
        productImageViewPager.setAdapter(productImageAdapter);

        viewPagerIndicator.setupWithViewPager(productImageViewPager,true);
    }
}