package com.protodec_beta;

import com.protodecbeta.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SetPreference extends PreferenceActivity {
 @Override
   protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
       addPreferencesFromResource(R.layout.mypreference);
   }
}

