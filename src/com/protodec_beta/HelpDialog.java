package com.protodec_beta;

import com.protodecbeta.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/** Class extends standard Dialog */
/** Implement onClickListener to dismiss dialog when OK Button is pressed */
public class HelpDialog extends Dialog implements OnClickListener {
	ImageButton okButton;

	public HelpDialog(Context context) {
		super(context);
		/** 'Window.FEATURE_NO_TITLE' - Used to hide the title */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.helpdialog);
		okButton = (ImageButton) findViewById(R.id.Button01);
		okButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		/** When OK Button is clicked, dismiss the dialog */
		if (v == okButton)
			dismiss();
	}

}
