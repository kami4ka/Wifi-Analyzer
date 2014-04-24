package com.kami.wificharts;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InfoAdapter extends BaseAdapter {
    
    Context ctx;
    LayoutInflater lInflater;
    List<ScanResult> results;
    Integer[] channels = {0, 2412, 2417, 2422, 2427, 2432, 2437, 2442, 2447, 2452, 2457, 2462, 2467, 2472, 2482}; 
    
    InfoAdapter(Context context, List<ScanResult> objects) {
        ctx = context;
        results = objects;
        lInflater = (LayoutInflater) ctx
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      }
    
    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parentView) {
        View mView = view;
        
        if (mView == null) {
          mView = lInflater.inflate(R.layout.list_row, parentView, false);
        }
        
        ScanResult mResult = (ScanResult) getItem(position);
        
        int channel = Arrays.asList(channels).indexOf(mResult.frequency);

        ((TextView) mView.findViewById(R.id.ssid)).setText(String.valueOf(mResult.SSID));
        ((TextView) mView.findViewById(R.id.info)).setText("Канал: " + channel);
        ((TextView) mView.findViewById(R.id.bssid)).setText(String.valueOf(mResult.BSSID));
        ((TextView) mView.findViewById(R.id.signal)).setText("Рівень: " + mResult.level + "dB");
        
        return mView;
    }

}
