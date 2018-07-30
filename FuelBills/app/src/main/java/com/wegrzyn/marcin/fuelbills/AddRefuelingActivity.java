package com.wegrzyn.marcin.fuelbills;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.wegrzyn.marcin.fuelbills.Utils.checkNum;

public class AddRefuelingActivity extends AppCompatActivity {

    private static final String TAG =AddRefuelingActivity.class.getSimpleName() ;
    private CarsViewModel carsViewModel;
    private int editInt;
    private int carId;

    private String currentDate;

    private TextView dateTv;
    private EditText tripDist;
    private EditText dist;
    private EditText quantity;
    private EditText price;
    private EditText totalPrice;
    private EditText note;

    private Refueling tempRefueling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_refueling);

        setView();

        carsViewModel = ViewModelProviders.of(this).get(CarsViewModel.class);

        if(getIntent().hasExtra(RefuelingActivity.REFUELING_ID)){
            editInt = getIntent().getIntExtra(RefuelingActivity.REFUELING_ID,-1);
        }
        if(getIntent().hasExtra(RefuelingActivity.CAR_ID)){
            carId = getIntent().getIntExtra(RefuelingActivity.CAR_ID,-1);
        }

        Date dateNow = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = dateFormat.format(dateNow);
        dateTv.setText(currentDate);

        if(editInt >-1){
            carsViewModel.getRefueling(editInt).observe(this, new Observer<Refueling>() {
                @Override
                public void onChanged(@Nullable Refueling refueling) {
                    tempRefueling = refueling;
                    dateTv.setText(refueling.getDate());
                    tripDist.setText(String.valueOf(refueling.getTripDist()));
                    dist.setText(String.valueOf(refueling.getDist()));
                    quantity.setText(String.valueOf(refueling.getQuantity()));
                    price.setText(String.valueOf(refueling.getPrice()));
                    totalPrice.setText(String.valueOf(refueling.getTotalPrice()));
                    note.setText(refueling.getNote());
                }
            });
        }
        setActionBarTitle();
    }

    private void setActionBarTitle() {
        if (getSupportActionBar() != null) {
            if(editInt==-1)
                getSupportActionBar().setTitle(R.string.add_refueling);
            else
                getSupportActionBar().setTitle(R.string.edit_refueling);
        }
    }

    private void setView() {
        dateTv = findViewById(R.id.date_ref_tv);
        tripDist = findViewById(R.id.trip_ref_dist_et);
        dist = findViewById(R.id.total_dist_et);
        quantity = findViewById(R.id.quantity_et);
        price = findViewById(R.id.price_et);
        totalPrice = findViewById(R.id.total_price_et);
        note = findViewById(R.id.note_et);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(editInt!=-1)saveToDb(editInt);
    }

    void saveToDb(int editInt){
// TODO: 30.07.2018 tutaj mozna zabezpieczyc przed pustymi ciagami

        String tripDistString = tripDist.getText().toString();
        String distString = dist.getText().toString();
        String quantityString = quantity.getText().toString();
        String priceString = price.getText().toString();
        String totalPriceString = totalPrice.getText().toString();
        String noteString = note.getText().toString();

        if(editInt==-1){
            carsViewModel.insertRefueling(new Refueling(carId,currentDate
                    ,Integer.parseInt(checkNum(tripDistString))
                    ,Float.parseFloat(checkNum(quantityString))
                    ,Double.parseDouble(checkNum(priceString))
                    ,Double.parseDouble(checkNum(totalPriceString))
                    ,noteString));
        }else {
            tempRefueling.setTripDist(Integer.parseInt(checkNum(tripDistString)));
            tempRefueling.setQuantity(Float.parseFloat(checkNum(quantityString)));
            tempRefueling.setPrice(Double.parseDouble(checkNum(priceString)));
            tempRefueling.setTotalPrice(Double.parseDouble(checkNum(totalPriceString)));
            tempRefueling.setNote(noteString);

            carsViewModel.updateRefueling(tempRefueling);
        }

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
