package com.example.inventoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CatalogAdapter adapter;
    private Animation mFabOpen, mFabClose;

    FloatingActionButton fab, FABOdb;
    TextView randomText,randomText1;

    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        mFabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);


        FABOdb = findViewById(R.id.FABOdb);
        randomText= findViewById(R.id.randomText);
        randomText1= findViewById(R.id.randomText1);
        FloatingActionButton fMain = findViewById(R.id.main_fab);
        fMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen){
                    FABOdb.setAnimation(mFabClose);
                    fab.setAnimation(mFabClose);

                    randomText.setVisibility(View.INVISIBLE);
                    randomText1.setVisibility(View.INVISIBLE);

                    isOpen = false;
                }

                else {
                    FABOdb.setAnimation(mFabOpen);
                    fab.setAnimation(mFabOpen);

                    randomText.setVisibility(View.VISIBLE);
                    randomText1.setVisibility(View.VISIBLE);

                    isOpen = true;

                }

            }
        });

        fab = findViewById(R.id.fab);
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
