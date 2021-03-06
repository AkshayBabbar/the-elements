package com.idkjava.thelements.custom;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.idkjava.thelements.R;
import com.idkjava.thelements.proto.Messages.CustomElement;

public class CustomElementActivity extends TabActivity
{
	//TODO: 1) Depending on the Intent, ACTION_EDIT or ACTION_NEW, set up the UI with
	//either default or loaded data, and allow the user to edit and save
	// 2) Make sure no duplicate names can be entered.
	/*
	 * All elements held in same file, accessed by name.
	 */
	public String oldFilename;
	public boolean newElement;
	public CustomElement.Builder mCustomElementBuilder;
	
	// Variables for passing data to basic activity
	public ArrayList<Integer> collisions;
	public ArrayList<Integer> specials;
	public ArrayList<Integer> specialVals;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Try loading the element
		oldFilename = getIntent().getStringExtra("filename");
		if(oldFilename != null)
		{
		    newElement = false;
		    try {
		        mCustomElementBuilder = CustomElement.newBuilder(
		                CustomElement.parseFrom(new FileInputStream(oldFilename)));
		    }
		    catch (IOException e) {
				// If loading fails, we need to quit and show a message
				Toast.makeText(getApplicationContext(), R.string.ce_load_failed_msg, Toast.LENGTH_LONG).show();
				finish();
			}
		}
		else
		{
            newElement = true;
			mCustomElementBuilder = CustomElement.newBuilder();
		}
		
		// Set the content view
		setContentView(R.layout.custom_element_activity);
		// Set up some variables
		Intent intent;
		TabSpec spec;
		TabHost tabHost = getTabHost();
		Resources res = getResources();
		// Create the tabs
		intent = new Intent(CustomElementActivity.this, CustomElementBasicActivity.class);
		spec = tabHost.newTabSpec("basic").setIndicator(res.getString(R.string.basic_tab)).setContent(intent);
		tabHost.addTab(spec);
		intent = new Intent(CustomElementActivity.this, CustomElementAdvancedActivity.class);
		spec = tabHost.newTabSpec("advanced").setIndicator(res.getString(R.string.advanced_tab)).setContent(intent);
		tabHost.addTab(spec);
		tabHost.setCurrentTab(0);
	}
}
