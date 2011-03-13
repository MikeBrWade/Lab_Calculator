package com.protodec_beta;

import java.math.BigInteger;
import java.text.NumberFormat;

import com.protodec_beta.CustomizeDialog;
import com.protodec_beta.HelpDialog;
import com.protodec_beta.SetPreference;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.protodecbeta.R;

public class ConverterActivity extends Activity implements OnClickListener, OnTouchListener {
	// Set of the TextView for the labels and the output lines
	private TextView lbldecimal, lblhex, lblbinary;
	private TextView txtdecimal, txthex, txtbinary, txtInformational, txtcomp, txtprecision, txtTestParse;

	// Private Button Objects to drive display/UI
	private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnA, btnB, btnC, btnD, btnE, btnF;
	private Button btnClear, btnPlus, btnMinus, btnTimes, btnDivide, btnShiftL, btnShiftR, btnEquals;
	private Button btnSign, btnBS, btnAND, btnOR, btnNOT, btnXOR;
	
	// Radio Buttons for switching between entry styles
	private RadioButton radio_dec, radio_hex, radio_bin;
	
	// Control Flags
	private enum modeTypeEnum {
		DECIMAL_MODE, HEX_MODE, BINARY_MODE
	}
	private modeTypeEnum currentMode;
	private boolean calculationOperationInProgress, logicflag, zerosflag, shiftflag;
	private	boolean testFlag1, testFlag2, testFlag3, testFlag4, testFlag5;
	private int secondflag, calcstatus, binbitsflag;
	
	// Member Variables for Calculation and output/input
	private String decstring, hexstring, binstring;
	private long decvalue, decsave;
	BigInteger uint64Instance;
	private String tempstring;
	private int tempInt;
	RelativeLayout mScreen;


	/** Called when the activity is first created. */
	// Basic init and object link into UI
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Sets my view to the default converterTab xml
		setContentView(R.layout.convertertab);

		// Setup Text/Label Objects linking into layout
		linkTextFieldsIntoControlObjects();
		
		// Setup Button Objects linking into layout
		linkButtonObjects();

		// Set Click Listeners
		linkClickAndTouchListenerInterfaces();
		
		// Playing around with context menus "long click"
		registerForContextMenu(btnNOT);

		mScreen = (RelativeLayout) findViewById(R.id.myScreen); 

	}
	
	//  =======================  NUMBERPAD =======================   
	//  Set of functions to manage the number pad, both the input and operation buttons
	private void setCalcKeys() {
		// Grabbing the current display instance and the orientation flag
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		int orient = display.getOrientation();
		
		// Depending on the orientation and the presence of a current calculation, hide/show the input elements
		// TODO I need a more elegant way of doing calculations, tracking them with a variable is kinda cruddy
		
		if(((orient==Surface.ROTATION_0)||(orient==Surface.ROTATION_180))) 
		{
			// If the phone is in landscape (either normal or upside down, enable the keys, 
			showAllInputButtons();
			
			// TODO not completed yet, add 64bit extended display fields
			hideExtended64BitDisplay();
		}
		else {
			// otherwise enable the extended 64 bit display and disable the keys)
			hideAllInputButtons();
			
			// TODO not completed yet, add 64bit extended display fields
			showExtended64BitDisplay();
		}
	}
	private void setLogicKeys() {
		// Grabbing the current display instance and the orientation flag
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		int orient = display.getOrientation();
		
		if(((orient==Surface.ROTATION_0)||(orient==Surface.ROTATION_180))) 
		{
			// If the phone is in landscape (either normal or upside down, enable the keys, 
			showAllOperationButtons();
		}
		else {
			// otherwise enable the extended 64 bit display and disable the keys)
			hideAllOperationButtons();
		}
	}	
	
	// Utility Function used to hide/show various fields and buttons in the UI
	private void showAllOperationButtons() {
		btnPlus.setVisibility(View.VISIBLE);
		btnMinus.setVisibility(View.VISIBLE);
		btnTimes.setVisibility(View.VISIBLE);
		btnDivide.setVisibility(View.VISIBLE);
		btnEquals.setVisibility(View.VISIBLE);
		btnNOT.setVisibility(View.VISIBLE);
		btnAND.setVisibility(View.VISIBLE);
		btnSign.setVisibility(View.VISIBLE);
		btnBS.setVisibility(View.VISIBLE);
		btnClear.setVisibility(View.VISIBLE);
		btnShiftL.setVisibility(View.VISIBLE);
		btnShiftR.setVisibility(View.VISIBLE);
	}
	private void hideAllOperationButtons() {
		btnPlus.setVisibility(View.INVISIBLE);
		btnMinus.setVisibility(View.INVISIBLE);
		btnTimes.setVisibility(View.INVISIBLE);
		btnDivide.setVisibility(View.INVISIBLE);
		btnEquals.setVisibility(View.INVISIBLE);
		btnNOT.setVisibility(View.INVISIBLE);
		btnAND.setVisibility(View.INVISIBLE);
		btnSign.setVisibility(View.INVISIBLE);
		btnBS.setVisibility(View.INVISIBLE);
		btnClear.setVisibility(View.INVISIBLE);
		btnShiftL.setVisibility(View.INVISIBLE);
		btnShiftR.setVisibility(View.INVISIBLE);		
	}
	private void showAllInputButtons() {
		btn0.setVisibility(View.VISIBLE);
		btn1.setVisibility(View.VISIBLE);
		btn2.setVisibility(View.VISIBLE);
		btn3.setVisibility(View.VISIBLE);
		btn4.setVisibility(View.VISIBLE);
		btn5.setVisibility(View.VISIBLE);
		btn6.setVisibility(View.VISIBLE);
		btn7.setVisibility(View.VISIBLE);
		btn8.setVisibility(View.VISIBLE);
		btn9.setVisibility(View.VISIBLE);		
		btnA.setVisibility(View.VISIBLE);
		btnB.setVisibility(View.VISIBLE);
		btnC.setVisibility(View.VISIBLE);
		btnD.setVisibility(View.VISIBLE);
		btnE.setVisibility(View.VISIBLE);
		btnF.setVisibility(View.VISIBLE);
	}
	private void hideAllInputButtons() {
		btn0.setVisibility(View.INVISIBLE);
		btn1.setVisibility(View.INVISIBLE);
		btn2.setVisibility(View.INVISIBLE);
		btn3.setVisibility(View.INVISIBLE);
		btn4.setVisibility(View.INVISIBLE);
		btn5.setVisibility(View.INVISIBLE);
		btn6.setVisibility(View.INVISIBLE);
		btn7.setVisibility(View.INVISIBLE);
		btn8.setVisibility(View.INVISIBLE);
		btn9.setVisibility(View.INVISIBLE);		
		btnA.setVisibility(View.INVISIBLE);
		btnB.setVisibility(View.INVISIBLE);
		btnC.setVisibility(View.INVISIBLE);
		btnD.setVisibility(View.INVISIBLE);
		btnE.setVisibility(View.INVISIBLE);
		btnF.setVisibility(View.INVISIBLE);		
	}
	private void showExtended64BitDisplay() {
		// TODO Add extended Display code
	}
	private void hideExtended64BitDisplay() {
		// TODO Add extended Display code
	}
	private void setDecButtons() {
		btn0.setEnabled(true);
		btn1.setEnabled(true);
		btn2.setEnabled(true);
		btn3.setEnabled(true);
		btn4.setEnabled(true);
		btn5.setEnabled(true);
		btn6.setEnabled(true);
		btn7.setEnabled(true);
		btn8.setEnabled(true);
		btn9.setEnabled(true);
		btnA.setEnabled(false);
		btnB.setEnabled(false);
		btnC.setEnabled(false);
		btnD.setEnabled(false);
		btnE.setEnabled(false);
		btnF.setEnabled(false);
	}
	private void setHexButtons() {
		btn0.setEnabled(true);
		btn1.setEnabled(true);
		btn2.setEnabled(true);
		btn3.setEnabled(true);
		btn4.setEnabled(true);
		btn5.setEnabled(true);
		btn6.setEnabled(true);
		btn7.setEnabled(true);
		btn8.setEnabled(true);
		btn9.setEnabled(true);
		btnA.setEnabled(true);
		btnB.setEnabled(true);
		btnC.setEnabled(true);
		btnD.setEnabled(true);
		btnE.setEnabled(true);
		btnF.setEnabled(true);
	}
	private void setBinButtons() {
		btn0.setEnabled(true);
		btn1.setEnabled(true);
		btn2.setEnabled(false);
		btn3.setEnabled(false);
		btn4.setEnabled(false);
		btn5.setEnabled(false);
		btn6.setEnabled(false);
		btn7.setEnabled(false);
		btn8.setEnabled(false);
		btn9.setEnabled(false);
		btnA.setEnabled(false);
		btnB.setEnabled(false);
		btnC.setEnabled(false);
		btnD.setEnabled(false);
		btnE.setEnabled(false);
		btnF.setEnabled(false);
	}
		
	//  ============================================================ 
	
	
	

	//  =================== UI Driver and Update Methods =======================
	// ----------------- Display Input Drivers  --------------------
	//Captures all clicks from various buttons and dispatches their commands
	public void onClick(View v) {   
		char c;
		String savstring;
		long decnew;
		long decprod;

		if(v==btnClear) 
		{
			// User pressed clear button
			// Clear current values and the UI
			clearDisp();
			setCalcKeys();
			setLogicKeys();
		}
		else if(v==btnBS) {
			switch(currentMode){
			case DECIMAL_MODE:
				c = decstring.charAt(0);
				if(decstring.length()==1) {
					decstring = "0";	
				}
				else if(decstring.length()==2 && c=='-') {
					decstring = "0";
				}
				else if(decstring.length()>0) {
					decstring = decstring.substring(0, decstring.length()-1);
				}
				decvalue = Long.parseLong(decstring);
				break;
			case HEX_MODE:
				if(hexstring.length()==1) {
					hexstring = "0";	
				}
				else if(hexstring.length()>0) {
					hexstring = hexstring.substring(0, hexstring.length()-1);
				}
				decvalue = Long.parseLong(hexstring, 16);
				break;
			case BINARY_MODE:
				if(binstring.length()==1) {
					binstring = "0";	
				}
				else if(binstring.length()>0) {
					binstring = binstring.substring(0, binstring.length()-1);
				}
				decvalue = Long.parseLong(binstring, 2); 
				break;
			}
			displayValues();
		}
		//Second flag - Plus = 1, Minus = 2, Times = 3, Divide = 4
		//          OR = 5, NOT = 6, AND = 7, ShiftL = 8, XOR = 9, ShiftR = 10    	
		else if(v==btnPlus) {
			decsave = decvalue;
			secondflag = 1;
			calcstatus = 1;
			btnClear.setText("Cancel");
			btnMinus.setVisibility(View.INVISIBLE);
			btnTimes.setVisibility(View.INVISIBLE);
			btnDivide.setVisibility(View.INVISIBLE);
			btnAND.setVisibility(View.INVISIBLE);
			btnOR.setVisibility(View.INVISIBLE);
			btnNOT.setVisibility(View.INVISIBLE);
			btnXOR.setVisibility(View.INVISIBLE);
			btnShiftL.setVisibility(View.INVISIBLE);
			btnShiftR.setVisibility(View.INVISIBLE);
			btnEquals.setEnabled(true);
			btnPlus.setEnabled(false);
		}
		else if(v==btnMinus) {
			decsave = decvalue;
			secondflag = 2;
			calcstatus = 1;
			btnClear.setText("Cancel");
			btnPlus.setVisibility(View.INVISIBLE);
			btnMinus.setVisibility(View.VISIBLE);
			btnTimes.setVisibility(View.INVISIBLE);
			btnDivide.setVisibility(View.INVISIBLE);
			btnAND.setVisibility(View.INVISIBLE);
			btnOR.setVisibility(View.INVISIBLE);
			btnNOT.setVisibility(View.INVISIBLE);
			btnXOR.setVisibility(View.INVISIBLE);
			btnShiftL.setVisibility(View.INVISIBLE);
			btnShiftR.setVisibility(View.INVISIBLE);
			btnEquals.setVisibility(View.VISIBLE);
			btnEquals.setEnabled(true);
			btnMinus.setEnabled(false);
		}
		else if(v==btnTimes) {
			decsave = decvalue;
			secondflag = 3;
			calcstatus = 1;
			btnClear.setText("Cancel");
			btnPlus.setVisibility(View.INVISIBLE);
			btnMinus.setVisibility(View.INVISIBLE);
			btnTimes.setVisibility(View.VISIBLE);
			btnDivide.setVisibility(View.INVISIBLE);
			btnAND.setVisibility(View.INVISIBLE);
			btnOR.setVisibility(View.INVISIBLE);
			btnNOT.setVisibility(View.INVISIBLE);
			btnXOR.setVisibility(View.INVISIBLE);
			btnShiftL.setVisibility(View.INVISIBLE);
			btnShiftR.setVisibility(View.INVISIBLE);
			btnEquals.setVisibility(View.VISIBLE);
			btnEquals.setEnabled(true);
			btnTimes.setEnabled(false);
		}
		else if(v==btnDivide) {
			decsave = decvalue;
			secondflag = 4;
			calcstatus = 1;
			btnClear.setText("Cancel");
			btnPlus.setVisibility(View.INVISIBLE);
			btnMinus.setVisibility(View.INVISIBLE);
			btnTimes.setVisibility(View.INVISIBLE);
			btnDivide.setVisibility(View.VISIBLE);
			btnAND.setVisibility(View.INVISIBLE);
			btnOR.setVisibility(View.INVISIBLE);
			btnNOT.setVisibility(View.INVISIBLE);
			btnXOR.setVisibility(View.INVISIBLE);
			btnShiftL.setVisibility(View.INVISIBLE);
			btnShiftR.setVisibility(View.INVISIBLE);
			btnEquals.setVisibility(View.VISIBLE);
			btnEquals.setEnabled(true);
			btnDivide.setEnabled(false);
		}
		else if(v==btnOR) {
			decsave = decvalue;
			secondflag = 5;
			calcstatus = 1;
			btnClear.setText("Cancel");
			btnPlus.setVisibility(View.INVISIBLE);
			btnMinus.setVisibility(View.INVISIBLE);
			btnTimes.setVisibility(View.INVISIBLE);
			btnDivide.setVisibility(View.INVISIBLE);
			btnAND.setVisibility(View.INVISIBLE);
			btnNOT.setVisibility(View.INVISIBLE);
			btnXOR.setVisibility(View.INVISIBLE);
			btnShiftL.setVisibility(View.INVISIBLE);
			btnShiftR.setVisibility(View.INVISIBLE);
			btnEquals.setEnabled(true);
			btnOR.setEnabled(false);
		}
		else if(v==btnAND) {
			decsave = decvalue;
			secondflag = 7;
			calcstatus = 1;
			btnClear.setText("Cancel");
			btnPlus.setVisibility(View.INVISIBLE);
			btnMinus.setVisibility(View.INVISIBLE);
			btnTimes.setVisibility(View.INVISIBLE);
			btnDivide.setVisibility(View.INVISIBLE);
			btnOR.setVisibility(View.INVISIBLE);
			btnNOT.setVisibility(View.INVISIBLE);
			btnXOR.setVisibility(View.INVISIBLE);
			btnShiftL.setVisibility(View.INVISIBLE);
			btnShiftR.setVisibility(View.INVISIBLE);
			btnEquals.setEnabled(true);
			btnAND.setEnabled(false);
		}
		else if(v==btnShiftL) {
			if (shiftflag==true) {
				decsave = decvalue;
				secondflag = 8;
				calcstatus = 1;
				btnClear.setText("Cancel");
				btnPlus.setVisibility(View.INVISIBLE);
				btnMinus.setVisibility(View.INVISIBLE);
				btnTimes.setVisibility(View.INVISIBLE);
				btnDivide.setVisibility(View.INVISIBLE);
				btnOR.setVisibility(View.INVISIBLE);
				btnAND.setVisibility(View.INVISIBLE);
				btnNOT.setVisibility(View.INVISIBLE);
				btnXOR.setVisibility(View.INVISIBLE);
				btnShiftR.setVisibility(View.INVISIBLE);
				btnEquals.setEnabled(true);
				btnShiftL.setEnabled(false);
			}
			else {
				//			Just shift one position left
				decvalue = decvalue << 1;
				displayValues();
			}

		}
		else if(v==btnXOR) {
			decsave = decvalue;
			secondflag = 9;
			calcstatus = 1;
			btnClear.setText("Cancel");
			btnPlus.setVisibility(View.INVISIBLE);
			btnMinus.setVisibility(View.INVISIBLE);
			btnTimes.setVisibility(View.INVISIBLE);
			btnDivide.setVisibility(View.INVISIBLE);
			btnOR.setVisibility(View.INVISIBLE);
			btnAND.setVisibility(View.INVISIBLE);
			btnNOT.setVisibility(View.INVISIBLE);
			btnShiftL.setVisibility(View.INVISIBLE);
			btnShiftR.setVisibility(View.INVISIBLE);
			btnEquals.setEnabled(true);
			btnXOR.setEnabled(false);
		}
		else if(v==btnShiftR) {
			if (shiftflag==true) {
				decsave = decvalue;
				secondflag = 10;
				calcstatus = 1;
				btnClear.setText("Cancel");
				btnPlus.setVisibility(View.INVISIBLE);
				btnMinus.setVisibility(View.INVISIBLE);
				btnTimes.setVisibility(View.INVISIBLE);
				btnDivide.setVisibility(View.INVISIBLE);
				btnOR.setVisibility(View.INVISIBLE);
				btnAND.setVisibility(View.INVISIBLE);
				btnNOT.setVisibility(View.INVISIBLE);
				btnXOR.setVisibility(View.INVISIBLE);
				btnShiftL.setVisibility(View.INVISIBLE);
				btnEquals.setEnabled(true);
				btnShiftR.setEnabled(false);
			}
			else {
				//			Just shift one position right    			
				decvalue = decvalue >> 1;
				displayValues();
			}
		}

		else if(v==btnSign) {
			decvalue = -decvalue;
			displayValues();
		}

		else if(v==btnNOT) {
			decvalue = ~decvalue;
			displayValues();
		}

		else if(v==btnEquals) {
			if(secondflag==1) {
				//				Plus
				decnew = decvalue + decsave;
				if ( (decvalue > 0 && decsave > 0 && (decnew < decvalue || decnew < decsave))
						|| (decvalue < 0 && decsave < 0 && (decnew > decvalue || decnew > decsave))) {
					Toast toast = Toast.makeText(getApplicationContext(), "Maximum number reached!", Toast.LENGTH_SHORT);
					toast.show();	
					decvalue = decsave;
				}
				else {
					decvalue = decnew;
				}
				displayValues();
			}
			else if(secondflag==2) {
				// 			Minus
				decnew = decsave - decvalue;
				if ( (decsave > 0 && decvalue < 0 && (decnew < decvalue || decnew < decsave))
						|| (decsave < 0 && decvalue > 0 && (decnew > decvalue || decnew > decsave))) {
					Toast toast = Toast.makeText(getApplicationContext(), "Maximum number reached!", Toast.LENGTH_SHORT);
					toast.show();	
					decvalue = decsave;
				}
				else {
					decvalue = decnew;
				}
				displayValues();
			}

			else if(secondflag==3) {
				// 			Times
				decnew = decvalue * decsave;
				if (decsave!=0) {
					decprod = decnew / decsave;
					if (decvalue != decprod) {
						Toast toast = Toast.makeText(getApplicationContext(), "Maximum number reached!", Toast.LENGTH_SHORT);
						toast.show();	
						decvalue = decsave;
					}
					else {
						decvalue = decnew;
					}
				}
				else {
					decvalue = decnew;
				}
				displayValues();
			}

			else if(secondflag==4) {
				// 			Divide
				if(decvalue==0) {
					Toast toast = Toast.makeText(getApplicationContext(), "Cannot divide by zero!", Toast.LENGTH_SHORT);
					toast.show();	
					clearDisp();
				}
				else {
					decvalue = decsave / decvalue;
					displayValues();
				}
			}
			else if(secondflag==5) {
				// 			OR
				decvalue = decsave | decvalue;
				displayValues();
			}
			else if(secondflag==7) {
				// 			AND
				decvalue = decsave & decvalue;
				displayValues();
			}
			else if(secondflag==8) {
				// 			ShiftL
				if(decvalue<=0) {
					decvalue = decsave;
				}
				else if(decvalue>63) {
					clearDisp();
				}
				else {
					int k = (int) decvalue;
					decvalue = decsave << k;
				}
				displayValues();
			}
			else if(secondflag==9) {
				// 			XOR
				decvalue = decsave ^ decvalue;
				displayValues();
			}
			else if(secondflag==10) {
				// 			ShiftR
				if(decvalue<=0) {
					decvalue = decsave;
				}
				else if(decvalue>63) {
					clearDisp();
				}
				else {
					String binsave = Long.toBinaryString(decsave);
					int k = (int) decvalue;
					if (k<binsave.length()) {
						int kx = binsave.length() - k;
						String newbin = binsave.substring(0, kx);
						decvalue = Long.parseLong(newbin, 2);
					}
					else {
						decvalue = 0;
					}
				}
				displayValues();
			}
			secondflag=0;
			calcstatus = 1;
			setCalcKeys();
			setLogicKeys();
			btnClear.setText("Clear");
		}

		else {
			//If here we are dealing with a number digit
			c = getButtonValue(v);
			if(calcstatus==1) {
				clearDisp();
				calcstatus=0;
			}
			switch(currentMode) {
			case DECIMAL_MODE:
				savstring = decstring;
				decstring = decstring + c;
				try {
					decvalue = Long.parseLong(decstring);
				}
				catch (Exception e) {
					Toast toast = Toast.makeText(getApplicationContext(), "Maximum number reached!", Toast.LENGTH_SHORT);
					toast.show();	
					decstring = savstring;
				}
			case HEX_MODE:
				savstring = hexstring;
				hexstring = hexstring + c;
				try {
					decvalue = Long.parseLong(hexstring, 16);
				}
				catch (Exception e) {
					Toast toast = Toast.makeText(getApplicationContext(), "Maximum number reached!", Toast.LENGTH_SHORT);
					toast.show();
					hexstring = savstring;
				}
			case BINARY_MODE:
				savstring = binstring;
				binstring = binstring + c;
				try {  	
					decvalue = Long.parseLong(binstring, 2);
				}
				catch (Exception e) {
					Toast toast = Toast.makeText(getApplicationContext(), "Maximum number reached!", Toast.LENGTH_SHORT);
					toast.show();
					binstring = savstring;
				}
			}
			displayValues();
		}
	}
	
	// ----------------- Display Output Drivers  --------------------
	//  Drives the various UI elements need to display output to the user
	private void displayValues() {
		String parsedNumericString;
		int kp, k;
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		int orient = display.getOrientation();
		Resources res = getResources();

		// Dumping config settings for debug purposes
		String configStatus =  String.format("1: %b, 2: %b, 3: %b, 4: %b, 5: %b", testFlag1, testFlag2, testFlag3, testFlag4, testFlag5);
		txtTestParse.setText(configStatus);
		txtTestParse.setVisibility(View.VISIBLE);
		
		// Dumping a 64bit HEX padded representation of the current value to a textview for debug purposes
		txtInformational.setText(buildPadded64BitHexString());
		txtInformational.setVisibility(View.VISIBLE);
		
		// =====================  OUTPUTTING FIELDS ====================
		//  ------------- DEC -----------------
		//  Display decvalue on the 4 output fields		
		decstring = Long.toString(decvalue);
		parsedNumericString = NumberFormat.getInstance().format(decvalue);
		txtdecimal.setText(parsedNumericString);

		
		//  ------------- HEX -----------------
		hexstring = Long.toHexString(decvalue);
		parsedNumericString = Long.toHexString(decvalue);
		parsedNumericString = parsedNumericString.toUpperCase();
		
		// This pads the HEX at the word boundary
		txthex.setText(padString(parsedNumericString,8));

		//  ------------- BIN -----------------
		binstring = Long.toBinaryString(decvalue);
		parsedNumericString = Long.toBinaryString(decvalue);
		// This pads the BIN at the byte boundary
		txtbinary.setText(padString(parsedNumericString,8));

		// Now that we have the strings lets scale the fields based on the size and the orientation
		//  ------------- DEC -----------------
		//  might not be needed
		//  ------------- HEX -----------------
		//  might not be needed
		//  ------------- BIN -----------------
		if ((parsedNumericString.length()>=22) & (orient==0)) {
			parsedNumericString = parsedNumericString.substring(parsedNumericString.length()- 22);
			txtbinary.setTextSize(10);
			//color = res.getColor(R.color.black);
			//txtbinary.setTextColor(color);
			//txtInformational.setVisibility(View.VISIBLE);
		}
		else{
			txtbinary.setTextSize(18);
			//color = res.getColor(R.color.black);
			//txtbinary.setTextColor(color);
			//txtInformational.setVisibility(View.INVISIBLE);
		}
	
	}
	// Used to clear all the fields as well as the labels and reset the buttons
	private void clearDisp() {
		// Resetting fields, button objects, and sizes
		txtdecimal.setText("0");
		txtdecimal.setTextSize(18);
		txthex.setText("0");
		txthex.setTextSize(18);
		txtbinary.setText("0");
		txtbinary.setTextSize(18);
		
		uint64Instance = BigInteger.ZERO;
		decvalue = 0;
		
		decstring = "0";
		hexstring = "0";
		binstring = "0";
		
		txtInformational.setVisibility(View.INVISIBLE);
		txtcomp.setVisibility(View.INVISIBLE);
		txtprecision.setVisibility(View.INVISIBLE);
		
		// used later when I signal for 64bit binary in landscape
		/*
		Resources res = getResources();
		int color = res.getColor(R.color.black);
		txtbinary.setTextColor(color);
		*/
	}
	
	//  ------------ Some Display Utility Functions ------------
	private String buildPadded64BitHexString() {
		String concateLocalString;
		Integer bitCountofCurrentValue;
		concateLocalString = "Bits[";
		
		// Grab the current value of the UI Input
		// This will need to be updated system wide but for now I am just trying it with the current 63 bit + 1 signed bit long value
		// I assumed this 64 bit value could be used unsigned but Java doesn't allow it.
		uint64Instance = BigInteger.valueOf(decvalue);

		// Grab the bit count and create the Hex Display
		bitCountofCurrentValue = uint64Instance.bitLength();
		concateLocalString = concateLocalString.concat(	String.valueOf(bitCountofCurrentValue) + 
														"] 0x" + 
														String.format("%016X", uint64Instance).substring(0, 8) + 
														" 0x" + 
														String.format("%016X", uint64Instance).substring(8, 16));		
		return concateLocalString;
	}
	private String padString(String paddedString, int i) {
		// Pads the string with spaces at the interval i
		//  TODO this is a bit hacky I need to look into the string utilities
		//    I am sure there is something that lets me do this easier and more
		//    efficiently
		Integer tmpInteger;
		String scratchPad;
		char c;
		paddedString = new StringBuilder(paddedString).reverse().toString();
		tmpInteger=0;
		scratchPad="";
		while(tmpInteger<paddedString.length()) {
			c = paddedString.charAt(tmpInteger);
			scratchPad = scratchPad + c;
			if((tmpInteger+1)%i==0) {
				scratchPad = scratchPad + " ";	
			}
			tmpInteger++;
		}
		return paddedString = new StringBuilder(scratchPad).reverse().toString();
	}
	private char getButtonValue(View v) {
		// TODO There has to be a better way, could I pull the value and reinterpret? 
		//    that would probably be more runtime expensive though I can't "switch" on 
		//    a view value and extracting text from the button elements then massaging
		//    then into values I can use probably would be worse
		char c;
		c = '0';
		if(v==btn0)      { c = '0'; }
		else if(v==btn1) { c = '1'; }
		else if(v==btn2) { c = '2'; }
		else if(v==btn3) { c = '3';	}
		else if(v==btn4) { c = '4'; }
		else if(v==btn5) { c = '5';	}
		else if(v==btn6) { c = '6'; }
		else if(v==btn7) { c = '7';	}
		else if(v==btn8) { c = '8';	}
		else if(v==btn9) { c = '9';	}
		else if(v==btnA) { c = 'A';	}
		else if(v==btnB) { c = 'B';	}
		else if(v==btnC) { c = 'C'; }
		else if(v==btnD) { c = 'D';	}
		else if(v==btnE) { c = 'E';	}
		else if(v==btnF) { c = 'F';	}
		return c;
	}
	
	// Handles the click of the radio buttons   
	private OnClickListener radio_listener = new OnClickListener() {
		public void onClick(View v) 
		{
			// Perform action on clicks
			if(v==radio_dec) {
				setDecButtons();
				currentMode = modeTypeEnum.DECIMAL_MODE;
			}
			else if(v==radio_hex) {
				setHexButtons();
				currentMode = modeTypeEnum.HEX_MODE;
			}
			else if(v==radio_bin) {
				setBinButtons();
				currentMode = modeTypeEnum.BINARY_MODE;
			}
		}
	};
	
	
	//  ======================== Options Menu ==============================
	//This is used to capture the menu action and create a menu instance
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()){
		case R.id.mnuAbout:
			CustomizeDialog customizeDialog = new CustomizeDialog(this);
			TextView title01 = (TextView)customizeDialog.findViewById(R.id.Title01);
			String about_string = "<center><b><font color=#4682B4>ABOUT</font></b></center><br>" +
			"<center>About ProtoDecoder</center><br><center>ProtoDec v1.5b</center>";			
			title01.setText(Html.fromHtml(about_string));
			TextView text = (TextView) customizeDialog.findViewById(R.id.TextView01);
			//text.setGravity(Gravity.CENTER);
			text.setText(R.string.about_body);
			customizeDialog.show();
			return true;

		case R.id.mnuSettings:
			startActivity(new Intent(ConverterActivity.this, SetPreference.class));
			return true;

		case R.id.mnuHelp:
			HelpDialog HelpDialog = new HelpDialog(this);
			TextView title02 = (TextView)HelpDialog.findViewById(R.id.Title01);
			title02.setText("Help information");
			TextView text1 = (TextView) HelpDialog.findViewById(R.id.TextView01);
			text1.setGravity(Gravity.LEFT);
			String help_string = "<b><font color=#4682B4>General usage</font></b><br>" +
			"ProtoDec: THERE IS NO HELP......<br> Mike has doooooooooomed you!";
			text1.setText(Html.fromHtml(help_string));
			HelpDialog.show();
			return true;

		case R.id.mnuClear:
			clearDisp();
			secondflag=0;
			calcstatus=0;
			setCalcKeys();  
			setLogicKeys();
			return true;

		case R.id.mnuExit:
			finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
 	
	// ========================== CONTEXT MENU ==========================
	// Testing out a long click option menu
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
		super.onCreateContextMenu(menu, v, menuInfo);  
		menu.setHeaderTitle("Example Context Menu");  
		menu.add(0, 1, 0, "Option 1");  
		menu.add(0, 2, 0, "Option 2");  
	}
	public boolean onContextItemSelected(MenuItem item) {  
		switch(item.getItemId())
		{
		case 1:
			// Do something based on Option 1 selected
			displayValues();
			break;
		case 2:
			// Do something based on Option 2 selected
			displayValues();
			break;
		default:
			// Erroneous selection
			return false;
		}
		// Show user passed option back to activity
		return true;
	}
	// ================================================================
		
	// ==================== PAUSE/RESUME STATE ==========================
	protected void onPause() 
	{
		super.onPause();
		int mode;

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		mode = currentMode.ordinal();
		editor.putLong("decvalue", decvalue);
		editor.putLong("decsave", decsave);
		editor.putInt("currentMode", mode);
		editor.putInt("secondflag", secondflag);
		editor.putInt("calcstatus", calcstatus);
		editor.commit();
	}
	protected void onResume() {

		super.onResume();

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		decvalue = preferences.getLong("decvalue", 0);
		decsave = preferences.getLong("decsave", 0);
		//tempstring = preferences.getString("currentMode", "d");
		tempInt = preferences.getInt("currentMode", 0);
		secondflag = preferences.getInt("secondflag", 0);
		calcstatus = preferences.getInt("calcstatus", 0);

		switch(tempInt) {
		case 0:
			currentMode = modeTypeEnum.DECIMAL_MODE;
			break;
		case 1:
			currentMode = modeTypeEnum.HEX_MODE;
			break;
		case 2:
			currentMode = modeTypeEnum.BINARY_MODE;
			break;
		default:
			// If the settings are missing or corrupt just default to decimal mode
			currentMode = modeTypeEnum.DECIMAL_MODE;			
		}

		SharedPreferences def_prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		testFlag1 = def_prefs.getBoolean("testFlag1", true);
		testFlag2 = def_prefs.getBoolean("testFlag2", true);
		testFlag3 = def_prefs.getBoolean("testFlag3", true);
		testFlag4 = def_prefs.getBoolean("testFlag4", true);
		testFlag5 = def_prefs.getBoolean("testFlag5", true);
		
		
		calculationOperationInProgress = def_prefs.getBoolean("calculationOperationInProgress", true);
		logicflag = def_prefs.getBoolean("logicflag", true);
		zerosflag = def_prefs.getBoolean("zerosflag", true);
		shiftflag = def_prefs.getBoolean("shiftflag", false);
		String tempst = def_prefs.getString("backgroundflag", "1");
	
		tempst = def_prefs.getString("userbaseflag", "8");
		tempst = def_prefs.getString("binbitsflag", "0");
		binbitsflag = Integer.valueOf(tempst);

		switch(currentMode) {
		case DECIMAL_MODE:
			radio_dec.setChecked(true);
			setDecButtons();
			break;
		case HEX_MODE:
			radio_hex.setChecked(true);
			setHexButtons();
			break;
		case BINARY_MODE:
			radio_bin.setChecked(true);
			setBinButtons();
			break;
		default:    
			// In any other case just set to decimal as default
			radio_dec.setChecked(true);
			setDecButtons();
			break;
		}	

		displayValues();
		setCalcKeys();
		setLogicKeys();
	}
	// ================================================================
	
	
	// ================ UTILITY FUNCTIONS  ====================
	//   --------  INIT -------- 
	// A few functions to setup the initial view and objects
	private void linkTextFieldsIntoControlObjects()
	{
		// Decimal Label and Output
		lbldecimal = (TextView)findViewById(R.id.lbldecimal);
		txtdecimal = (TextView)findViewById(R.id.txtdecimal);
		// Hex Label and Output
		lblhex = (TextView)findViewById(R.id.lblhex);
		txthex = (TextView)findViewById(R.id.txthex);
		// Binary Label and Output
		lblbinary = (TextView)findViewById(R.id.lblbinary);
		txtbinary = (TextView)findViewById(R.id.txtbinary);
		// Other text fields for output
		txtInformational = (TextView)findViewById(R.id.txtrotate);
		txtcomp = (TextView)findViewById(R.id.txtcomp);
		txtprecision = (TextView)findViewById(R.id.txtprecision);
		txtTestParse = (TextView)findViewById(R.id.txtTestParse);
		
		// Create uint
		//uint64Instance = new BigInteger(Long.toString(decvalue), 10);
	}
	private void linkButtonObjects(){
		// Linking all Button objects into their UI counterpart
		btn0 = (Button)findViewById(R.id.btn0);
		btn1 = (Button)findViewById(R.id.btn1);
		btn2 = (Button)findViewById(R.id.btn2);
		btn3 = (Button)findViewById(R.id.btn3);
		btn4 = (Button)findViewById(R.id.btn4);
		btn5 = (Button)findViewById(R.id.btn5);
		btn6 = (Button)findViewById(R.id.btn6);
		btn7 = (Button)findViewById(R.id.btn7);
		btn8 = (Button)findViewById(R.id.btn8);
		btn9 = (Button)findViewById(R.id.btn9);		
		btnA = (Button)findViewById(R.id.btnA);
		btnB = (Button)findViewById(R.id.btnB);
		btnC = (Button)findViewById(R.id.btnC);
		btnD = (Button)findViewById(R.id.btnD);
		btnE = (Button)findViewById(R.id.btnE);
		btnF = (Button)findViewById(R.id.btnF);
		btnBS = (Button)findViewById(R.id.btnBS);
		btnClear = (Button)findViewById(R.id.btnClear);
		btnPlus = (Button)findViewById(R.id.btnPlus);
		btnMinus = (Button)findViewById(R.id.btnMinus);
		btnTimes = (Button)findViewById(R.id.btnTimes);
		btnDivide = (Button)findViewById(R.id.btnDivide);
		btnSign = (Button)findViewById(R.id.btnSign);
		btnShiftL = (Button)findViewById(R.id.btnShiftL);
		btnShiftR = (Button)findViewById(R.id.btnShiftR);
		btnEquals = (Button)findViewById(R.id.btnEquals);
		btnAND = (Button)findViewById(R.id.btnAND);
		btnOR = (Button)findViewById(R.id.btnOR);
		btnNOT = (Button)findViewById(R.id.btnNOT);
		btnXOR = (Button)findViewById(R.id.btnXOR);
		radio_dec = (RadioButton) findViewById(R.id.radio_dec);
		radio_hex = (RadioButton) findViewById(R.id.radio_hex);
		radio_bin = (RadioButton) findViewById(R.id.radio_bin);
	}
	private void linkClickAndTouchListenerInterfaces()
	{
		btn0.setOnClickListener(this);
		btn0.setOnTouchListener(this);
		btn1.setOnClickListener(this);
		btn1.setOnTouchListener(this);
		btn2.setOnClickListener(this);
		btn2.setOnTouchListener(this);
		btn3.setOnClickListener(this);
		btn3.setOnTouchListener(this);
		btn4.setOnClickListener(this);
		btn4.setOnTouchListener(this);
		btn5.setOnClickListener(this);
		btn5.setOnTouchListener(this);
		btn6.setOnClickListener(this);
		btn6.setOnTouchListener(this);
		btn7.setOnClickListener(this);
		btn7.setOnTouchListener(this);
		btn8.setOnClickListener(this);
		btn8.setOnTouchListener(this);
		btn9.setOnClickListener(this);
		btn9.setOnTouchListener(this);
		btnA.setOnClickListener(this);
		btnA.setOnTouchListener(this);
		btnB.setOnClickListener(this);
		btnB.setOnTouchListener(this);
		btnC.setOnClickListener(this);
		btnC.setOnTouchListener(this);
		btnD.setOnClickListener(this);
		btnD.setOnTouchListener(this);
		btnE.setOnClickListener(this);
		btnE.setOnTouchListener(this);
		btnF.setOnClickListener(this);
		btnF.setOnTouchListener(this);
		btnBS.setOnClickListener(this);
		btnBS.setOnTouchListener(this);
		btnClear.setOnClickListener(this);
		btnClear.setOnTouchListener(this);

		btnPlus.setOnClickListener(this);
		btnPlus.setOnTouchListener(this);
		btnMinus.setOnClickListener(this);
		btnMinus.setOnTouchListener(this);
		btnTimes.setOnClickListener(this);
		btnTimes.setOnTouchListener(this);
		btnDivide.setOnClickListener(this);
		btnDivide.setOnTouchListener(this);
		btnSign.setOnClickListener(this);
		btnSign.setOnTouchListener(this);
		btnShiftL.setOnClickListener(this);
		btnShiftL.setOnTouchListener(this);
		btnShiftR.setOnClickListener(this);
		btnShiftR.setOnTouchListener(this);
		btnEquals.setOnClickListener(this);
		btnEquals.setOnTouchListener(this);

		btnAND.setOnClickListener(this);
		btnAND.setOnTouchListener(this);
		btnOR.setOnClickListener(this);
		btnOR.setOnTouchListener(this);
		btnNOT.setOnClickListener(this);
		btnNOT.setOnTouchListener(this);
		btnXOR.setOnClickListener(this);
		btnXOR.setOnTouchListener(this);

		radio_dec.setOnClickListener(radio_listener);
		radio_dec.setOnTouchListener(this);
		radio_hex.setOnClickListener(radio_listener);
		radio_hex.setOnTouchListener(this);
		radio_bin.setOnClickListener(radio_listener);
		radio_bin.setOnTouchListener(this);
	}
	

	
	// capture onTouch to play around with the events etc
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
	
}