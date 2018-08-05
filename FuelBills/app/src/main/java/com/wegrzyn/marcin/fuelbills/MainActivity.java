package com.wegrzyn.marcin.fuelbills;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListItemClickListener {

    public static final String EDIT = "edit";
    public static final String CAR_ID = "car_id";
    private CarsViewModel carsViewModel;
    private CarsAdapter carsAdapter;

    private List<Car> mCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Car List");
        }

        AdView adView = findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        RecyclerView recyclerView = findViewById(R.id.car_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(this, getResources().getInteger(R.integer.grid_size));
        recyclerView.setLayoutManager(mLayoutManager);

        carsAdapter = new CarsAdapter(this, this);
        recyclerView.setAdapter(carsAdapter);


        carsViewModel = ViewModelProviders.of(this).get(CarsViewModel.class);
        carsViewModel.getAllCars().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(@Nullable List<Car> cars) {
                mCars = cars;
                carsAdapter.setCars(mCars);
            }
        });
        carsViewModel.getAllRefillings().observe(this, new Observer<List<Refueling>>() {
            @Override
            public void onChanged(@Nullable List<Refueling> refuelings) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.add_car_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCar();
            }
        });
    }

    @Override
    public void onItemClick(int itemIndex) {
        Intent intent = new Intent(MainActivity.this,RefuelingActivity.class);
        intent.putExtra(CAR_ID,mCars.get(itemIndex).getCarId());
        startActivity(intent);
    }

    @Override
    public void onItemDelete(int itemDelete) {
      carsViewModel.deleteCar(mCars.get(itemDelete));
    }

    @Override
    public void editItemData(int id) {
        Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
        intent.putExtra(EDIT,id);
        startActivity(intent);
    }

    private void addCar() {
        Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
        intent.putExtra(EDIT,-1);
        startActivity(intent);
    }
}
