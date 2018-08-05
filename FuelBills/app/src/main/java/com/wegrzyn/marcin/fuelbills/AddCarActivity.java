package com.wegrzyn.marcin.fuelbills;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static com.wegrzyn.marcin.fuelbills.Utils.checkNum;

public class AddCarActivity extends AppCompatActivity {

    private static final String TAG = AddCarActivity.class.getSimpleName() ;
    private CarsViewModel carsViewModel;

    private EditText nameEt;
    private EditText vinEt;
    private EditText plateEt;
    private EditText tankEt;
    private Spinner fuelSpinner;

    private int fuelType;
    private int editInt;
    private Car tempCar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        setViews();

        fuelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,String.valueOf(position));
                fuelType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        carsViewModel = ViewModelProviders.of(this).get(CarsViewModel.class);

        if(getIntent().hasExtra(MainActivity.EDIT)){
            editInt = getIntent().getIntExtra(MainActivity.EDIT,-1);
        }
        if(editInt >-1){

            carsViewModel.getCar(editInt).observe(this, new Observer<Car>() {
                @Override
                public void onChanged(@Nullable Car car) {
                    tempCar = car;
                    nameEt.setText(car.getName());
                    vinEt.setText(car.getVin());
                    plateEt.setText(car.getPlate());
                    tankEt.setText(String.valueOf(car.getTankSize()));
                    fuelSpinner.setSelection(car.getFuelType());
                }
            });

        }

        setActionBarTitle();
    }

    private void setActionBarTitle() {
        if (getSupportActionBar() != null) {
            if(editInt==-1)
                getSupportActionBar().setTitle("Add Car");
            else
                getSupportActionBar().setTitle("Edit Car");
        }
    }

    private void setViews() {
        nameEt = findViewById(R.id.name_et);
        vinEt = findViewById(R.id.vin_et);
        plateEt = findViewById(R.id.plate_et);
        tankEt = findViewById(R.id.tank_size_et);
        fuelSpinner = findViewById(R.id.fuel_spinner);
    }

    private void saveToDb(int editInt){
        String name = nameEt.getText().toString();
        String vin = vinEt.getText().toString();
        String plate = plateEt.getText().toString();
        String tank = tankEt.getText().toString();

        if(editInt==-1){
            carsViewModel.insertCar(new Car(name,vin,fuelType,plate,Integer.parseInt(checkNum(tank))));
        }else {
            tempCar.setName(name);
            tempCar.setVin(vin);
            tempCar.setPlate(plate);
            tempCar.setFuelType(fuelType);
            tempCar.setTankSize(Integer.parseInt(checkNum(tank)));
            carsViewModel.updateCar(tempCar);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(editInt!=-1)saveToDb(editInt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_activity_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item_menu:
                saveToDb(editInt);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
