package com.wegrzyn.marcin.fuelbills;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    private int carId;

    private LineChart avgLineChart;
    private LineChart distLineChart;

    private List<Refueling> refuelingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        avgLineChart = findViewById(R.id.avg_chart);
        distLineChart = findViewById(R.id.dist_chart);


        if(getIntent().hasExtra(RefuelingActivity.CAR_ID)){
            carId = getIntent().getIntExtra(RefuelingActivity.CAR_ID,-1);
        }

        CarsViewModel carsViewModel = ViewModelProviders.of(this).get(CarsViewModel.class);

        carsViewModel.getRefuelingById(carId).observe(this, new Observer<List<Refueling>>() {
            @Override
            public void onChanged(@Nullable List<Refueling> refuelings) {
                refuelingList = refuelings;

                if(refuelings!= null&&refuelings.size()>0)showChart();
            }
        });

    }

    private void showChart() {

        int size = refuelingList.size();
        List<Entry> entriesAvg = new ArrayList<>();
        List<Entry> entriesDist = new ArrayList<>();
        List<Entry> entriesPrice = new ArrayList<>();


        final String[] dates = new String[size];
        for (int i = 0; i<size; i++){
            float avg = refuelingList.get(i).getAvg();
            if(Float.isNaN(avg))avg=0;
            entriesAvg.add(new Entry(i,avg));
            entriesDist.add(new Entry(i,refuelingList.get(i).getDist()));
            entriesPrice.add(new Entry(i, (float) refuelingList.get(i).getPrice()));
            dates[i]= refuelingList.get(i).getDate();
        }

        LineDataSet dataSetAvg = new LineDataSet(entriesAvg,"avg");
        LineDataSet dataSetDist = new LineDataSet(entriesDist,"distance");
        LineDataSet dataSetPrice = new LineDataSet(entriesPrice,"price");

        dataSetAvg.setColor(getResources().getColor(R.color.red_800));
        dataSetAvg.setCircleColor(getResources().getColor(R.color.red_800));

        dataSetPrice.setColor(getResources().getColor(R.color.indigo_800));
        dataSetPrice.setCircleColor(getResources().getColor(R.color.indigo_800));

        dataSetDist.setColor(getResources().getColor(R.color.green_800));
        dataSetDist.setCircleColor(getResources().getColor(R.color.green_800));
        

        List<ILineDataSet> avgDataSets = new ArrayList<>();
        List<ILineDataSet> distDataSets = new ArrayList<>();

        avgDataSets.add(dataSetAvg);
        avgDataSets.add(dataSetPrice);
        distDataSets.add(dataSetDist);


        LineData avgLineData = new LineData(avgDataSets);
        LineData distLineData = new LineData(distDataSets);



        avgLineChart.setData(avgLineData);
        avgLineChart.setVisibleXRangeMaximum(getResources().getInteger(R.integer.chart_range));
        avgLineChart.invalidate();

        distLineChart.setData(distLineData);
        distLineChart.setVisibleXRangeMaximum(getResources().getInteger(R.integer.chart_range));
        distLineChart.invalidate();

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return dates[(int) value];
            }
        };

        XAxis avgXAxis = avgLineChart.getXAxis();
        XAxis distXAxis = distLineChart.getXAxis();

        avgXAxis.setGranularity(2f);
        distXAxis.setGranularity(2f);
        avgXAxis.setValueFormatter(formatter);
        distXAxis.setValueFormatter(formatter);
        Description avgDescription = avgLineChart.getDescription();
        avgDescription.setEnabled(false);
        Description distDescription = distLineChart.getDescription();
        distDescription.setEnabled(false);

    }
}
