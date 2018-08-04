package com.wegrzyn.marcin.fuelbills;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.icu.util.ULocale;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.wegrzyn.marcin.fuelbills.Utils.checkNum;

public class AddRefuelingActivity extends AppCompatActivity implements DatePickerFragment.OnDatePicListener {

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
    private int maxDistance;


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

        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        // TODO: 03.08.2018
        dist.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_NEXT){
                    Log.d(TAG, "action: next");
                }
                return false;
            }
        });

        price.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_NEXT){

                    String quantityString = quantity.getText().toString();
                    final float quantityNum = Float.parseFloat(checkNum(quantityString));

                    String priceString = price.getText().toString();
                    final double priceNum = Double.parseDouble(checkNum(priceString));

                    String calculatePrice = String.format(Locale.US,
                            "%.2f",priceNum*quantityNum);;

                    totalPrice.setText(calculatePrice);
                }
                return false;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(editInt!=-1)saveToDb(editInt);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    void saveToDb(int editInt){

        String tripDistString = tripDist.getText().toString();
        int tripDistNum = Integer.parseInt(checkNum(tripDistString));

        String distString = dist.getText().toString();
        int distNum = Integer.parseInt(checkNum(distString));

        String quantityString = quantity.getText().toString();
        final float quantityNum = Float.parseFloat(checkNum(quantityString));

        String priceString = price.getText().toString();
        final double priceNum = Double.parseDouble(checkNum(priceString));

        String totalPriceString = totalPrice.getText().toString();
        double totalPriceNum = Double.parseDouble(checkNum(totalPriceString));

        String noteString = note.getText().toString();

        float avg = getAvg(tripDistNum, quantityNum);
        if(Float.isNaN(avg))avg=0;

        if(editInt==-1){
            Refueling refueling = new Refueling(carId,currentDate
                    ,tripDistNum
                    ,distNum
                    ,quantityNum
                    ,priceNum
                    ,totalPriceNum
                    ,avg
                    ,noteString);
            carsViewModel.insertRefueling(refueling);
        }else {
            tempRefueling.setTripDist(tripDistNum);
            tempRefueling.setQuantity(quantityNum);
            tempRefueling.setPrice(priceNum);
            tempRefueling.setTotalPrice(totalPriceNum);
            tempRefueling.setDist(distNum);
            tempRefueling.setAvg(avg);
            tempRefueling.setNote(noteString);
            tempRefueling.setDate(currentDate);

            carsViewModel.updateRefueling(tempRefueling);
        }

    }

    private float getAvg(int tripDist, float quantity) {
        float avg = (quantity*100)/tripDist;
        Toast.makeText(getBaseContext(),"Your AVG is: "
                +String.format(Locale.US,"%.2f",avg)
                ,Toast.LENGTH_SHORT).show();
        return avg;
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

    @Override
    public void onDatePick(String date) {
        currentDate = date;
        dateTv.setText(currentDate);

    }
}
