package com.protodec_beta;

import java.math.BigInteger;
import java.text.NumberFormat;

import com.protodec_beta.CustomizeDialog;
import com.protodec_beta.HelpDialog;
import com.protodec_beta.SetPreference;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
	//private TextView lbldecimal, lblhex, lblbinary;
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
	private enum calculationTypeEnum {
		PLUS_CALC, MINUS_CALC, MULTIPLY_CALC, DIVIDE_CALC, AND_CALC, OR_CALC, NOT_CALC, SHIFT_R_CALC, SHIFT_L_CALC, NO_OP_CALC
	}
	private calculationTypeEnum currentCalculationOperation;
	private boolean calculationOperationInProgress;
	private	boolean testFlag1, testFlag2, testFlag3, testFlag4, testFlag5;
	
	// Member Variables for Calculation and output/input
	private String decstring, hexstring, binstring;
	BigInteger uint64Instance, uint64Instance_SaveValue;
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
		// Action Per Button Press
		switch(v.getId()) {
		case R.id.btnClear: 	performBtnClearActions();		  break;
		case R.id.btnBS: 		performBtnBackSpaceActions(); 	  break;
		case R.id.btnPlus:		performBtnPlusActions(); 		  break;
		case R.id.btnMinus:		performBtnMinusActions(); 		  break;
		case R.id.btnTimes:		performBtnTimesActions(); 		  break;
		case R.id.btnDivide:	performBtnDivideActions(); 		  break;
		case R.id.btnOR:		performBtnORActions(); 			  break;
		case R.id.btnAND:		performBtnANDActions(); 		  break;
		case R.id.btnXOR:		performBtnXORActions(); 		  break;
		case R.id.btnSign:		performBtnSignActions(); 		  break;
		case R.id.btnNOT:		performBtnNOTActions(); 		  break;
		case R.id.btnShiftL:	performBtnShiftLActions();		  break;
		case R.id.btnShiftR:	performBtnShiftRActions();		  break;	
		case R.id.btnEquals:	performBtnEqualActions();		  break;
		default: 				performDigitButtonPressAction(v); break;
		}
		
		// Actions complete update UI Output Elements
		displayValues();
	}
	// ------------- Input Utility functions -------------------
	// ------------- Button Click Dispatchers ------------------
	private void performBtnClearActions() {
		// User pressed clear button
		// Clear current values and the UI
		// Remove the Op Status and Flag
		btnClear.setText("Clear");
		currentCalculationOperation = calculationTypeEnum.NO_OP_CALC;
		calculationOperationInProgress = false;
		
		// Clear the tracking current value in the system
		uint64Instance = BigInteger.ZERO;
		uint64Instance_SaveValue = BigInteger.ZERO;
		
		clearDisp();
		setCalcKeys();
		setLogicKeys();	
	}
	private void performBtnBackSpaceActions() {
		switch(currentMode){
		case DECIMAL_MODE:
			if(decstring.length()==1) {decstring = "0";}
			else if(decstring.length()>0) {decstring = decstring.substring(0, decstring.length()-1);}
			uint64Instance = new BigInteger(decstring); //TODO find something besides "new" to do this I think Java GC takes care of this though
			break;
		case HEX_MODE:
			if(hexstring.length()==1) {hexstring = "0";}
			else if(hexstring.length()>0) {hexstring = hexstring.substring(0, hexstring.length()-1);}
			uint64Instance = new BigInteger(hexstring, 16); //TODO find something besides "new" to do this I think Java GC takes care of this though
			break;
		case BINARY_MODE:
			if(binstring.length()==1) {binstring = "0";}
			else if(binstring.length()>0) {binstring = binstring.substring(0, binstring.length()-1);}
			uint64Instance = new BigInteger(binstring, 2); //TODO find something besides "new" to do this I think Java GC takes care of this though
			break;
		}
	}
	private void performBtnPlusActions() {
		// Configure Button Interface for Current Operation
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
		
		// Setup Operation State and Operation Type
		currentCalculationOperation = calculationTypeEnum.PLUS_CALC;
		calculationOperationInProgress = true;
		
		// Save off Previous Value for Operation
		uint64Instance_SaveValue = uint64Instance;
		
	}
	private void performBtnMinusActions() {
		// Configure Button Interface for Current Operation
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
		
		// Setup Operation State and Operation Type
		currentCalculationOperation = calculationTypeEnum.MINUS_CALC;
		calculationOperationInProgress = true;
		
		// Save off Previous Value for Operation
		uint64Instance_SaveValue = uint64Instance;
	}
	private void performBtnTimesActions() {
		// Configure Button Interface for Current Operation
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
		
		// Setup Operation State and Operation Type
		currentCalculationOperation = calculationTypeEnum.MULTIPLY_CALC;
		calculationOperationInProgress = true;
		
		// Save off Previous Value for Operation
		uint64Instance_SaveValue = uint64Instance;
	}
	private void performBtnDivideActions() {
		// Configure Button Interface for Current Operation
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
		
		// Setup Operation State and Operation Type
		currentCalculationOperation = calculationTypeEnum.DIVIDE_CALC;
		calculationOperationInProgress = true;
		
		// Save off Previous Value for Operation
		uint64Instance_SaveValue = uint64Instance;
	}
	private void performBtnORActions() {
		// Configure Button Interface for Current Operation
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
		
		// Setup Operation State and Operation Type
		currentCalculationOperation = calculationTypeEnum.OR_CALC;
		calculationOperationInProgress = true;
		
		// Save off Previous Value for Operation
		uint64Instance_SaveValue = uint64Instance;
	}
	private void performBtnANDActions() {
		// Configure Button Interface for Current Operation
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
		
		// Setup Operation State and Operation Type
		currentCalculationOperation = calculationTypeEnum.AND_CALC;
		calculationOperationInProgress = true;
		
		// Save off Previous Value for Operation
		uint64Instance_SaveValue = uint64Instance;
	}
	private void performBtnShiftLActions() {
		// Configure Button Interface for Current Operation
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
		
		// Setup Operation State and Operation Type
		currentCalculationOperation = calculationTypeEnum.SHIFT_L_CALC;
		calculationOperationInProgress = true;
		
		// Save off Previous Value for Operation
		uint64Instance_SaveValue = uint64Instance;
	}
	private void performBtnShiftRActions() {
		// Configure Button Interface for Current Operation
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

		// Setup Operation State and Operation Type
		currentCalculationOperation = calculationTypeEnum.SHIFT_R_CALC;
		calculationOperationInProgress = true;
		
		// Save off Previous Value for Operation
		uint64Instance_SaveValue = uint64Instance;
	}
	private void performBtnXORActions() {
		// Configure Button Interface for Current Operation
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
		
		// Setup Operation State and Operation Type
		currentCalculationOperation = calculationTypeEnum.SHIFT_R_CALC;
		calculationOperationInProgress = true;
		
		// Save off Previous Value for Operation
		uint64Instance_SaveValue = uint64Instance;
	}
	private void performBtnSignActions() {
		uint64Instance = uint64Instance.negate();
	}
	private void performBtnNOTActions() {
		uint64Instance = uint64Instance.not();
	}
	private void performBtnEqualActions() {
		switch(currentCalculationOperation)
		{
		case PLUS_CALC:			calcBtnPlusActions();  		 break;
		case MINUS_CALC:		calcBtnMinusActions(); 		 break;
		case MULTIPLY_CALC:		calcBtnTimesActions(); 		 break;
		case DIVIDE_CALC:		calcBtnDivideActions(); 	 break;
		case OR_CALC:			calcBtnORActions(); 		 break;
		case AND_CALC:			calcBtnANDActions(); 		 break;
		case SHIFT_L_CALC:		calcBtnShiftLActions();		 break;
		case SHIFT_R_CALC:		calcBtnShiftRActions();	 	 break;	
		}
		
		// Re-enable the keys
		btnEquals.setEnabled(false);
		
		currentCalculationOperation = calculationTypeEnum.NO_OP_CALC;
		calculationOperationInProgress = false;
	}
	// -------------- Operation Drivers -------------------------
	// NHAT CHECK THIS!!!
	private void calcBtnPlusActions() {
		btnClear.setText("Clear");
		btnPlus.setVisibility(View.VISIBLE);
		btnMinus.setVisibility(View.VISIBLE);
		btnTimes.setVisibility(View.VISIBLE);
		btnDivide.setVisibility(View.VISIBLE);
		btnAND.setVisibility(View.VISIBLE);
		btnOR.setVisibility(View.VISIBLE);
		btnNOT.setVisibility(View.VISIBLE);
		btnXOR.setVisibility(View.VISIBLE);
		btnShiftL.setVisibility(View.VISIBLE);
		btnShiftR.setVisibility(View.VISIBLE);
		btnEquals.setVisibility(View.INVISIBLE);
		btnEquals.setEnabled(false);
		btnPlus.setEnabled(true);
		
		uint64Instance = uint64Instance_SaveValue.add(uint64Instance);
	}
	private void calcBtnMinusActions() {
		btnClear.setText("Clear");
		btnPlus.setVisibility(View.VISIBLE);
		btnMinus.setVisibility(View.VISIBLE);
		btnTimes.setVisibility(View.VISIBLE);
		btnDivide.setVisibility(View.VISIBLE);
		btnAND.setVisibility(View.VISIBLE);
		btnOR.setVisibility(View.VISIBLE);
		btnNOT.setVisibility(View.VISIBLE);
		btnXOR.setVisibility(View.VISIBLE);
		btnShiftL.setVisibility(View.VISIBLE);
		btnShiftR.setVisibility(View.VISIBLE);
		btnEquals.setVisibility(View.INVISIBLE);
		btnEquals.setEnabled(false);
		btnMinus.setEnabled(true);
		
		uint64Instance = uint64Instance_SaveValue.subtract(uint64Instance);
	}
	private void calcBtnTimesActions() {
		btnClear.setText("Clear");
		btnPlus.setVisibility(View.VISIBLE);
		btnMinus.setVisibility(View.VISIBLE);
		btnTimes.setVisibility(View.VISIBLE);
		btnDivide.setVisibility(View.VISIBLE);
		btnAND.setVisibility(View.VISIBLE);
		btnOR.setVisibility(View.VISIBLE);
		btnNOT.setVisibility(View.VISIBLE);
		btnXOR.setVisibility(View.VISIBLE);
		btnShiftL.setVisibility(View.VISIBLE);
		btnShiftR.setVisibility(View.VISIBLE);
		btnEquals.setVisibility(View.INVISIBLE);
		btnEquals.setEnabled(false);
		btnTimes.setEnabled(true);
		
		uint64Instance = uint64Instance_SaveValue.multiply(uint64Instance);
	}
	private void calcBtnDivideActions() {
		btnClear.setText("Clear");
		btnPlus.setVisibility(View.VISIBLE);
		btnMinus.setVisibility(View.VISIBLE);
		btnTimes.setVisibility(View.VISIBLE);
		btnDivide.setVisibility(View.VISIBLE);
		btnAND.setVisibility(View.VISIBLE);
		btnOR.setVisibility(View.VISIBLE);
		btnNOT.setVisibility(View.VISIBLE);
		btnXOR.setVisibility(View.VISIBLE);
		btnShiftL.setVisibility(View.VISIBLE);
		btnShiftR.setVisibility(View.VISIBLE);
		btnEquals.setVisibility(View.INVISIBLE);
		btnEquals.setEnabled(false);
		btnDivide.setEnabled(true);
		
		if(uint64Instance==BigInteger.ZERO) {
			Toast toast = Toast.makeText(getApplicationContext(), "Cannot divide by zero!", Toast.LENGTH_SHORT);
			toast.show();
		}
		else {
			uint64Instance = uint64Instance_SaveValue.divide(uint64Instance);
		}
	}
	private void calcBtnORActions() {
		btnClear.setText("Clear");
		btnPlus.setVisibility(View.VISIBLE);
		btnMinus.setVisibility(View.VISIBLE);
		btnTimes.setVisibility(View.VISIBLE);
		btnDivide.setVisibility(View.VISIBLE);
		btnAND.setVisibility(View.VISIBLE);
		btnOR.setVisibility(View.VISIBLE);
		btnNOT.setVisibility(View.VISIBLE);
		btnXOR.setVisibility(View.VISIBLE);
		btnShiftL.setVisibility(View.VISIBLE);
		btnShiftR.setVisibility(View.VISIBLE);
		btnEquals.setVisibility(View.INVISIBLE);
		btnEquals.setEnabled(false);
		btnOR.setEnabled(true);
		
		uint64Instance = uint64Instance_SaveValue.or(uint64Instance);
	}
	private void calcBtnANDActions() {
		btnClear.setText("Clear");
		btnPlus.setVisibility(View.VISIBLE);
		btnMinus.setVisibility(View.VISIBLE);
		btnTimes.setVisibility(View.VISIBLE);
		btnDivide.setVisibility(View.VISIBLE);
		btnAND.setVisibility(View.VISIBLE);
		btnOR.setVisibility(View.VISIBLE);
		btnNOT.setVisibility(View.VISIBLE);
		btnXOR.setVisibility(View.VISIBLE);
		btnShiftL.setVisibility(View.VISIBLE);
		btnShiftR.setVisibility(View.VISIBLE);
		btnEquals.setVisibility(View.INVISIBLE);
		btnEquals.setEnabled(false);
		btnAND.setEnabled(true);
		
		uint64Instance = uint64Instance_SaveValue.and(uint64Instance);
	}
	private void calcBtnShiftLActions() {
		btnClear.setText("Clear");
		btnPlus.setVisibility(View.VISIBLE);
		btnMinus.setVisibility(View.VISIBLE);
		btnTimes.setVisibility(View.VISIBLE);
		btnDivide.setVisibility(View.VISIBLE);
		btnAND.setVisibility(View.VISIBLE);
		btnOR.setVisibility(View.VISIBLE);
		btnNOT.setVisibility(View.VISIBLE);
		btnXOR.setVisibility(View.VISIBLE);
		btnShiftL.setVisibility(View.VISIBLE);
		btnShiftR.setVisibility(View.VISIBLE);
		btnEquals.setVisibility(View.INVISIBLE);
		btnEquals.setEnabled(false);
		btnShiftL.setEnabled(true);
		
		uint64Instance = uint64Instance_SaveValue.shiftLeft(1);  // TODO add context menu for multiple shifts
	}
	private void calcBtnShiftRActions() {
		btnClear.setText("Clear");
		btnPlus.setVisibility(View.VISIBLE);
		btnMinus.setVisibility(View.VISIBLE);
		btnTimes.setVisibility(View.VISIBLE);
		btnDivide.setVisibility(View.VISIBLE);
		btnAND.setVisibility(View.VISIBLE);
		btnOR.setVisibility(View.VISIBLE);
		btnNOT.setVisibility(View.VISIBLE);
		btnXOR.setVisibility(View.VISIBLE);
		btnShiftL.setVisibility(View.VISIBLE);
		btnShiftR.setVisibility(View.VISIBLE);
		btnEquals.setVisibility(View.INVISIBLE);
		btnEquals.setEnabled(false);
		btnShiftR.setEnabled(true);
		
		uint64Instance = uint64Instance_SaveValue.shiftRight(1);  // TODO add context menu for multiple shifts
	}
	private void performDigitButtonPressAction(View v) {
		//If here we are dealing with a number digit
		char c = getButtonValue(v); // Grab Char Version of Button Press
		
		// If we are in the middle of an operation, cancel it
		if(currentCalculationOperation!=calculationTypeEnum.NO_OP_CALC) {
			clearDisp();
			//calculationOperationInProgress = false;
			//currentCalculationOperation = calculationTypeEnum.NO_OP_CALC;
		}
		
		switch(currentMode) {
		case DECIMAL_MODE:
			decstring = decstring + c;
			uint64Instance = new BigInteger(decstring, 10);
			break;
		case HEX_MODE:
			hexstring = hexstring + c;
			uint64Instance = new BigInteger(hexstring, 16);
			break;
		case BINARY_MODE:
			binstring = binstring + c;
			uint64Instance = new BigInteger(binstring, 2);
			break;
		}
	}
	
	// ----------------- Display Output Drivers  --------------------
	//  Drives the various UI elements need to display output to the user
	private void displayValues() {
		String parsedNumericString;
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		int orient = display.getOrientation();

		// Dumping config settings for debug purposes
		String configStatus =  String.format("1: %b, 2: %b, 3: %b, 4: %b, 5: %b", testFlag1, testFlag2, testFlag3, testFlag4, testFlag5);
		txtTestParse.setText(configStatus);
		txtTestParse.setVisibility(View.VISIBLE);
		
		// Dumping a 64bit HEX padded representation of the current value to a textview for debug purposes
		txtInformational.setText(buildPadded64BitHexString());
		txtInformational.setVisibility(View.VISIBLE);
		
		// =====================  OUTPUTTING FIELDS ====================
		//  ------------- DEC -----------------
		decstring = uint64Instance.toString(10);
		parsedNumericString = NumberFormat.getInstance().format(uint64Instance);
		txtdecimal.setText(parsedNumericString);
		
		//  ------------- HEX -----------------
		hexstring = uint64Instance.toString(16);
		txthex.setText(padString(uint64Instance.toString(16).toUpperCase(),8)); // Pad to the word

		//  ------------- BIN -----------------
		binstring = uint64Instance.toString(2);
		txtbinary.setText(padString(uint64Instance.toString(2).toUpperCase(),8)); // Pad to the byte

		// Now that we have the strings lets scale the fields based on the size and the orientation
		//  ------------- DEC -----------------
		//  might not be needed
		//  ------------- HEX -----------------
		//  might not be needed
		//  ------------- BIN -----------------
		if ((orient==0)) 
		{
			if ((txtbinary.length()>=22)) {
				txtbinary.setTextSize(10);
			}				
			else{
				txtbinary.setTextSize(18);
			}
		}
		else {
			if ((txtbinary.length()>=42)){
				txtbinary.setTextSize(10);
			}
			else{
				txtbinary.setTextSize(18);
			}
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
		//uint64Instance = BigInteger.valueOf(decvalue);

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
		//    that would probably be more runtime expensive though and extracting text 
		//    from the button elements then massaging
		//    then into values I can use probably would be worse
		char c = '0';	
		switch(v.getId()) {
		case R.id.btn0:	c = '0'; break;
		case R.id.btn1:	c = '1'; break;
		case R.id.btn2: c = '2'; break;
		case R.id.btn3:	c = '3'; break;
		case R.id.btn4:	c = '4'; break;
		case R.id.btn5:	c = '5'; break;
		case R.id.btn6:	c = '6'; break;
		case R.id.btn7:	c = '7'; break;
		case R.id.btn8:	c = '8'; break;
		case R.id.btn9:	c = '9'; break;
		case R.id.btnA:	c = 'A'; break;
		case R.id.btnB:	c = 'B'; break;
		case R.id.btnC:	c = 'C'; break;
		case R.id.btnD:	c = 'D'; break;
		case R.id.btnE:	c = 'E'; break;
		case R.id.btnF:	c = 'F'; break;
		}
		return c;
	}
	
	// Handles the click of the radio buttons   
	private OnClickListener radio_listener = new OnClickListener() {
		public void onClick(View v){
			// Perform action on clicks
			switch(v.getId()) {
			case R.id.radio_dec: setDecButtons(); currentMode = modeTypeEnum.DECIMAL_MODE; break;
			case R.id.radio_hex: setHexButtons(); currentMode = modeTypeEnum.HEX_MODE; break;
			case R.id.radio_bin: setBinButtons(); currentMode = modeTypeEnum.BINARY_MODE; break;
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
		editor.putString("uint64", uint64Instance.toString());
		editor.putString("uint64_save", uint64Instance_SaveValue.toString());
		editor.putInt("currentMode", mode);
		editor.commit();
	}
	protected void onResume() {

		super.onResume();

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		uint64Instance = new BigInteger(preferences.getString("uint64", "0"));
		uint64Instance_SaveValue= new BigInteger(preferences.getString("uint64_save", "0"));
		tempInt = preferences.getInt("currentMode", 0);

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
		String tempst = def_prefs.getString("backgroundflag", "1");
	
		tempst = def_prefs.getString("userbaseflag", "8");
		tempst = def_prefs.getString("binbitsflag", "0");
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
		txtdecimal = (TextView)findViewById(R.id.txtdecimal);
		// Hex Label and Output
		txthex = (TextView)findViewById(R.id.txthex);
		// Binary Label and Output
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