package com.wegrzyn.marcin.fuelbills;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.wegrzyn.marcin.fuelbills.databinding.ActivityAddCarBinding;


public class AddCarActivity extends AppCompatActivity  {

    private CarsViewModel carsViewModel;

    private ActivityAddCarBinding addCarBinding;

    private int fuelType;
    private int editInt;
    private Car tempCar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addCarBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_car);

        addCarBinding.fuelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
                    addCarBinding.nameEt.setText(car.getName());
                    addCarBinding.vinEt.setText(car.getVin());
                    addCarBinding.plateEt.setText(car.getPlate());
                    addCarBinding.fuelSpinner.setSelection(car.getFuelType());
                }
            });

        }

        setActionBarTitle();
    }

    private void setActionBarTitle() {
        if (getSupportActionBar() != null) {
            if(editInt==-1)
                getSupportActionBar().setTitle(getString(R.string.add_car));
            else
                getSupportActionBar().setTitle(R.string.edit_car);
        }
    }
    private void saveToDb(int editInt){
        String name = addCarBinding.nameEt.getText().toString();
        String vin = addCarBinding.vinEt.getText().toString();
        String plate = addCarBinding.plateEt.getText().toString();

        if(editInt==-1){
            carsViewModel.insertCar(new Car(name,vin,fuelType,plate));
        }else {
            tempCar.setName(name);
            tempCar.setVin(vin);
            tempCar.setPlate(plate);
            tempCar.setFuelType(fuelType);
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
