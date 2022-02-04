package com.digitalmusdi.digitalmaintenance.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.digitalmusdi.digitalmaintenance.databinding.FragmentHomeBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.*;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    TextView tvDate;
    PieChart piePMRealitization,pieFailure;
    LineChart lineFailure;
    TableLayout tableFailure;
    Button btnNewEdit,btnNewDel,btnNewView; // variable declare for dynamic View,Delete and Edit button
    TableRow newTR;			// variable declare for dynamic table row
    TextView newTxtID,newTxtName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        tvDate = binding.tvDate;
        piePMRealitization = binding.piePMRealitization;
        pieFailure = binding.pieFailure;
        lineFailure = binding.lineFailure;
        tableFailure = binding.tableFailure;

        homeViewModel.getDate().observe(getViewLifecycleOwner(),(s)->tvDate.setText(s));

        getDataPie();
        getDataLine();

        tableFailure.setColumnStretchable(0, true); //first column
        tableFailure.setColumnStretchable(1, true); //second column
        tableFailure.setColumnStretchable(2, true); //third column

        dynamicViewElement();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getDataPie(){
        // PM Real
        ArrayList<PieEntry> NoOfEmp = new ArrayList<>();

        NoOfEmp.add(new PieEntry(30f, "Open"));
        NoOfEmp.add(new PieEntry(70f, "Close"));
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "PM Realization");

        PieData data = new PieData(dataSet);
        piePMRealitization.setData(data);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        piePMRealitization.animateXY(2000, 2000);
        Description description = piePMRealitization.getDescription();
        description.setEnabled(true);
        description.setText("PM Realization");
        Legend legend = piePMRealitization.getLegend();
        legend.setEnabled(true);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setXEntrySpace(5f); // space between the legend entries on the x-axis
        legend.setYEntrySpace(5f); // space between the legend entries on the y-axis

        // Failure
        ArrayList<PieEntry> failAll = new ArrayList<>();
        failAll.add(new PieEntry(30f,"Open"));
        failAll.add(new PieEntry(70f,"Close"));
        PieDataSet set1 = new PieDataSet(failAll,"Failure");
        PieData data1 = new PieData(set1);
        pieFailure.setData(data1);
        set1.setColors(ColorTemplate.COLORFUL_COLORS);
        pieFailure.animateXY(2000,2000);
        Description description1 = pieFailure.getDescription();
        description1.setEnabled(true);
        description1.setText("Failure");
        Legend legend1 = pieFailure.getLegend();
        legend1.setEnabled(true);
        legend1.setForm(Legend.LegendForm.CIRCLE);
        legend1.setXEntrySpace(5f); // space between the legend entries on the x-axis
        legend1.setYEntrySpace(5f);
    }

    private void getDataLine(){
        ArrayList<Entry> failure = new ArrayList<>();

        failure.add(new Entry(0f,10f,0));
        failure.add(new Entry(1f,8f,1));
        failure.add(new Entry(2f,10f,2));
        failure.add(new Entry(3f,5f,3));
        failure.add(new Entry(4f,8f,4));

        LineDataSet dataSet = new LineDataSet(failure,"Failure");
        LineData lineData = new LineData(dataSet);
        lineFailure.setData(lineData);

        lineFailure.animateXY(2000,2000);
        Description description1 = lineFailure.getDescription();
        description1.setEnabled(true);
        description1.setText("Failure");
        Legend legend1 = lineFailure.getLegend();
        legend1.setEnabled(true);
        legend1.setForm(Legend.LegendForm.CIRCLE);
        legend1.setXEntrySpace(5f); // space between the legend entries on the x-axis
        legend1.setYEntrySpace(5f);

        final String[] quarters = new String[] { "2021-01-01","2021-02-01","2021-03-01","2021-04-01","2021-05-01" };

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return quarters[(int) value];
            }
        };

        XAxis xAxis = lineFailure.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        lineFailure.invalidate();


    }

    public void dynamicViewElement() {
        newTR = new TableRow(getContext());
        btnNewEdit = new Button(getContext());
        btnNewDel = new Button(getContext());
        btnNewView = new Button(getContext());
        newTxtID = new TextView(getContext());
        newTxtName = new TextView(getContext());

        /** start setting values of dyamic View Elements **/
        newTxtID.setText("1");
        newTxtName.setText("Codeinquirer Me");

        btnNewEdit.setText("Edit");
        btnNewDel.setText("Del");
        btnNewView.setText("View");
        /** end setting values of dyamic View Elements **/

        /** start appending dynamic textviews and buttons in tablerow **/
        newTR.addView(newTxtID);
        newTR.addView(newTxtName);
        newTR.addView(btnNewEdit);
        newTR.addView(btnNewDel);
        newTR.addView(btnNewView);
        /** start appending dynamic textviews and buttons in tablerow **/


        tableFailure.addView(newTR); //appending of dynamic table row to tablelayout
    }

}