package com.example.mqtttest2;

import com.example.mqtttest2.R.array;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.os.Build;

public class MessageDetails extends Activity {
	 static final String TAG="MessageDetails";
Spinner spinnerGroup;
EditText txtMessage;
String Message="";
String Topic="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_details);
		
		spinnerGroup=(Spinner)findViewById(R.id.spinnerGroup);
		ArrayAdapter<CharSequence> adapter=ArrayAdapter
				.createFromResource(this, R.array.bloodGroups_array, android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerGroup.setAdapter(adapter);
			txtMessage=(EditText)findViewById(R.id.txtMessage);	
				
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					Topic = extras.getString("TOPIC");
					Message=extras.getString("MESSAGE");
					
				}
				if(Message!="")
				{
					txtMessage.setText(Message);	
				}
				Log.d(TAG,"Tpic is "+Topic);
				if(Topic!=null)
				{
					if(Topic.contains("APositive"))
					{
						spinnerGroup.setSelection(1);
					}
					if(Topic.contains("ANegative"))
					{
						spinnerGroup.setSelection(2);
					}
					if(Topic.contains("BPositive"))
					{
						spinnerGroup.setSelection(3);
					}
					if(Topic.contains("BNegative"))
					{
						spinnerGroup.setSelection(4);
					}
					if(Topic.contains("OPositive"))
					{
						spinnerGroup.setSelection(5);
					}
					if(Topic.contains("ONegative"))
					{
						spinnerGroup.setSelection(6);
					}
					if(Topic.contains("ABPositive"))
					{
						spinnerGroup.setSelection(7);
					}
					if(Topic.contains("ABNegative"))
					{
						spinnerGroup.setSelection(8);
					}
				}
				
		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_message_details,
//					container, false);
//			return rootView;
//		}
//	}

}
