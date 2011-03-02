package com.protodec_beta;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ConverterActivity extends Activity {
	private EditText decTextInput;
	private EditText hexTextInput;
	private EditText binTextInput;
	
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convertertab);
        	
        //alertDialog = new AlertDialog.Builder(this).create();
        
        decTextInput = (EditText) findViewById(R.id.decText);
        hexTextInput = (EditText) findViewById(R.id.hexText);
        binTextInput = (EditText) findViewById(R.id.binText);
        
        decTextInput.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				try{
					hexTextInput.setText(Integer.toHexString(Integer.parseInt(decTextInput.getText().toString())));
					binTextInput.setText(Integer.toBinaryString(Integer.parseInt(decTextInput.getText().toString())));
				}
				catch(Exception e){
					
				    //alertDialog.setTitle("ERROR");
				    //alertDialog.setMessage(e.);
				    //alertDialog.show();
				}
				
			}
		});
        
        decTextInput.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				try{
					hexTextInput.setText(Integer.toHexString(Integer.parseInt(decTextInput.getText().toString())));
					binTextInput.setText(Integer.toBinaryString(Integer.parseInt(decTextInput.getText().toString())));
				}
				catch(Exception e){
					//alertDialog.setTitle("ERROR");
				    //alertDialog.setMessage(e.getMessage());
				    //alertDialog.show();
				}
				return true;
			}
		});
        
        hexTextInput.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				try{
					decTextInput.setText(Integer.toString(Integer.parseInt(hexTextInput.getText().toString(), 16)));
					binTextInput.setText(Integer.toBinaryString(Integer.parseInt(hexTextInput.getText().toString(), 16)));
				}
				catch(Exception e){
					//alertDialog.setTitle("ERROR");
				    //alertDialog.setMessage(e.getMessage());
				    //alertDialog.show();
				}
				
			}
		});
        hexTextInput.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				try{
					decTextInput.setText(Integer.toString(Integer.parseInt(hexTextInput.getText().toString(), 16)));
					binTextInput.setText(Integer.toBinaryString(Integer.parseInt(hexTextInput.getText().toString(), 16)));
				}
				catch(Exception e){
					//alertDialog.setTitle("ERROR");
				    //alertDialog.setMessage(e.getMessage());
				    //alertDialog.show();
				}
				return true;
			}
		});
        
        binTextInput.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				try{
					decTextInput.setText(Integer.toString(Integer.parseInt(binTextInput.getText().toString(), 2)));
					hexTextInput.setText(Integer.toHexString(Integer.parseInt(binTextInput.getText().toString(), 2)));
				}
				catch(Exception e){
					//alertDialog.setTitle("ERROR");
				    //alertDialog.setMessage(e.getMessage());
				    //alertDialog.show();
				}
				
			}
		});
        binTextInput.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				try{
					decTextInput.setText(Integer.toString(Integer.parseInt(binTextInput.getText().toString(), 2)));
					hexTextInput.setText(Integer.toHexString(Integer.parseInt(binTextInput.getText().toString(), 2)));
				}
				catch(Exception e){
					//alertDialog.setTitle("ERROR");
				    //alertDialog.setMessage(e.getMessage());
				    //alertDialog.show();
				}
				return true;
			}
		});
        
    }
}