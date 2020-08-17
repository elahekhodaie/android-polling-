package com.jasbuber.allpolls.services;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChartDisplayService {

    public static final int[] CHART_COLORS = {
            Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80), Color.rgb(159, 154, 164),
            Color.rgb(214, 249, 221), Color.rgb(194, 207, 187), Color.rgb(200, 197, 135),
            Color.rgb(130, 212, 187)
    };

    public PieData generatePieData(Map<String, Double> data) {

        List<String> labels = new ArrayList<>();
        labels.addAll(data.keySet());

        List<Entry> values = new ArrayList<>();

        List<Double> valueList = new ArrayList<>();
        valueList.addAll(data.values());

        for (int i = 0; i < valueList.size(); i++) {
            values.add(new Entry((float) (double) valueList.get(i), i));
        }

        PieDataSet ds1 = new PieDataSet(values, "");
        ds1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                if ((value % 1) == 0) {
                    return String.format(Locale.US, "%.1f", value);
                }
                return String.format(Locale.US, "%.2f", value);
            }
        });
        ds1.setColors(CHART_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        return new PieData(labels, ds1);
    }

    public void initializeChart(PieChart chart) {
        chart.setDrawHoleEnabled(false);
        chart.getLegend().setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        chart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        chart.setDescription("");
        chart.setRotationEnabled(false);
        chart.setTouchEnabled(false);
    }
}
