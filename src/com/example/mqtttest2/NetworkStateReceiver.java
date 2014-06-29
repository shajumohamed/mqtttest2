package com.example.mqtttest2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkStateReceiver extends BroadcastReceiver {
static final String TAG="NetworkStateReceiver";
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		 final Intent intent = new Intent(arg0, MQTTService.class);
		if(isOnline(arg0))
		{
			
			// arg0.startService(intent);
			Log.d(TAG, "connected");
		}
		else
		{
			
			
			//arg0.stopService(intent);
			Log.d(TAG, "disconnected");
		}
		
		
	}
	
	
	 public boolean isOnline(Context context) {
         ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo netInfo = cm.getActiveNetworkInfo();
//should check null because in air plan mode it will be null
         if (netInfo != null && netInfo.isConnected()) {
             return true;
         }
         return false;
     }

}
