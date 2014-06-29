package com.example.mqtttest2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class PushCallback implements MqttCallback {
	static final String TAG="PushCallback";
	 private ContextWrapper context;
	 private int notificationid=0;

	    public PushCallback(ContextWrapper context) {

	        this.context = context;
	    }

	    @Override
	    public void connectionLost(Throwable cause) {
	        //We should reconnect here
	    }

	    @SuppressWarnings("deprecation")
		@Override
	    public void messageArrived(MqttTopic topic, MqttMessage message) throws Exception {

	        final NotificationManager notificationManager = (NotificationManager)
	                context.getSystemService(Context.NOTIFICATION_SERVICE);

	        final Notification notification = new Notification(R.drawable.ic_launcher,
	                "Alert!!", System.currentTimeMillis());

	        // Hide the notification after its selected
	        notification.flags |= Notification.FLAG_AUTO_CANCEL;

	        final Intent intent = new Intent(context, MessageDetails.class);
	        Bundle extras = new Bundle();
	        Log.d(TAG,"tpoic is "+topic.toString());
	       
	        extras.putString("TOPIC",topic.toString() );
	        extras.putString("MESSAGE",message.toString());
	        intent.putExtras(extras);
	        intent.setAction("Test");
	        
	        final PendingIntent activity = PendingIntent.getActivity(context, notificationid, intent, 0);

	        notification.setLatestEventInfo(context, "Alert","Blood Required", activity);
	        notification.number += 1;
	        notificationManager.notify(notificationid, notification);
	        notificationid++;
	    }

	    @Override
	    public void deliveryComplete(MqttDeliveryToken token) {
	        //We do not need this because we do not publish
	    }
	}