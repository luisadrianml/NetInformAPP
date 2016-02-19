package com.commovil.luisadrianml.networkinformationapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        int gsm_signal =  getIntent().getExtras().getInt(MainActivity.GSM_SIGNAL);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(40f, 0));
        entries.add(new BarEntry(60f, 1));
        entries.add(new BarEntry(80f, 2));
        entries.add(new BarEntry(120f, 3));


        BarDataSet dataset = new BarDataSet(entries, getApplicationContext().getResources().getString(R.string.signal_stre)+gsm_signal);
        gsm_signal = gsm_signal*-1;
        ArrayList<String> labels = new ArrayList<String>();
        labels.add(getApplicationContext().getResources().getString(R.string.min));
        labels.add(getApplicationContext().getResources().getString(R.string.normal));
        labels.add(getApplicationContext().getResources().getString(R.string.good));
        labels.add(getApplicationContext().getResources().getString(R.string.perfect));

        int[] colors;
        if (gsm_signal<= 70) {
            colors = new int[]{Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE};
        } else if (gsm_signal<=80) {
            colors = new int[]{Color.BLUE, Color.BLUE, Color.BLUE, Color.WHITE};
        } else if(gsm_signal<=90) {
            colors = new int[]{Color.BLUE, Color.BLUE, Color.WHITE, Color.WHITE};
        } else  {
            colors = new int[]{Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE};
        }

        dataset.setColors(colors);
        dataset.setDrawValues(false);

        BarChart chart = new BarChart(this.getBaseContext());

        setContentView(chart);

        YAxis rightYA = chart.getAxis(YAxis.AxisDependency.RIGHT);

        rightYA.setAxisMaxValue(120);
        rightYA.setAxisMinValue(1);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);
        chart.setDescription(getApplicationContext().getResources().getString(R.string.dbValue));
    }
}
