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
import android.widget.Toast;

import java.util.List;

public class RefuelingActivity extends AppCompatActivity implements ListItemClickListener {

    private static final String TAG = RefuelingActivity.class.getSimpleName();
    public static final String REFUELING_ID = "refueling_id";
    public static final String CAR_ID = "car_id";
    private int carId;
    private RefuelingAdapter refuelingAdapter;
    private CarsViewModel carsViewModel;
    private List<Refueling> refuelingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refueling);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.refueling_list);
        }

        if(getIntent().hasExtra(MainActivity.CAR_ID)){
            carId= getIntent().getIntExtra(MainActivity.CAR_ID,-1);
            Log.d(TAG,"id: "+String.valueOf(carId));
        }

        RecyclerView recyclerView = findViewById(R.id.refueling_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);

        refuelingAdapter = new RefuelingAdapter(this,this);
        recyclerView.setAdapter(refuelingAdapter);

        carsViewModel = ViewModelProviders.of(this).get(CarsViewModel.class);

        carsViewModel.getRefuelingById(carId).observe(this, new Observer<List<Refueling>>() {
            @Override
            public void onChanged(@Nullable List<Refueling> refuelling) {
                refuelingList = refuelling;
                refuelingAdapter.setRefuelingList(refuelling);
                Log.d(TAG,"list size: "+ String.valueOf(refuelling.size()));
            }
        });

        FloatingActionButton fab = findViewById(R.id.add_refueling_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(RefuelingActivity.this,AddRefuelingActivity.class);
               intent.putExtra(REFUELING_ID,-1);
               intent.putExtra(CAR_ID,carId);
               startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(int itemIndex) {
        Log.d(TAG,"item click: "+String.valueOf(itemIndex));
    }

    @Override
    public void onItemDelete(int itemDelete) {
        Log.d(TAG,"item delete: "+String.valueOf(itemDelete));
        carsViewModel.deleteRefueling(refuelingList.get(itemDelete));
    }

    @Override
    public void editItemData(int id) {
        Log.d(TAG,"item edit"+String.valueOf(id));
        Intent intent = new Intent(RefuelingActivity.this, AddRefuelingActivity.class);
        intent.putExtra(REFUELING_ID,id);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refueling_activity_menu, menu);
        return true; }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_chart_activity_item_menu:
                Intent intent = new Intent(RefuelingActivity.this, ChartActivity.class);
                intent.putExtra(CAR_ID,carId);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
