package com.kami.wificharts;

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

        ((TextView) mView.findViewById(R.id.ssid)).setText(mResult.SSID);

        return mView;
    }

}
