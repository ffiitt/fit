package com.example.project;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.example.project.myhomepage.deletethread;
import com.example.project.test.LoginThread;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class infodevice extends Activity{
	String user="";
    String name="";
    private String date = "";
    private ArrayList<String> n_devicename = new ArrayList<String>();
    private ArrayList<String> n_devicesum = new ArrayList<String>();
    private ArrayList<String> nn_devicename = new ArrayList<String>();
    private ArrayList<String> nn_devicesum = new ArrayList<String>();
    boolean is_end = false;
	Handler handler1;
    @Override 
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.infodevice);
		date = today();
		init();
		draw_device(is_end);
		final View v = (View)findViewById(R.id.right);
        v.setBackgroundColor(Color.WHITE);
        final View vv = (View)findViewById(R.id.left);
        vv.setBackgroundColor(Color.RED);
        final Button signed = (Button)findViewById(R.id.signed);
        signed.setText("后天");
        signed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final View left = (View)findViewById(R.id.left);
				left.setBackgroundColor(Color.WHITE);
				final View right = (View)findViewById(R.id.right);
				right.setBackgroundColor(Color.RED);
				if(!is_end){
					is_end = true;
					draw_device(is_end);
				}
			}
		});
        final Button signing = (Button)findViewById(R.id.signing);
        signing.setText("明天");
		signing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final View left = (View)findViewById(R.id.left);
				left.setBackgroundColor(Color.RED);
				final View right = (View)findViewById(R.id.right);
				right.setBackgroundColor(Color.WHITE);
				if(is_end){
					is_end = false;
					draw_device(is_end);
				}
			}
		});
    }
    private void init(){
    	for(int i = 0 ; i < 20 ; i++ ){
    		nn_devicename.add(i+"00");
    		nn_devicesum.add(i+"00");
    		n_devicename.add(i+"11");
    		n_devicesum.add(i+"11");
    	}
    }
    private void draw_device(boolean sign){
    	int listlength = 0;
    	RelativeLayout r = (RelativeLayout)findViewById(R.id.rr);
    	r.removeAllViews();
    	if(!is_end){
    		listlength = n_devicename.size();
    	}else{
    		listlength = nn_devicename.size();
    	}
    	for(int i = 0 ; i < listlength ; i++){
			//新加的
    		View vv = new View(this);
    		android.widget.RelativeLayout.LayoutParams param0 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    		param0.height = 130;
		    param0.width = 1300;
			param0.leftMargin = 1;
			param0.topMargin = 1 + 140*i;
		
    		vv.setBackgroundResource(R.drawable.shape);
    		r.addView(vv,param0);
			//
    		View v = new View(this);
    		android.widget.RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		//param.setMargins(25, 330+i*130, 980, 1280-i*130);
    		param.height = 80;
    		param.width = 60;
    		param.leftMargin = 20;
    		param.topMargin = 20 + 140*i;
    		
    		v.setBackgroundResource(R.drawable.hot);
    		r.addView(v,param);
    		TextView tt = new TextView(this);
    		android.widget.RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		//param2.setMargins(150, 390+i*130, 10, 1200);
    		param2.height = 80;
    		param2.width = 100;
    		param2.leftMargin = 200;
    		param2.topMargin = 30 + 140*i;
    		tt.setText(nn_devicename.get(i));
    		tt.setTextSize(20);
    		r.addView(tt,param2);
    		TextView t = new TextView(this);
    		android.widget.RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    		param1.height = 50;
    		param1.width = 100;
    		param1.leftMargin = 700;
    		param1.topMargin = 40 + 140*i;
    		
    		t.setText(nn_devicesum.get(i));
    		t.setTextSize(15);
    		r.addView(t,param1);
    		View vi = new View(this);
    		android.widget.RelativeLayout.LayoutParams param3 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    		param3.setMargins(0,123+i*130,0,1073-i*130);
    		vi.setBackground(new ColorDrawable(Color.RED));
    		r.addView(vi,param3);	
		//新加的
    		final ImageButton bu = new ImageButton(this);
    		android.widget.RelativeLayout.LayoutParams param4 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    		param4.height = 80;
    		param4.width = 60;
    		param4.leftMargin = 950;
    		param4.topMargin = 20 + 140*i;
    		bu.setBackgroundResource(R.drawable.next);
    		r.addView(bu,param4);
    		bu.setOnClickListener(new OnClickListener() {
			
    			@Override
    			public void onClick(View v) {
				// TODO Auto-generated method stub
    				int j = (bu.getBottom() - 100)/ 130;
    				System.out.println(bu.getBottom());
				//System.out.println(agena.get(j));

//    				Thread loginThread = new Thread(new deletethread());
//    				loginThread.start(); 
				
    			}
    		});
		
    	}

    }
    private String today(){
    	String year,month,day;
        Calendar now = Calendar.getInstance();
   	 	year ="" + now.get(Calendar.YEAR);
   	 	if(now.get(Calendar.MONTH)+1 < 10){
   	 		month = "0" + (now.get(Calendar.MONTH)+1);
   	 	}
   	 	else{
   	 		month = "" + (now.get(Calendar.MONTH)+1);
   	 	}
   	 	if(now.get(Calendar.DAY_OF_MONTH) < 10){
   	 		day = "0" + (now.get(Calendar.DAY_OF_MONTH));
   	 	}
   	 	else{
   	 		day = "" + (now.get(Calendar.DAY_OF_MONTH));
   	 	}
   	 	return year+"-"+month+"-"+day;
    }



}
