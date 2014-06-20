package com.example.mqtttest2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

public class MQTTService extends Service {
	 private final IBinder mBinder = new MyBinder();
	 
	 //public static final String BROKER_URL = "tcp://broker.mqttdashboard.com:1883";
	  // public static final String BROKER_URL = "tcp://test.mosquitto.org:1883";
	 public static final String BROKER_URL = "tcp://shajumohamed.com:1883";

	    /* In a real application, you should get an Unique Client ID of the device and use this, see
	    http://android-developers.blogspot.de/2011/03/identifying-app-installations.html */
	    public static final String clientId = "android-client123456789";

	    //public static final String TOPIC = "de/eclipsemagazin/blackice/warnings";
	    public static final String TOPIC = "presence";
	    private MqttClient mqttClient;


	    @Override
	    public IBinder onBind(Intent arg0) {
	      return mBinder;
	    }
	
	
	 public int onStartCommand(Intent intent,int flags, int startId) {
		 Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
	       try {
	    	   int i=0;
	    	   //MqttDefaultFilePersistence persistence = new MqttDefaultFilePersistence("/mmt/sdcard/temp");
	            mqttClient = new MqttClient(BROKER_URL, MqttClient.generateClientId(), new MemoryPersistence());
               
	            mqttClient.setCallback(new PushCallback(this));
	            mqttClient.connect();

	            //Subscribe to all subtopics of homeautomation
	          mqttClient.subscribe(TOPIC);


	        } catch (Exception e) {
	            Toast.makeText(getApplicationContext(), "Something went wrong!" + e.getMessage(), Toast.LENGTH_LONG).show();
	            e.printStackTrace();
	        }

	       return Service.START_NOT_STICKY;
	 }
	 
	 public void subscribetoTopic(String newTopic)
	 {
		 try {
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
	 
	 public void publishtoTopic(String topic,String Message)
	 {
		 try {
			 
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
