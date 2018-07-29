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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CarsAdapter.ListItemClickListener {

    public static final String EDIT = "edit";
    private CarsViewModel carsViewModel;
    private CarsAdapter carsAdapter;

    private List<Car> mCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.car_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(this, 1);
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
    }

    @Override
    public void onItemClick(int itemIndex) {
        Toast.makeText(this,String.valueOf(itemIndex),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemDelete(int itemDelete) {
      carsViewModel.deleteCar(mCars.get(itemDelete));
    }

    @Override
    public void editCarData(int id) {

        Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
        intent.putExtra(EDIT,id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activiry_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_car_item_menu:
                Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
                intent.putExtra(EDIT,-1);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
