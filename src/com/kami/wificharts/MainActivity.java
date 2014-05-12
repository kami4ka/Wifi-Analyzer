
package com.kami.wificharts;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {
    
    private static final int HISTORY_SIZE = 10;
    private XYPlot strengthPlot;
    private XYPlot timePlot;
    
    WifiManager mainWifiObj;
    WifiScanReceiver wifiReciever;
    ListView list;
    String wifis[];
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.listView1);
        
        initPlots();
        setTestData();
        
        mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiReciever = new WifiScanReceiver();
        mainWifiObj.startScan();
    }

    
    protected void onPause() {
        unregisterReceiver(wifiReciever);
        super.onPause();
     }

     protected void onResume() {
        registerReceiver(wifiReciever, new IntentFilter(
        WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
     }
     
    private void initPlots()
    {
        // initialize our XYPlot reference:
        strengthPlot = (XYPlot) findViewById(R.id.signalStrength);
        
        strengthPlot.setRangeValueFormat(new DecimalFormat("#"));
        //strengthPlot.setRangeStepValue(10);
        //strengthPlot.setTicksPerRangeLabel(7);
        strengthPlot.setRangeBoundaries(-100, -20, BoundaryMode.FIXED);
        
        strengthPlot.setDomainValueFormat(new DecimalFormat("#"));
        //strengthPlot.setDomainStepValue(1);
        //strengthPlot.setTicksPerDomainLabel(13);
        strengthPlot.setDomainBoundaries(0, 14, BoundaryMode.FIXED);
        
        timePlot = (XYPlot)findViewById(R.id.signalTime);
    }
    
    private void setTestData() {
        Number[] series1Numbers = {-100, -80, -50, -20, -70, -4};
 
        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series
 
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new SplineLineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_plf1);
 
        // add a new series' to the xyplot:
        strengthPlot.addSeries(series1, series1Format);
 
    }
     
    class WifiScanReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
            
           List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
           
           list.setAdapter(new InfoAdapter(getApplicationContext(), wifiScanList));
        
        }
     }

}
