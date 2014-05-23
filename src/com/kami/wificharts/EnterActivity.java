package com.kami.wificharts;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EnterActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        
    }
    
    public void goCapturing(View v){
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }
}
