package com.kami.wificharts;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InfoAdapter extends BaseAdapter {
    
    Context ctx;
    LayoutInflater lInflater;
    List<Signal> results;
    Integer[] channels = {0, 2412, 2417, 2422, 2427, 2432, 2437, 2442, 2447, 2452, 2457, 2462, 2467, 2472, 2482}; 
    
    InfoAdapter(Context context, List<Signal> objects) {
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
        
       
        
        Signal mResult = (Signal) getItem(position);
        
        int channel = Arrays.asList(channels).indexOf(mResult.getScanResult().frequency);
        
        String encryption;
        
        if(mResult.getScanResult().capabilities.contains("WPA"))
        {
             // We know there is WPA encryption
            encryption = "WPA";
        }
        else if(mResult.getScanResult().capabilities.contains("WEP"))
        {
            encryption = "WEP";
        }
        else
        { 
            // Another type of security scheme, open wifi, captive portal, etc..
            encryption = "Mixed/Open";
        }
        
        ((TextView) mView.findViewById(R.id.ssid)).setTextColor(Color.parseColor("#" + mResult.getColor()));
        ((TextView) mView.findViewById(R.id.info)).setTextColor(Color.parseColor("#" + mResult.getColor()));
        ((TextView) mView.findViewById(R.id.bssid)).setTextColor(Color.parseColor("#" + mResult.getColor()));
        ((TextView) mView.findViewById(R.id.signal)).setTextColor(Color.parseColor("#" + mResult.getColor()));

        ((TextView) mView.findViewById(R.id.ssid)).setText(String.valueOf(mResult.getScanResult().SSID));
        ((TextView) mView.findViewById(R.id.info)).setText(ctx.getResources().getString(R.string.channel) + channel + ", " + ctx.getResources().getString(R.string.encryption) + encryption);
        ((TextView) mView.findViewById(R.id.bssid)).setText(String.valueOf(mResult.getScanResult().BSSID));
        ((TextView) mView.findViewById(R.id.signal)).setText(ctx.getResources().getString(R.string.strength) + mResult.getScanResult().level + "dB");
        
        return mView;
    }

}
