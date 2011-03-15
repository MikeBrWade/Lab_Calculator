package com.protodec_beta;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.protodecbeta.R;

public class EncodeActivity extends Activity implements OnClickListener, OnTouchListener {
	private TextView FileText;
	private Button OpenFile, Button1, Button2, Button3;
	private EditText BrowseField;
	
	private String BrowsePath, TextField;
	private Boolean filePathSet = false;
	
	RelativeLayout encoderScreen;
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sets the view to the encoder view
        setContentView(R.layout.encodertabview);
        
        // Links the text to the appropriate UI IDs
        linkTextFields();
        
        // Links the buttons to their appropriate UI Objects
        linkButtonObjects();
        
        // Sets up the click and touch interfaces for the buttons
        linkClickandTouchListener();
        
        // Set this so keyboard doesn't appear
        BrowseField.setInputType(0);
        
        // Beats me what this does, have to ask mike
        encoderScreen = (RelativeLayout)findViewById(R.id.encoderScreen);
             
    }
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.browseText : browseForFile(); break;
		case R.id.openButton : openFileFromPath(); break;
		case R.id.button1	 : testButton1(); break;
		case R.id.button2    : testButton2(); break;
		case R.id.button3    : testButton3(); break;
		}
		
	}

	private void linkTextFields(){
		FileText = (TextView)findViewById(R.id.fileContent);
		BrowseField = (EditText)findViewById(R.id.browseText);
	}
	
	private void linkButtonObjects() {
		OpenFile = (Button)findViewById(R.id.openButton);
		Button1 = (Button)findViewById(R.id.button1);
		Button2 = (Button)findViewById(R.id.button2);
		Button3 = (Button)findViewById(R.id.button3);
	}
	
	private void linkClickandTouchListener() {
		
		BrowseField.setOnClickListener(this);
		BrowseField.setOnTouchListener(this);
		OpenFile.setOnClickListener(this);
		OpenFile.setOnTouchListener(this);
		Button1.setOnClickListener(this);
		Button1.setOnTouchListener(this);
		Button2.setOnClickListener(this);
		Button2.setOnTouchListener(this);
		Button3.setOnClickListener(this);
		Button3.setOnTouchListener(this);
	}
	
	private void browseForFile() {
		FileText.setText("Browse for file text selected\n");
		FileText.setVisibility(View.VISIBLE);
		
	}
	private void openFileFromPath() {
		// TODO Auto-generated method stub
		FileText.setText("Open File Button Pressed\n");
		FileText.setVisibility(View.VISIBLE);
		
	}
	private void testButton1() {
		// TODO Auto-generated method stub
		FileText.setText("Test Button1 Pressed!");
		FileText.setVisibility(View.VISIBLE);
		
	}
	private void testButton2() {
		// TODO Auto-generated method stub
		FileText.setText("Test Button2 Pressed!");
		FileText.setVisibility(View.VISIBLE);
		
	}
	private void testButton3() {
		// TODO Auto-generated method stub
		FileText.setText("Test Button3 Pressed!");
		FileText.setVisibility(View.VISIBLE);
		
	}

	
    
}