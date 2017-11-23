package com.example.project;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.example.project.test.LoginThread;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
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

public class match extends Activity{
	String user="";
    String name="";
    private ArrayList<String> matchID = new ArrayList<String>();
	Handler handler1;
    @Override 
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.match);
        final View v = (View)findViewById(R.id.right);
        v.setBackgroundColor(Color.WHITE);
        final View vv = (View)findViewById(R.id.left);
        vv.setBackgroundColor(Color.RED);
//     	RelativeLayout r = (RelativeLayout)findViewById(R.id.rr);
//		View v = new View(this);
//		v.setBackgroundColor(Color.RED);
//		android.widget.RelativeLayout.LayoutParams param0 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		param0.height = 3;
//        param0.width = 300;
//		param0.leftMargin = 10;
//		param0.topMargin = 40;
//		r.addView(v,param0);
        init();
     	draw_match();
		final Button signed = (Button)findViewById(R.id.signed);
		signed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final View left = (View)findViewById(R.id.left);
				left.setBackgroundColor(Color.WHITE);
				final View right = (View)findViewById(R.id.right);
				right.setBackgroundColor(Color.RED);
			}
		});
		final Button signing = (Button)findViewById(R.id.signing);
		signing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final View left = (View)findViewById(R.id.left);
				left.setBackgroundColor(Color.RED);
				final View right = (View)findViewById(R.id.right);
				right.setBackgroundColor(Color.WHITE);
			}
		});
    }
    public void init(){
        for(int i = 1 ; i < 15 ; i++){
        	matchID.add(""+i+"");
        }
    }
    public void draw_match(){
//     	RelativeLayout r = (RelativeLayout)findViewById(R.id.rr);
//		View v = new View(this);
//		v.setBackgroundResource(R.drawable.bai1);
//		android.widget.RelativeLayout.LayoutParams param0 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		param0.height = 1000;
//        param0.width = 300;
//		param0.leftMargin = 10;
//		param0.topMargin = 40;
//		r.addView(v,param0);
//		android.widget.RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		View vv = new View(this);
//		vv.setBackgroundResource(R.drawable.bai1);
//		param1.topMargin=1200;
//		param1.height = 1000;
//        param1.width = 300;
//		param1.leftMargin = 10;
//		r.addView(vv,param1);
    	RelativeLayout r = (RelativeLayout)findViewById(R.id.rr);
    	for(int i = 0 ; i < matchID.size(); i++ ){
    		View vv = new View(this);
			android.widget.RelativeLayout.LayoutParams param0 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			param0.height = 300;
		    param0.width = 1200;
			param0.leftMargin = 1;
			param0.topMargin = 1 + 310*i;
			//param0.setMargins(0, 10, 100, 100);
			vv.setBackgroundResource(R.drawable.blue);
			r.addView(vv,param0);
		    final Button bu = new Button(this);
			android.widget.RelativeLayout.LayoutParams param4 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			param4.setMargins(950,30+i*130,70,1100-i*130);
			bu.setText("进入赛事");
			bu.setTextColor(Color.RED);
			bu.setBackgroundResource(R.drawable.login_shape);
			bu.setTextSize(11);
			param4.height = 90;
			param4.width = 230;
			param4.leftMargin = 380;
			param4.topMargin = 80 + 310*i;		
			r.addView(bu,param4);
			bu.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println(bu.getBottom());
				}
			});
    	}
    }


}
