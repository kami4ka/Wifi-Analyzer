package com.kami.wificharts;

import android.net.wifi.ScanResult;

import com.androidplot.xy.SimpleXYSeries;

public class Signal {
    
    private static final int HISTORY_SIZE = 20;
    private String mName;
    private String mColor;
    private ScanResult mScanResult;
    private SimpleXYSeries mSeries = null;
    
    public Signal (String name) {
    	setName(name);
    }
    
    public Signal(String name, String color, ScanResult scanresult) {
        mSeries = new SimpleXYSeries(mName);
        mSeries.useImplicitXVals();
        setName(name);
        setColor(color);
        setScanResult(scanresult);
    }
    
    public void addToSeries(int level) {
        if (mSeries.size() > HISTORY_SIZE) {
            mSeries.removeFirst();
        }
        
        mSeries.addLast(null, level);
    }
    
    public void removeLast() {
    	mSeries.removeLast();
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Signal)) return false;
        Signal obj = (Signal) o;
        return this.mName.equals(obj.getName());
    }
    
    public SimpleXYSeries getSeries() {
        return mSeries;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String mColor) {
        this.mColor = mColor;
    }

	public ScanResult getScanResult() {
		return mScanResult;
	}

	public void setScanResult(ScanResult mScanResult) {
		this.mScanResult = mScanResult;
	}
    

}
