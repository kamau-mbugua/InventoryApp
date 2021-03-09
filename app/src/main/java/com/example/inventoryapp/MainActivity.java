package com.example.inventoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;

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

        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Tie DrawerLayout events to the ActionBarToggle.
        drawerToggle = setupDrawerToggle();

        // Attach listener to drawer menuitems and handle user selections.
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        //View drawerHeader = nvDrawer.inflateHeaderView(R.layout.drawer_header);
        setupDrawerContent(nvDrawer);
        
        mDrawer.addDrawerListener(drawerToggle);*/


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

        FABOdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Comming Soon!!", Toast.LENGTH_SHORT).show();
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

    /*private void setupDrawerContent(NavigationView nvDrawer) {
        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return false;
            }
        });
    }*/

    /*private void selectDrawerItem(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_aboutus:
                Toast.makeText(this, "Implemented soon", Toast.LENGTH_SHORT).show();
               *//* startActivity(new Intent(getApplicationContext(),HistoryActivity.class));
                *//*finish();
                break;
            case R.id.nav_help:
                Toast.makeText(this, "Implemenetd soon", Toast.LENGTH_SHORT).show();
               *//* startActivity(new Intent(getApplicationContext(),AboutUsActivity.class));
                *//*finish();
                break;
            default:
        }
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_aboutus:
                Toast.makeText(this, "Implemented soon", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), About.class));
                finish();
                break;
            case R.id.nav_help:
                Toast.makeText(this, "Implemenetd soon", Toast.LENGTH_SHORT).show();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);

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
