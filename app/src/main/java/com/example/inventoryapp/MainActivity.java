package com.example.inventoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CatalogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Product product = new Product();
            Warehouse.getInstance(this).addProduct(product);
            Intent intent = ProductActivity.newIntent(this, product.getId());
            startActivity(intent);
        });



        // Find the ListView which will be populated with the pet data
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        // Setup an Adapter to create a list item for each row of product data in the Cursor.
        Warehouse warehouse = Warehouse.getInstance(this);
        List<Product> products = warehouse.getProducts();

        adapter = new CatalogAdapter(this, products);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        Warehouse warehouse = Warehouse.getInstance(this);
        List<Product> products = warehouse.getProducts();

        if (adapter == null) {
            adapter = new CatalogAdapter(this, products);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setData(products);
            adapter.notifyDataSetChanged();
        }
        super.onResume();
    }
}
