package com.example.inventoryapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inventoryapp.R;
import com.example.inventoryapp.Utils;
import com.example.inventoryapp.Product;
import com.example.inventoryapp.Warehouse;
import com.example.inventoryapp.ProductActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ProductHolder> {
    static class ProductHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private TextView price;
        private TextView quantity;
        private Button sellButton;
        private int imageWidth;
        private int imageHeight;

        ProductHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.product_image_view);
            image.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    image.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    imageWidth = image.getMeasuredWidth();
                    imageHeight = image.getMeasuredHeight();
                }
            });

            title = itemView.findViewById(R.id.list_item_product_title);
            price = itemView.findViewById(R.id.list_item_product_price);
            quantity = itemView.findViewById(R.id.list_item_product_quantity);
            sellButton = itemView.findViewById(R.id.list_item_sell_button);
        }

        void bindData(@Nullable Bitmap photo, String productTitle, String productPrice, String productQuantity) {
            if (photo != null) {
                image.setImageBitmap(photo);
            } else {
                image.setImageResource(R.drawable.no_prod_img);
            }

            title.setText(productTitle);
            quantity.setText(productQuantity);

            price.setText(itemView.getContext().getString(R.string.list_item_price_label));
            price.append(productPrice);

        }
    }

    private List<Product> products;
    private Context context;


    public CatalogAdapter(Context context, @Nullable List<Product> products) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = products.get(position);

        File photoFile = Warehouse.getInstance(context).getPhotoFile(product);
        Bitmap photo = null;
        if (photoFile != null && photoFile.exists()) {
            photo = Utils.getScaledBitmap(photoFile.getPath(), holder.imageWidth, holder.imageHeight);
        }

        holder.bindData(photo, product.getTitle(), product.getPrice().toString(), Integer.toString(product.getQuantity()));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = ProductActivity.newIntent(context, product.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    public void setData(@Nullable List<Product> products) {
        this.products = products;
    }
}
