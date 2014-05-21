
package com.kami.wificharts;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.PointLabeler;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {
    
    private XYPlot strengthPlot;
    private XYPlot timePlot;
    
    private int mInterval = 10000; // 10 seconds by default, can be changed later
    
    WifiManager mainWifiObj;
    WifiScanReceiver wifiReciever;
    ListView list;
    String wifis[];
    ArrayList<Signal> signalList;
    int curColor = 0;
    private InfoAdapter mInfAd;
    
    Integer[] channels = {0, 2412, 2417, 2422, 2427, 2432, 2437, 2442, 2447, 2452, 2457, 2462, 2467, 2472, 2482};
	private Handler mHandler;
    
    static String[] ColourValues = new String[] { 
        "FF0000", "00FF00", "0000FF", "FFFF00", "FF00FF", "00FFFF", 
        "800000", "008000", "000080", "808000", "800080", "008080", "808080", 
        "C00000", "00C000", "0000C0", "C0C000", "C000C0", "00C0C0", "C0C0C0", 
        "400000", "004000", "000040", "404000", "400040", "004040", "404040", 
        "96000", "00960", "000020", "20960", "96020", "002020", "202020", 
        "600000", "006000", "000060", "606000", "600060", "006060", "606060", 
        "A00000", "00A000", "0000A0", "A0A000", "A000A0", "00A0A0", "A0A0A0", 
        "E00000", "00E000", "0000E0", "E0E000", "E000E0", "00E0E0", "E0E0E0", 
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.listView1);

        mHandler = new Handler();
        signalList = new ArrayList<Signal>();
        
        initPlots();
        mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiReciever = new WifiScanReceiver();
        mainWifiObj.startScan();
        
        startRepeatingTask();
        
        mInfAd = new InfoAdapter(getApplicationContext(), signalList);
        
        list.setAdapter(mInfAd);
        
        
    }

    
    protected void onPause() {
        unregisterReceiver(wifiReciever);
        stopRepeatingTask();
        super.onPause();
     }

     protected void onResume() {
        registerReceiver(wifiReciever, new IntentFilter(
        WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        startRepeatingTask();
        super.onResume();
     }
     
     Runnable mStatusChecker = new Runnable() {
    	    @Override 
    	    public void run() {
    	      mainWifiObj.startScan(); //this function can change value of mInterval.
    	      mHandler.postDelayed(mStatusChecker, mInterval);
    	    }
    	  };

    	  void startRepeatingTask() {
    	    mStatusChecker.run(); 
    	  }

    	  void stopRepeatingTask() {
    	    mHandler.removeCallbacks(mStatusChecker);
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
        strengthPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
        //strengthPlot.setTicksPerDomainLabel(15);
        strengthPlot.setDomainBoundaries(0, 14, BoundaryMode.FIXED);
        //strengthPlot.setDomainStepValue(1);
        
        timePlot = (XYPlot)findViewById(R.id.signalTime);
        
        timePlot.setRangeValueFormat(new DecimalFormat("#"));
        //strengthPlot.setRangeStepValue(10);
        //strengthPlot.setTicksPerRangeLabel(7);
        timePlot.setRangeBoundaries(-100, -20, BoundaryMode.FIXED);
        
        timePlot.setDomainValueFormat(new DecimalFormat("#"));
        timePlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
        //strengthPlot.setTicksPerDomainLabel(15);
        timePlot.setDomainBoundaries(0, 20, BoundaryMode.FIXED);
        //strengthPlot.setDomainStepValue(1);
    }
        
    private void addNumbersToPlot() {
    	for (Signal signal : signalList) {
    		
    		List<? extends Number> xVals =  new ArrayList<Double>();
            List<? extends Number> yVals =  new ArrayList<Double>();
    		
	        //Number[] series1Numbers = {-96, -96, -96, -96, -96, -96, -96, -96, -96, -96, -96, -96, -96, -96, -96};
	        //series1Numbers[Arrays.asList(channels).indexOf(signal.getScanResult().frequency)] = signal.getScanResult().level;
	        
	        System.out.println(Arrays.asList(channels).indexOf(signal.getScanResult().frequency) + " " + signal.getScanResult().level);
	        // Turn the above arrays into XYSeries':
	        
	        SimpleXYSeries series1 = new SimpleXYSeries(xVals, yVals, signal.getName());                             // Set the display title of the series
	        
	        series1.addFirst(Arrays.asList(channels).indexOf(signal.getScanResult().frequency) + 2, -100);
	        series1.addFirst(Arrays.asList(channels).indexOf(signal.getScanResult().frequency), signal.getScanResult().level);
	        series1.addFirst(Arrays.asList(channels).indexOf(signal.getScanResult().frequency) - 2, -100);
	        
	        // Create a formatter to use for drawing a series using LineAndPointRenderer
	        // and configure it from xml:
	        LineAndPointFormatter series1Format = new SplineLineAndPointFormatter(Color.parseColor("#" + signal.getColor()),
	        		Color.BLACK, null);
	        
	        series1Format.setPointLabelFormatter(new PointLabelFormatter(Color.WHITE));
    		series1Format.setPointLabeler(new PointLabeler() {
				
				@Override
				public String getLabel(XYSeries arg0, int arg1) {
					// TODO Auto-generated method stub
					return arg1 == 1 ? arg0.getTitle():"";
				}
			});
	        
	        //series1Format.setPointLabelFormatter(new PointLabelFormatter());
	        //series1Format.configure(getApplicationContext(),
	        //        R.xml.line_point_formatter_with_plf1);
	 
	        // add a new series' to the xyplot:
	        strengthPlot.addSeries(series1, series1Format);
    	}
    	
        strengthPlot.redraw();
 
    }
    
    private void clearPlots() {
    	strengthPlot.clear();
    	timePlot.clear();
    }
    
    private void addTimeNumbers() {
    	
    	for (Signal mSignal : signalList) {
    		LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.parseColor("#" + mSignal.getColor()),
            		Color.BLACK, null, null);
    		
    		
    		timePlot.addSeries(mSignal.getSeries(), series1Format);
    	}
    	
    	timePlot.redraw();
    }
     
    class WifiScanReceiver extends BroadcastReceiver {


		public void onReceive(Context c, Intent intent) {
            
           List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
           
           for (ScanResult scan : wifiScanList)
           {
               if (signalList.contains(new Signal(scan.SSID, ColourValues[curColor], scan))){
            	   //signalList.get(signalList.indexOf(new Signal(scan.SSID, ColourValues[curColor], scan))).addToSeries(scan.level);
            	   System.out.println("Found " + scan.SSID);
               } else {
                   signalList.add(new Signal(scan.SSID, ColourValues[curColor], scan));
                   //signalList.get(signalList.indexOf(new Signal(scan.SSID, ColourValues[curColor], scan))).addToSeries(scan.level);
                   curColor++;
               }
               
           }
           
           for (Signal sig : signalList)
           {
        	   sig.addToSeries(-100);
           }
           
           for (ScanResult scan : wifiScanList)
           {
                signalList.get(signalList.indexOf(new Signal(scan.SSID))).removeLast();
                signalList.get(signalList.indexOf(new Signal(scan.SSID))).addToSeries(scan.level);
                signalList.get(signalList.indexOf(new Signal(scan.SSID))).setScanResult(scan);
            
           }
           
           System.out.println(signalList.size());
           System.out.println("----------------");
           
           clearPlots();

           addTimeNumbers();
           addNumbersToPlot();
           
           
           mInfAd.notifyDataSetChanged();
        }
     }
}
