package com.example.mqtttest2;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

public class MQTTService extends Service {
	 private final IBinder mBinder = new MyBinder();
	 static final String TAG="MQTTService";
	 
	 //public static final String BROKER_URL = "tcp://broker.mqttdashboard.com:1883";
	  public static final String BROKER_URL = "tcp://test.mosquitto.org:1883";
	 //public static final String BROKER_URL = "tcp://shajumohamed.com:1883";
	 //public static final String BROKER_URL = "tcp://test.mosca.io:1883";

	    /* In a real application, you should get an Unique Client ID of the device and use this, see
	    http://android-developers.blogspot.de/2011/03/identifying-app-installations.html */
	    //public static final String clientId = "android-client123456789";

	    //public static final String TOPIC = "de/eclipsemagazin/blackice/warnings";
	    public static final String TOPIC = "Allianz/#";
	    private MqttClient mqttClient;
	    private MqttConnectOptions mqttOptions;
	    String mqttClientID;


	    @Override
	    public IBinder onBind(Intent arg0) {
	      return mBinder;
	    }
	
	
	 public int onStartCommand(Intent intent,int flags, int startId) {
		 Toast.makeText(getApplicationContext(), "Service Called", Toast.LENGTH_LONG).show();
	       try {
	    	   
	    	   if(mqttClient==null||!mqttClient.isConnected())
	    	   {
	    		   //get client id form prefs
	    	   SharedPreferences sharedPreferences = PreferenceManager    			
	    			                   .getDefaultSharedPreferences(this);
	    	    mqttClientID=sharedPreferences.getString("mqttClientID","");
	    	    
	    	    //if client id is empty generate and save to prefs
	    	    if(mqttClientID=="")
	    	    {
	    	    	 Log.d(TAG,"Client id not in prefs");
	    	    	mqttClientID=MqttClient.generateClientId();
	    	    	Editor editor = sharedPreferences.edit();	    	    
	    	    	        editor.putString("mqttClientID", mqttClientID);   	    	
	    	    	        editor.commit();
	    	    	      

	    	    }
	    	    Log.d(TAG," Client id is"+ mqttClientID);
	    	    
	    	    
	    	  
	            mqttClient = new MqttClient(BROKER_URL, MqttClient.generateClientId(), new MemoryPersistence());
	            mqttOptions=new MqttConnectOptions();
	            mqttOptions.setCleanSession(false);
	            
	            mqttClient.setCallback(new PushCallback(this));	            
	            mqttClient.connect(mqttOptions);	          
	         // mqttClient.subscribe(TOPIC,1);
	          
	    	   }


	        } catch (Exception e) {
	            Toast.makeText(getApplicationContext(), "Something went wrong!" + e.getMessage(), Toast.LENGTH_LONG).show();
	            e.printStackTrace();
	        }

	       return Service.START_NOT_STICKY;
	 }
	 
	 public void subscribetoTopic(String newTopic)
	 {
		 try {
			 if(!mqttClient.isConnected())
			 {
				  mqttClient.connect(mqttOptions);	 
			 }
			mqttClient.subscribe(newTopic);
			 Toast.makeText(getApplicationContext(), "Subscribed to " + newTopic, Toast.LENGTH_LONG).show();
		} catch (MqttSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void subscribetoBloodGroups(ArrayList<String> subscriptions,ArrayList<String> unSubscriptions)
	 {
		 try {
			 if(!mqttClient.isConnected())
			 {
				  mqttClient.connect(mqttOptions);	 
			 }
			 for(String sub:subscriptions)
			 {
				 mqttClient.subscribe("Allianz/bloodGroups/"+sub,2); 
				  Log.d(TAG," subscribed to "+"Allianz/bloodGroups/"+sub);
			 }
			 for(String unSub:unSubscriptions)
			 {
				 mqttClient.unsubscribe("Allianz/bloodGroups/"+unSub);
				 Log.d(TAG," unsub from "+"Allianz/bloodGroups/"+unSub);
			 }
			 
			//mqttClient.subscribe(newTopic);
			 Toast.makeText(getApplicationContext(), "Subscribed to done", Toast.LENGTH_LONG).show();
		} catch (MqttSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void publishtoTopic(String topic,String Message)
	 {
		 
		 try {
			 if(!mqttClient.isConnected())
			 {
				  mqttClient.connect(mqttOptions);	 
			 }
			 final MqttTopic temperatureTopic = mqttClient.getTopic(topic);

		        

		        final MqttMessage message = new MqttMessage(String.valueOf(Message).getBytes());
		        temperatureTopic.publish(message);
			
			 Toast.makeText(getApplicationContext(), "newTopic", Toast.LENGTH_LONG).show();
		} catch (MqttSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

	    @Override
	    public void onDestroy() {
	       try {
	            mqttClient.disconnect(0);
	            Log.d(TAG,"mqtt disconnected");
	        } catch (MqttException e) {
	            Toast.makeText(getApplicationContext(), "Something went wrong!" + e.getMessage(), Toast.LENGTH_LONG).show();
	            e.printStackTrace();
	        }
	    }
	    public class MyBinder extends Binder {
	        MQTTService getService() {
	          return MQTTService.this;
	        }
	      }

}
