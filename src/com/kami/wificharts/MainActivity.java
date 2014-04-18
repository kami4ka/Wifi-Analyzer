
package com.kami.wificharts;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    
    WifiManager mainWifiObj;
    WifiScanReceiver wifiReciever;
    ListView list;
    String wifis[];
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.listView1);
        mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiReciever = new WifiScanReceiver();
        mainWifiObj.startScan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
    
    
    class WifiScanReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
           List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
           wifis = new String[wifiScanList.size()];
           for(int i = 0; i < wifiScanList.size(); i++){
              wifis[i] = (wifiScanList.get(i)).SSID + ": level = " + (wifiScanList.get(i)).level 
                      + ", BSSID = " + (wifiScanList.get(i)).BSSID + ", frequency = " + (wifiScanList.get(i)).frequency;
           }

           list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
           android.R.layout.simple_list_item_1,wifis));
        }
     }

}
