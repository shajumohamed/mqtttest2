package com.example.mqtttest2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {
	
	public static final String SERVICE_CLASSNAME = "com.example.mqtttest2.MQTTService";
    private Button button;
    private Button button2;
    private Button button3;
    private Button btnManageSub;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private MQTTService s;
    static final String TAG="MainActivity";

     @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        button = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        btnManageSub=(Button)findViewById(R.id.btnManageSub);
        		
        editText1=(EditText)findViewById(R.id.txtMessage);
        editText2=(EditText)findViewById(R.id.editText2);
        editText3=(EditText)findViewById(R.id.editText3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               
                callSubscribeService();
            }
        });
        
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               
                callPublishService();
            }
        });
        btnManageSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               
            	Intent intent = new Intent(view.getContext(), BloodGroupSelection.class);
            	startActivity(intent);
            }
        });
        
        
        updateButton();
     
    }public void callSubscribeService(){
    	
    	s.subscribetoTopic(editText1.getText().toString());
    }
public void callPublishService(){
    	
    	s.publishtoTopic(editText2.getText().toString(),editText3.getText().toString());
    }
     
     
     private ServiceConnection mConnection = new ServiceConnection() {
     	
    	 @SuppressLint("NewApi") @Override
        public void onServiceConnected(ComponentName className, 
            IBinder binder) {
          MQTTService.MyBinder b = (MQTTService.MyBinder) binder;
          s = b.getService();
          Toast.makeText(getBaseContext(), "Connected", Toast.LENGTH_SHORT)
              .show();
        }
        
		
		

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
    };
    
  
    private void updateButton() {
        if (serviceIsRunning()) {
            button.setText("Stop Service");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    button.setText("Start Service");
                    stopBlackIceService();
                    updateButton();
                }
            });

        } else {
            button.setText("Start Service");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    button.setText("Stop Service");
                    startBlackIceService();
                    updateButton();
                   // s.subscribetoTopic("shaju");
                }
            });
        }
    }

    @SuppressLint("NewApi") private void startBlackIceService() {

        final Intent intent = new Intent(this, MQTTService.class);
        this.startService(intent);
    }

    @SuppressLint("NewApi") private void stopBlackIceService() {

        final Intent intent = new Intent(this, MQTTService.class);
        this.stopService(intent);
    }

    @SuppressLint("NewApi") private boolean serviceIsRunning() {
        ActivityManager manager = (ActivityManager)this.getSystemService(this.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SERVICE_CLASSNAME.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
   @SuppressLint("NewApi") public static class PlaceholderFragment extends Fragment {
    	public static final String SERVICE_CLASSNAME = "com.example.mqtttest2.MQTTService";
        private Button button;
        private Button button2;
        private MQTTService s;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
         
            button = (Button) rootView.findViewById(R.id.button1);
        
           
           
            updateButton();
         
            
            return rootView;
        }
        
        
        

        private ServiceConnection mConnection = new ServiceConnection() {
        	
        	 @SuppressLint("NewApi") @Override
            public void onServiceConnected(ComponentName className, 
                IBinder binder) {
              MQTTService.MyBinder b = (MQTTService.MyBinder) binder;
              s = b.getService();
              Toast.makeText(getActivity().getBaseContext(), "Connected", Toast.LENGTH_SHORT)
                  .show();
            }
            
			
			

			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				
			}
        };
        
      
        private void updateButton() {
            if (serviceIsRunning()) {
                button.setText("Stop Service");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button.setText("Start Service");
                        stopBlackIceService();
                        updateButton();
                    }
                });

            } else {
                button.setText("Start Service");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button.setText("Stop Service");
                        startBlackIceService();
                        updateButton();
                       // s.subscribetoTopic("shaju");
                    }
                });
            }
        }

        @SuppressLint("NewApi") private void startBlackIceService() {

            final Intent intent = new Intent(this.getActivity(), MQTTService.class);
            this.getActivity().startService(intent);
        }

        @SuppressLint("NewApi") private void stopBlackIceService() {

            final Intent intent = new Intent(this.getActivity(), MQTTService.class);
            this.getActivity().stopService(intent);
        }

        @SuppressLint("NewApi") private boolean serviceIsRunning() {
            ActivityManager manager = (ActivityManager)this.getActivity().getSystemService(this.getActivity().ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (SERVICE_CLASSNAME.equals(service.service.getClassName())) {
                    return true;
                }
            }
            return false;
        }
    }

}
