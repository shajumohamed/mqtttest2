package com.example.mqtttest2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import android.os.Build;
import android.preference.PreferenceManager;

public class BloodGroupSelection extends Activity {
	CheckBox chkAPositive;
	CheckBox chkANegative;
	CheckBox chkBPositive;
	CheckBox chkBNegative;
	CheckBox chkOPositive;
	CheckBox chkONegative;
	CheckBox chkABPositive;
	CheckBox chkABNegative;
	Button btnSubscribe;
	 private MQTTService mqttService;
	 SharedPreferences sharedPreferences;
	 String bloodGroupSelectionsPref="";
	 List<String> subscriptionsFromPrefsArray;
	 static final String TAG="BloodGroupSelection";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_blood_group_selection);
		sharedPreferences= PreferenceManager    			
                .getDefaultSharedPreferences(this);
		chkAPositive=(CheckBox)findViewById(R.id.chkAPostive);
		chkANegative=(CheckBox)findViewById(R.id.chkANegative);
		chkBPositive=(CheckBox)findViewById(R.id.chkBPositive);
		chkBNegative=(CheckBox)findViewById(R.id.chkBNegative);
		chkOPositive=(CheckBox)findViewById(R.id.chkOPositive);
		chkONegative=(CheckBox)findViewById(R.id.chkONegative);
		chkABPositive=(CheckBox)findViewById(R.id.chkABPositive);
		chkABNegative=(CheckBox)findViewById(R.id.chkABNegative);
		btnSubscribe=(Button)findViewById(R.id.btnSubscribe);
		bloodGroupSelectionsPref=sharedPreferences.getString("bloodGroupSelections","");
		 Log.d(TAG," selections from pref "+ bloodGroupSelectionsPref);
		
		  if(bloodGroupSelectionsPref!="")
	         {
		
		subscriptionsFromPrefsArray =Arrays.asList(bloodGroupSelectionsPref
					.subSequence(2, bloodGroupSelectionsPref.length())
					.toString()
					.split("\\$\\#"));
		if(subscriptionsFromPrefsArray.contains("APositive"))
		 {
			 chkAPositive.setChecked(true);
		 }
		if(subscriptionsFromPrefsArray.contains("ANegative"))
		 {
			 chkANegative.setChecked(true);
		 }
		if(subscriptionsFromPrefsArray.contains("BPositive"))
		 {
			 chkBPositive.setChecked(true);
		 }
		if(subscriptionsFromPrefsArray.contains("BNegative"))
		 {
			 chkBNegative.setChecked(true);
		 }
		if(subscriptionsFromPrefsArray.contains("OPositive"))
		 {
			 chkOPositive.setChecked(true);
		 }
		if(subscriptionsFromPrefsArray.contains("ONegative"))
		 {
			 chkONegative.setChecked(true);
		 }
		if(subscriptionsFromPrefsArray.contains("ABPositive"))
		 {
			 chkABPositive.setChecked(true);
		 }
		if(subscriptionsFromPrefsArray.contains("ABNegative"))
		 {
			 chkABNegative.setChecked(true);
		 }



	         }
		  
		  
		  btnSubscribe.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	               
	                callBloodGroupSubscribeService();
	            }
	        });
		 
		if (savedInstanceState == null) {
		//	getSupportFragmentManager().beginTransaction()
			//		.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	public void callBloodGroupSubscribeService(){
		ArrayList<String> subscriptions=new ArrayList<String>();
		ArrayList<String> unsubscriptions=new ArrayList<String>();
		String bloodGroupSelectionsCurrent="";
		  
		
		
		
         if(chkAPositive.isChecked())
         {
        	 bloodGroupSelectionsCurrent+="$#APositive";
        	 subscriptions.add("APositive");
        	 
         }
         if(chkANegative.isChecked())
         {
        	 bloodGroupSelectionsCurrent+="$#ANegative";
        	 subscriptions.add("ANegative");
        	 
         }
         if(chkBPositive.isChecked())
         {
        	 bloodGroupSelectionsCurrent+="$#BPositive";
        	 subscriptions.add("BPositive");
        	 
         }
         if(chkBNegative.isChecked())
         {
        	 bloodGroupSelectionsCurrent+="$#BNegative";
        	 subscriptions.add("BNegative");
        	 
         }
         if(chkOPositive.isChecked())
         {
        	 bloodGroupSelectionsCurrent+="$#OPositive";
        	 subscriptions.add("OPositive");
        	 
         }
         if(chkONegative.isChecked())
         {
        	 bloodGroupSelectionsCurrent+="$#ONegative";
        	 subscriptions.add("ONegative");
        	 
         }
         if(chkABPositive.isChecked())
         {
        	 bloodGroupSelectionsCurrent+="$#ABPositive";
        	 subscriptions.add("ABPositive");
        	 
         }
         if(chkABNegative.isChecked())
         {
        	 bloodGroupSelectionsCurrent+="$#ABNegative";
        	 subscriptions.add("ABNegative");
        	 
         }
        
         
         if(bloodGroupSelectionsPref!="")
         {
       
         
         
         for(String oldSub:subscriptionsFromPrefsArray )
         {
        	 if(!subscriptions.contains(oldSub))
        	 {
        		unsubscriptions.add(oldSub); 
        	 }
         }
         }
         
         Log.d(TAG,"bloodGroupSelectionsCurrent "+bloodGroupSelectionsCurrent);
         mqttService.subscribetoBloodGroups(subscriptions,unsubscriptions);
         Editor editor = sharedPreferences.edit();	    	    
	        editor.putString("bloodGroupSelections",bloodGroupSelectionsCurrent );   	    	
	        editor.commit();
         
         
		
	}
	
	 private ServiceConnection mConnection = new ServiceConnection() {
	     	
    	 @SuppressLint("NewApi") @Override
        public void onServiceConnected(ComponentName className, 
            IBinder binder) {
          MQTTService.MyBinder b = (MQTTService.MyBinder) binder;
          mqttService = b.getService();
          Toast.makeText(getBaseContext(), "Connected", Toast.LENGTH_SHORT)
              .show();
        }

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			
		}
	 };
    	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.blood_group_selection, menu);
		return true;
	}
	  @Override
	    protected void onResume() {
	      super.onResume();
	      Intent intent= new Intent(this, MQTTService.class);
	      bindService(intent, mConnection,
	          Context.BIND_AUTO_CREATE);
	    }

	    @Override
	    protected void onPause() {
	      super.onPause();
	      unbindService(mConnection);
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
//			View rootView = inflater.inflate(
//					R.layout.fragment_blood_group_selection, container, false);
//			return rootView;
//		}
//	}

}

