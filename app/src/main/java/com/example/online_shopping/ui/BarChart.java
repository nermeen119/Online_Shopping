package com.example.online_shopping.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.online_shopping.R;
import com.example.online_shopping.database.AllProductDatabase;
import com.example.online_shopping.database.productDataBase;
import com.example.online_shopping.model.AllProductModel;
import com.example.online_shopping.model.product;
import com.example.online_shopping.user.AllProductDao;
import com.example.online_shopping.user.productDao;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class BarChart extends AppCompatActivity {

    com.github.mikephil.charting.charts.BarChart barChart;
    ArrayList<BarEntry> barEntryList;
    ArrayList<String> labelsNames;
    List<AllProductModel> products;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);


        barChart = (com.github.mikephil.charting.charts.BarChart) findViewById(R.id.chart);

        barEntryList = new ArrayList<>();
        labelsNames = new ArrayList<>();

        AllProductDatabase proDatabase= AllProductDatabase.Get_Instance(getApplicationContext());
        final AllProductDao prodDao=proDatabase.allProductDao();

        new Thread(new Runnable() {
            @Override
            public void run() {
               products = prodDao.getTopProducts();

                for(int i=0;i<products.size();i++){
                    String title= products.get(i).getTitle();
                    int pcount = products.get(i).getPurchaseCount();
                    barEntryList.add(new BarEntry(i, pcount));
                    labelsNames.add(title);
                }

                BarDataSet barDataSet = new BarDataSet(barEntryList, "Top 5 Best Selling Products");
                barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                barChart.setDescription(null);
                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));

                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(false);
                xAxis.setGranularity(1f);
                xAxis.setLabelCount(labelsNames.size());
                xAxis.setLabelRotationAngle(360);

            }
        }).start();

        barChart.animateY(2000);
        barChart.invalidate();
    }

}