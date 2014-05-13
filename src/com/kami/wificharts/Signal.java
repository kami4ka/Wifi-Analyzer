package com.kami.wificharts;

import com.androidplot.xy.SimpleXYSeries;

public class Signal {
    
    private static final int HISTORY_SIZE = 10;
    private String mName;
    private String mColor;
    private SimpleXYSeries mSeries = null;
    
    public Signal(String name, String color) {
        mSeries = new SimpleXYSeries(mName);
        mSeries.useImplicitXVals();
        setName(name);
        setColor(color);
    }
    
    public void addToSeries(int level) {
        if (mSeries.size() > HISTORY_SIZE) {
            mSeries.removeFirst();
        }
        
        mSeries.addLast(null, level);
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
    

}
