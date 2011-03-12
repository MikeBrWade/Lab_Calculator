package com.protodec_beta;

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
	private TextView lbldecimal;
	private TextView txtdecimal;
	private TextView lblhex;
	private TextView txthex;
	private TextView lblbinary;
	private TextView txtbinary;
	private TextView txtrotate;
	private TextView txtcomp;
	private TextView txtprecision;
	private Button btn0;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;
	private Button btn7;
	private Button btn8;
	private Button btn9;
	private Button btnA;
	private Button btnB;
	private Button btnC;
	private Button btnD;
	private Button btnE;
	private Button btnF;
	private Button btnClear;
	private Button btnPlus;
	private Button btnMinus;
	private Button btnTimes;
	private Button btnDivide;
	private Button btnShiftL;
	private Button btnShiftR;
	private Button btnEquals;
	private Button btnSign;
	private Button btnBS;
	private Button btnAND;
	private Button btnOR;
	private Button btnNOT;
	private Button btnXOR;
	private RadioButton radio_dec;
	private RadioButton radio_hex;
	private RadioButton radio_bin;
	private long decvalue;
	private long decsave;
	private char modeflag;
	private String decstring;
	private String hexstring;
	private String binstring;
	private int binbitsflag;
	private boolean calcflag;
	private boolean logicflag;
	private boolean zerosflag;
	private boolean shiftflag;
	private int secondflag;
	private int calcstatus;
	
	private	boolean testFlag1;
	private	boolean testFlag2;
	private	boolean testFlag3;
	private	boolean testFlag4;
	private	boolean testFlag5;

	private String tempstring;
	RelativeLayout mScreen;
	//TableLayout mScreen;


	/** Called when the activity is first created. */
	@Override
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
		

		//registerForContextMenu(btnNOT);

		mScreen = (RelativeLayout) findViewById(R.id.myScreen);

	}
	// A few functions to setup the initial view and objects
	private void linkTextFieldsIntoControlObjects()
	{
		lbldecimal = (TextView)findViewById(R.id.lbldecimal);
		txtdecimal = (TextView)findViewById(R.id.txtdecimal);
		lblhex = (TextView)findViewById(R.id.lblhex);
		txthex = (TextView)findViewById(R.id.txthex);
		lblbinary = (TextView)findViewById(R.id.lblbinary);
		txtbinary = (TextView)findViewById(R.id.txtbinary);
		txtrotate = (TextView)findViewById(R.id.txtrotate);
		txtcomp = (TextView)findViewById(R.id.txtcomp);
		txtprecision = (TextView)findViewById(R.id.txtprecision);
	}
	private void linkButtonObjects()
	{
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
	
	// Flipping the activate/deactivate of the number pad
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
	
	private void setCalcKeys() {
		// Grabbing the current display instance and the orientation flag
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		int orient = display.getOrientation();
		
		// Depending on the orientation and the presence of a current calculation, hide/show the input elements
		if((calcflag==true) & (orient==0)) 
		{
			if (secondflag==0) {
				btnPlus.setVisibility(View.VISIBLE);
				btnMinus.setVisibility(View.VISIBLE);
				btnTimes.setVisibility(View.VISIBLE);
				btnDivide.setVisibility(View.VISIBLE);
				btnEquals.setVisibility(View.VISIBLE);
				btnPlus.setEnabled(true);
				btnMinus.setEnabled(true);
				btnTimes.setEnabled(true);
				btnDivide.setEnabled(true);
				btnEquals.setEnabled(false);
			}
			else if(secondflag==1) {
				btnPlus.setVisibility(View.VISIBLE);
				btnMinus.setVisibility(View.INVISIBLE);
				btnTimes.setVisibility(View.INVISIBLE);
				btnDivide.setVisibility(View.INVISIBLE);
				btnEquals.setVisibility(View.VISIBLE);
				btnEquals.setEnabled(true);
				btnPlus.setEnabled(false);
			}
			else if(secondflag==2) {
				btnPlus.setVisibility(View.INVISIBLE);
				btnMinus.setVisibility(View.VISIBLE);
				btnTimes.setVisibility(View.INVISIBLE);
				btnDivide.setVisibility(View.INVISIBLE);
				btnEquals.setVisibility(View.VISIBLE);
				btnEquals.setEnabled(true);
				btnMinus.setEnabled(false);
			}
			else if(secondflag==3) {
				btnPlus.setVisibility(View.INVISIBLE);
				btnMinus.setVisibility(View.INVISIBLE);
				btnTimes.setVisibility(View.VISIBLE);
				btnDivide.setVisibility(View.INVISIBLE);
				btnEquals.setVisibility(View.VISIBLE);
				btnEquals.setEnabled(true);
				btnTimes.setEnabled(false);
			}
			else if(secondflag==4) {
				btnPlus.setVisibility(View.INVISIBLE);
				btnMinus.setVisibility(View.INVISIBLE);
				btnTimes.setVisibility(View.INVISIBLE);
				btnDivide.setVisibility(View.VISIBLE);
				btnEquals.setVisibility(View.VISIBLE);
				btnEquals.setEnabled(true);
				btnDivide.setEnabled(false);
			}
		}
		else {
			btnPlus.setVisibility(View.INVISIBLE);
			btnMinus.setVisibility(View.INVISIBLE);
			btnTimes.setVisibility(View.INVISIBLE);
			btnDivide.setVisibility(View.INVISIBLE);
			if((logicflag==false) | (orient!=0)) {
				btnEquals.setVisibility(View.INVISIBLE);
			}
		}
	}
	
	private void setLogicKeys() {
		// Grabbing the current display instance and the orientation flag
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		int orient = display.getOrientation();
		//Depending on the orientation and the presence of a current logic operation, hide/show the input elements
		if((logicflag==true) & (orient==0)) {
			btnAND.setVisibility(View.VISIBLE);
			btnOR.setVisibility(View.VISIBLE);
			btnNOT.setVisibility(View.VISIBLE);
			btnXOR.setVisibility(View.VISIBLE);
			btnShiftL.setVisibility(View.VISIBLE);
			btnShiftR.setVisibility(View.VISIBLE);
			btnEquals.setVisibility(View.VISIBLE);
			btnAND.setEnabled(true);
			btnOR.setEnabled(true);
			btnNOT.setEnabled(true);
			btnXOR.setEnabled(true);
			btnShiftL.setEnabled(true);
			btnShiftR.setEnabled(true);
			btnEquals.setEnabled(false);
		}
		else {
			btnAND.setVisibility(View.INVISIBLE);
			btnOR.setVisibility(View.INVISIBLE);
			btnNOT.setVisibility(View.INVISIBLE);
			btnXOR.setVisibility(View.INVISIBLE);
			btnShiftL.setVisibility(View.INVISIBLE);
			btnShiftR.setVisibility(View.INVISIBLE);
			if ((calcflag==false) | (orient!=0)) {
				btnEquals.setVisibility(View.INVISIBLE);
			}
			if (orient!=0) {
				btnNOT.setVisibility(View.VISIBLE);
			}
		}
	}
	
	//This is used to capture the menu action and create a menu instance
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	// capture onTouch to play around with the events etc
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
	
	//======================================================= 
	//Captures all clicks from various buttons and dispatches their commands
	public void onClick(View v) {   
		char c;
		String savstring;
		long decnew;
		long decprod;

		if(v==btnClear) {
			if (secondflag>0) {
				decvalue = decsave;
				displayValues();
			}
			else {
				clearDisp();
			}
			secondflag=0;
			calcstatus=0;
			setCalcKeys();
			setLogicKeys();
			btnClear.setText("Clear");
		}
		else if(v==btnBS) {
			if(modeflag=='d') {
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
			}
			else if(modeflag=='h') {
				if(hexstring.length()==1) {
					hexstring = "0";	
				}
				else if(hexstring.length()>0) {
					hexstring = hexstring.substring(0, hexstring.length()-1);
				}
				decvalue = Long.parseLong(hexstring, 16);
			}
			else if(modeflag=='b') {
				if(binstring.length()==1) {
					binstring = "0";	
				}
				else if(binstring.length()>0) {
					binstring = binstring.substring(0, binstring.length()-1);
				}
				decvalue = Long.parseLong(binstring, 2);   			
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

			if(modeflag=='d') {
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
				displayValues();
			}
			else if(modeflag=='h') {
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
				displayValues();
			}
			else if(modeflag=='b') {
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
				displayValues();
			}
		}
	}
	
	// Used to clear all the fields as well as the labels and reset the buttons
	private void clearDisp() {
		txtdecimal.setText("0");
		txthex.setText("0");
		txtbinary.setText("0");
		decvalue = 0;
		decstring = "0";
		hexstring = "0";
		binstring = "0";
		txtrotate.setVisibility(View.INVISIBLE);
		txtcomp.setVisibility(View.INVISIBLE);
		txtprecision.setVisibility(View.INVISIBLE);
		Resources res = getResources();
		int color = res.getColor(R.color.black);
		txtbinary.setTextColor(color);
	}
	
	//========================================================
	private void displayValues() {
		String parsedNumericString;
		int color;
		int kp, k;

		// just playing with settings value retrieval
		StringBuilder s = new StringBuilder(100);
		s.append("1: ");
		s.append(testFlag1);
		s.append(" 2: ");
		s.append(testFlag2);
		s.append(" 3: ");
		s.append(testFlag3);
		s.append(" 4: ");
		s.append(testFlag4);
		s.append(" 5: ");
		s.append(testFlag4);
		
		txtrotate.setText(s.toString());
		
		txtrotate.setVisibility(View.VISIBLE);
		
		//  Display decvalue on the 4 output fields
		
		decstring = Long.toString(decvalue);
		hexstring = Long.toHexString(decvalue);
		binstring = Long.toBinaryString(decvalue);

		//  Decimal	
		parsedNumericString = NumberFormat.getInstance().format(decvalue);
		txtdecimal.setText(parsedNumericString);

		//  Hex	
		parsedNumericString = Long.toHexString(decvalue);
		parsedNumericString = parsedNumericString.toUpperCase();

		if (decvalue<128 && decvalue>=-128) {
			if (parsedNumericString.length()>2) {
				parsedNumericString = parsedNumericString.substring(parsedNumericString.length()-2);
			}
		}
		else if (decvalue<32768 && decvalue>=-32768) {
			if (parsedNumericString.length()>4) {
				parsedNumericString = parsedNumericString.substring(parsedNumericString.length()-4);
			}
		}
		else if (decvalue/2 < 1073741824 && decvalue/2 >=-1073741824) {
			if (parsedNumericString.length()>8) {
				parsedNumericString = parsedNumericString.substring(parsedNumericString.length()-8);
			}
		}

		txthex.setText(padString(parsedNumericString,4));

		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		int orient = display.getOrientation();
		Resources res = getResources();

		//  Binary    
		parsedNumericString = Long.toBinaryString(decvalue);
		if (binbitsflag==0) {
			if (decvalue<16 && decvalue>=0) {
				if (decvalue != 0 && zerosflag==true) {
					txtprecision.setText("(4-bit)");
					txtprecision.setVisibility(View.VISIBLE);
					kp = 4 - parsedNumericString.length();
					for (k=1; k<=kp ; ++k) {
						parsedNumericString = "0" + parsedNumericString;
					}
				}
				else {
					txtprecision.setVisibility(View.INVISIBLE);
				}
			}
			else if (decvalue<256 && decvalue>=-128) {
				if (parsedNumericString.length()>8) {
					parsedNumericString = parsedNumericString.substring(parsedNumericString.length()-8);
					txtprecision.setText("(8-bit)");
					txtprecision.setVisibility(View.VISIBLE);
				}
				else {
					if (decvalue != 0 && zerosflag==true) {
						txtprecision.setText("(8-bit)");
						txtprecision.setVisibility(View.VISIBLE);
						kp = 8 - parsedNumericString.length();
						for (k=1; k<=kp ; ++k) {
							parsedNumericString = "0" + parsedNumericString;
						}
					}
					else {
						txtprecision.setVisibility(View.INVISIBLE);
					}
				}
			}
			else if (decvalue<65536 && decvalue>=-32768) {
				if (parsedNumericString.length()>16) {
					parsedNumericString = parsedNumericString.substring(parsedNumericString.length()-16);
					txtprecision.setText("(16-bit)");
					txtprecision.setVisibility(View.VISIBLE);
				}
				else {
					if (zerosflag==true) {
						txtprecision.setText("(16-bit)");
						txtprecision.setVisibility(View.VISIBLE);
						kp = 16 - parsedNumericString.length();
						for (k=1; k<=kp ; ++k) {
							parsedNumericString = "0" + parsedNumericString;
						}
					}
					else {
						txtprecision.setVisibility(View.INVISIBLE);
					}
				}
			}
			else if (decvalue/4 < 1073741824 && decvalue/2 >=-1073741824) {
				if (parsedNumericString.length()>32) {
					parsedNumericString = parsedNumericString.substring(parsedNumericString.length()-32);
					txtprecision.setText("(32-bit)");
					txtprecision.setVisibility(View.VISIBLE);
				}
				else {
					if (zerosflag==true) {
						txtprecision.setText("(32-bit)");
						txtprecision.setVisibility(View.VISIBLE);
						kp = 32 - parsedNumericString.length();
						for (k=1; k<=kp ; ++k) {
							parsedNumericString = "0" + parsedNumericString;
						}
					}
					else {
						txtprecision.setVisibility(View.INVISIBLE);
					}
				}
			}
			else {
				txtprecision.setVisibility(View.INVISIBLE);
			}
		}
		//	This is user set bits, not auto
		else {
			if (binbitsflag==4) {
				if (decvalue<16 && decvalue>=0) {
					if (decvalue != 0) {
						txtprecision.setText("(4-bit)");
						txtprecision.setVisibility(View.VISIBLE);
						kp = 4 - parsedNumericString.length();
						for (k=1; k<=kp ; ++k) {
							parsedNumericString = "0" + parsedNumericString;
						}
					}
					else {
						txtprecision.setVisibility(View.INVISIBLE);
					}
				}
				else {
					txtprecision.setVisibility(View.INVISIBLE);
				}
			}

			else if (binbitsflag==8) {
				if (decvalue<256 && decvalue>=-128) {
					if (parsedNumericString.length()>8) {
						parsedNumericString = parsedNumericString.substring(parsedNumericString.length()-8);
						txtprecision.setText("(8-bit)");
						txtprecision.setVisibility(View.VISIBLE);
					}
					else {
						if (decvalue != 0) {
							txtprecision.setText("(8-bit)");
							txtprecision.setVisibility(View.VISIBLE);
							kp = 8 - parsedNumericString.length();
							for (k=1; k<=kp ; ++k) {
								parsedNumericString = "0" + parsedNumericString;
							}
						}
						else {
							txtprecision.setVisibility(View.INVISIBLE);
						}
					}
				}
				else {
					txtprecision.setVisibility(View.INVISIBLE);
				}
			}

			else if (binbitsflag==16) {
				if (decvalue<65536 && decvalue>=-32768) {
					if (parsedNumericString.length()>16) {
						parsedNumericString = parsedNumericString.substring(parsedNumericString.length()-16);
						txtprecision.setText("(16-bit)");
						txtprecision.setVisibility(View.VISIBLE);
					}
					else {
						if (decvalue != 0) {
							txtprecision.setText("(16-bit)");
							txtprecision.setVisibility(View.VISIBLE);
							kp = 16 - parsedNumericString.length();
							for (k=1; k<=kp ; ++k) {
								parsedNumericString = "0" + parsedNumericString;
							}
						}
						else {
							txtprecision.setVisibility(View.INVISIBLE);
						}
					}
				}
				else {
					txtprecision.setVisibility(View.INVISIBLE);
				}
			}

			else if (binbitsflag==32) {
				if (decvalue/4 < 1073741824 && decvalue/2 >=-1073741824) {
					if (parsedNumericString.length()>32) {
						parsedNumericString = parsedNumericString.substring(parsedNumericString.length()-32);
						txtprecision.setText("(32-bit)");
						txtprecision.setVisibility(View.VISIBLE);
					}
					else {
						if (decvalue != 0) {
							txtprecision.setText("(32-bit)");
							txtprecision.setVisibility(View.VISIBLE);
							kp = 32 - parsedNumericString.length();
							for (k=1; k<=kp ; ++k) {
								parsedNumericString = "0" + parsedNumericString;
							}
						}
						else {
							txtprecision.setVisibility(View.INVISIBLE);
						}
					}
				}
				else {
					txtprecision.setVisibility(View.INVISIBLE);
				}
			}
		}

		if ((parsedNumericString.length()>=22) & (orient==0)) {
			parsedNumericString = parsedNumericString.substring(parsedNumericString.length()- 22);
			color = res.getColor(R.color.silver);
			txtbinary.setTextColor(color);
			//txtrotate.setVisibility(View.VISIBLE);
		}
		else{
			color = res.getColor(R.color.black);
			txtbinary.setTextColor(color);
			//txtrotate.setVisibility(View.INVISIBLE);
		}
		txtbinary.setText(padString(parsedNumericString,4));
		txtcomp.setVisibility(View.INVISIBLE);
	}
	private String padString(String paddedString, int i) {
		// Pads the string with spaces at the interval i
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
		// There has to be a better way, 
		//    could I pull the value and reinterpret? that would probably be more runtime expensive though
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
				modeflag = 'd';
			}
			else if(v==radio_hex) {
				setHexButtons();
				modeflag = 'h';
			}
			else if(v==radio_bin) {
				setBinButtons();
				modeflag = 'b';
			}
		}
	};
	
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
	//Context menu for ones and two complement    
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
		super.onCreateContextMenu(menu, v, menuInfo);  
		menu.setHeaderTitle("Set complement");  
		menu.add(0, 1, 0, "1s complement (NOT)");  
		menu.add(0, 2, 0, "2s complement (NOT+1)");  
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {  
		if(item.getItemId()==1){
			decvalue = ~decvalue;
			displayValues();
		}
		else if(item.getItemId()==2){
			decvalue = ~decvalue+1;
			displayValues();
		}
		else {
			return false;
		}  
		return true;  
	}
	//This restores screen status after change of orientation    
	@Override
	protected void onPause() {
		super.onPause();
		String mode;

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		mode = String.valueOf(modeflag);
		editor.putLong("decvalue", decvalue);
		editor.putLong("decsave", decsave);
		editor.putString("modeflag", mode);
		editor.putInt("secondflag", secondflag);
		editor.putInt("calcstatus", calcstatus);
		editor.commit();
	}
	@Override
	protected void onResume() {

		super.onResume();

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		decvalue = preferences.getLong("decvalue", 0);
		decsave = preferences.getLong("decsave", 0);
		tempstring = preferences.getString("modeflag", "d");
		secondflag = preferences.getInt("secondflag", 0);
		calcstatus = preferences.getInt("calcstatus", 0);

		modeflag = tempstring.charAt(0);

		SharedPreferences def_prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		testFlag1 = def_prefs.getBoolean("testFlag1", true);
		testFlag2 = def_prefs.getBoolean("testFlag2", true);
		testFlag3 = def_prefs.getBoolean("testFlag3", true);
		testFlag4 = def_prefs.getBoolean("testFlag4", true);
		testFlag5 = def_prefs.getBoolean("testFlag5", true);
		
		
		calcflag = def_prefs.getBoolean("calcflag", true);
		logicflag = def_prefs.getBoolean("logicflag", true);
		zerosflag = def_prefs.getBoolean("zerosflag", true);
		shiftflag = def_prefs.getBoolean("shiftflag", false);
		String tempst = def_prefs.getString("backgroundflag", "1");
	
		tempst = def_prefs.getString("userbaseflag", "8");
		tempst = def_prefs.getString("binbitsflag", "0");
		binbitsflag = Integer.valueOf(tempst);

		switch(modeflag) {
		case('d'):
			radio_dec.setChecked(true);
		setDecButtons();
		break;
		case('h'):
			radio_hex.setChecked(true);
		setHexButtons();
		break;
		case('b'):  
			radio_bin.setChecked(true);
		setBinButtons();
		break;
		default:    
			radio_dec.setChecked(true);
			setDecButtons();
			break;
		}	

		displayValues();
		setCalcKeys();
		setLogicKeys();
	}
}
/* Saved Code Section
//myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
 * 	private Vibrator myVib;
	private boolean vibflag;
	vibflag = def_prefs.getBoolean("vibflag", true);
		//=============================================================    
	//Background color settings, 1 through 4    
	private void setBackColor(int bckflag) {
		int color;
		Resources res = getResources();
		if(bckflag==1) {
			color = res.getColor(R.color.black);
		}
		else if(bckflag==2) {
			color = res.getColor(R.color.gold);
		}
		else if(bckflag==3) {
			color = res.getColor(R.color.steelblue);
		}
		else if(bckflag==4) {
			color = res.getColor(R.color.teal);
		}
		else if(bckflag==5) {
			color = res.getColor(R.color.red);
		}
		else if(bckflag==6) {
			color = res.getColor(R.color.purple);
		}
		else if(bckflag==7) {
			color = res.getColor(R.color.brown);
		}
		else {
			color = res.getColor(R.color.black);
		}
		mScreen.setBackgroundColor(color);
	}
		setBackColor(backgroundflag);
 */
