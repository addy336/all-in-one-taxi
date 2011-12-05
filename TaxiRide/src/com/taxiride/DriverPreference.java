package com.taxiride;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


public class DriverPreference extends PreferenceActivity  implements OnSharedPreferenceChangeListener{
	 
	
	private String deviceID;
	private  String cabName; 
	private  String pin; 
	private  String phoneNum;
	private  String fullName;
	private  String maxPickUp;
	private String maxDropOff;
	private String paymentType;
	private String regID; 
	private SharedPreferences prefs;
	private SharedPreferences.Editor ed;
	private String id; 
	
	
	protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         addPreferencesFromResource(R.xml.driver_preferences);
         deviceID = Secure.getString(getBaseContext().getContentResolver(),
                 Secure.ANDROID_ID);
         SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
         sp.registerOnSharedPreferenceChangeListener(this);
          prefs = getSharedPreferences("DriverPreference", Context.MODE_PRIVATE);
         ed = prefs.edit();
         ed.putBoolean("HaveShownPrefs", true);
         ed.commit();
        
         
	 }   
	
	  @Override
		    public boolean onCreateOptionsMenu(Menu menu) {
	  	        menu.add(Menu.NONE, 0, 0, "Save");
	  	        return super.onCreateOptionsMenu(menu);
	  	    }
	  	 
	  	    @Override
	  	    public boolean onOptionsItemSelected(MenuItem item) {
	  	    	 
	  	    	boolean isGood = false; 
	  	    	SharedPreferences prefs = getSharedPreferences("DriverPreference", Context.MODE_PRIVATE);
	  	    		  id = prefs.getString("regID", "");
		  	   	String result = updateHttpRequest();
		  	
	  	             if(result.equals("fail")){
	  	            	Toast toast1 = Toast.makeText(getApplicationContext(),"Cannot send to the server, please try again", BIND_AUTO_CREATE);
	                    toast1.show();
	  	             }else if(!result.equals("fail")){
	  	            	 isGood=true;  
	  	            	 
	  	            	 ed.putString("regID",result);
	  	            	 ed.commit();
	  	            	 String temp =prefs.getString("regID", "");
	  	            	 
	  	            	 
	  	            	 
	  	            	Toast toast2 = Toast.makeText(getApplicationContext(),"Successfully send to server", BIND_AUTO_CREATE);
	                    toast2.show();
	  	            	 
	  	             }
	  	           if(isGood == true ) 	{ 
	  	        	   switch (item.getItemId()) {
	  	        	   case 0:
	  	                startActivity(new Intent(this, DriverWindow.class));
	  	                return true;
	  	        	   }
	  	      
	  	         }
	  	           Toast.makeText(getApplicationContext(), "Please enter all the information", 100);
	  	        return false;
	  	    }
	  	  @Override 
	        protected void onResume(){
	            super.onResume();
	            // Set up a listener whenever a key changes             
	            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	        }
	  	@Override 
        protected void onPause() { 
            super.onPause();
            // Unregister the listener whenever a key changes             
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);     
        } 

			@Override
			public void onSharedPreferenceChanged(SharedPreferences sp,
					String key) {
				
				 if (key.equals("editName")) {
					  fullName = sp.getString(key, "");
					  ed.putString("editName", fullName); 
					  ed.commit();
					
				 }	 
				 
				 	if(key.equals("editCompanyName")){
				 	cabName = sp.getString(key, "");
				 	ed.putString("editCompanyName", cabName);
				 	ed.commit();
				 	
				 	}
				 	if(key.equals("editPassword")){
				 		
				 	pin = sp.getString(key, "");
				 	ed.putString("editPassword", pin);
				 	ed.commit();
				 	
				 	}
				 	if(key.equals("editPhoneNum")){
				 	phoneNum = sp.getString(key, "");
				 	ed.putString("editPhoneNum", phoneNum);
				 	ed.commit();
				 	
				 	}
				 	if(key.equals("editMaxPickup")){
				 	maxPickUp = sp.getString(key, "");
				 	ed.putString("editMaxPickup", maxPickUp);
				 	ed.commit();
				 	
				 	}
				 	if(key.equals("editMaxDropOff")){
				 	maxDropOff = sp.getString(key, "");
				 	ed.putString("editMaxDropOff", maxDropOff);
				 	ed.commit();
				 	
				 	
				 	}
				 	if(key.equals("editPayment")){
				 	paymentType = sp.getString(key, "");
				 	ed.putString("editPayment", paymentType);
				 	ed.commit();
				 	
				 	}
				 	
		
			 
				    	
				// TODO Auto-generated m			
			}
			
       
 
       public String updateHttpRequest(){
    	   
    	   String myURL = "http://taxitestcenter.appspot.com/settings";
			 
			 StringBuilder taxiStationResponse = new StringBuilder();
			 StringBuffer jb = new StringBuffer();
			 Gson gson = new Gson();
				String requestResult = "";
				
				int counter = 0;
				try {
					// Construct data
					
					 String data = URLEncoder.encode("regID", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
					  data += "&" + URLEncoder.encode("loginName", "UTF-8") + "=" + URLEncoder.encode(deviceID, "UTF-8");
					  data += "&" + URLEncoder.encode("loginPin", "UTF-8") + "=" + URLEncoder.encode(pin, "UTF-8");
					  data += "&" + URLEncoder.encode("fullName", "UTF-8") + "=" + URLEncoder.encode(fullName, "UTF-8");
					  data += "&" + URLEncoder.encode("cabName", "UTF-8") + "=" + URLEncoder.encode(cabName, "UTF-8");
					  data += "&" + URLEncoder.encode("phoneNumber", "UTF-8") + "=" + URLEncoder.encode(phoneNum, "UTF-8");
					  data += "&" + URLEncoder.encode("maxPickupDistance", "UTF-8") + "=" + URLEncoder.encode(maxPickUp, "UTF-8");
					  data += "&" + URLEncoder.encode("maxDropOffDistance", "UTF-8") + "=" + URLEncoder.encode(maxDropOff, "UTF-8");
					  data += "&" + URLEncoder.encode("preferredPayment", "UTF-8") + "=" + URLEncoder.encode(paymentType, "UTF-8");
					  
				  URL url = new URL(myURL);
				  URLConnection conn =  url.openConnection();
				  conn.setDoOutput(true);

				// Send data

				 OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				 wr.write(data);
				 wr.flush();

				// Get the response
				   BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				   String line;


				  while ((line = rd.readLine()) != null) {
				// Process line...
				        taxiStationResponse.append(line);
				  }
			   wr.close();
			rd.close();
			 } catch (Exception e) {
			 }
			 requestResult = taxiStationResponse.toString();
		//	 requestResult = requestResult.substring(1);
		//	 requestResult = requestResult.substring(0, requestResult.length() - 1);
    	   return requestResult; 
    	   
    	   
       }
}
