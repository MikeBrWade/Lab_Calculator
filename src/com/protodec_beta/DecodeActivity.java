package com.protodec_beta;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DecodeActivity extends Activity {
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is a DECODE tab.");
        setContentView(textview);
    }
}