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
	private String user="";
    private String name="";
    boolean is_signed = false;
    private ArrayList<String> matchID_ing = new ArrayList<String>();
    private ArrayList<String> matchID_ed = new ArrayList<String>();
    private ArrayList<String> matchname_ing = new ArrayList<String>();
    private ArrayList<String> matchname_ed = new ArrayList<String>();
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
        final Button listen_back = (Button)findViewById(R.id.back);
        listen_back.setOnClickListener(new listen_back());
        Intent intent = getIntent();
		Bundle data = intent.getExtras();
		name = data.getString("name");
		user = data.getString("user");
        init();
     	draw_match(is_signed);
		final Button signed = (Button)findViewById(R.id.signed);
		signed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final View left = (View)findViewById(R.id.left);
				left.setBackgroundColor(Color.WHITE);
				final View right = (View)findViewById(R.id.right);
				right.setBackgroundColor(Color.RED);
				if(!is_signed){
					is_signed = true;
					draw_match(is_signed);
				}
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
				if(is_signed){
					is_signed = false;
					draw_match(is_signed);
				}
			}
		});
    }
    public void init(){
        for(int i = 1 ; i < 15 ; i++){
        	matchID_ing.add(""+i+"");
        	matchID_ed.add(""+i+"");
        	matchname_ing.add("这里是赛事"+i+"");
        	matchname_ed.add("这里是结束的赛事"+i+"");
        }
        
    }
    public void draw_match(boolean sign){
    	int listlength = 0;
    	RelativeLayout r = (RelativeLayout)findViewById(R.id.rr);
    	r.removeAllViews();
    	if(!is_signed){
    		listlength = matchID_ing.size();
    	}else{
    		listlength = matchID_ed.size();
    	}
    	for(int i = 0 ; i < listlength; i++ ){
    		View vv = new View(this);
			android.widget.RelativeLayout.LayoutParams param0 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			param0.height = 300;
		    param0.width = 1200;
			param0.leftMargin = 1;
			param0.topMargin = 1 + 310*i;
			vv.setBackgroundResource(R.drawable.blue);
			r.addView(vv,param0);
			final TextView text = new TextView(this);
			android.widget.RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			if(!is_signed){
				text.setText(matchname_ing.get(i));
			}
			else{
				text.setText(matchname_ed.get(i));
			}
			text.setTextColor(Color.RED);
			text.setTextSize(13);
			param1.height = 60;
			param1.width = 500;
			param1.topMargin = 80 + 310*i;
			param1.leftMargin = 120;
			r.addView(text,param1);
		    final Button bu = new Button(this);
			android.widget.RelativeLayout.LayoutParams param4 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//			param4.setMargins(950,30+i*130,70,1100-i*130);
			bu.setText("进入赛事");
			bu.setTextColor(Color.RED);
			bu.setBackgroundResource(R.drawable.login_shape);
			bu.setTextSize(11);
			param4.height = 90;
			param4.width = 230;
			param4.leftMargin = 730;
			param4.topMargin = 210 + 310*i;		
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
	class listen_back implements OnClickListener{
		@Override
    	public void onClick(View v){
    		Bundle data = new Bundle();
    		Intent intent = new Intent(match.this,MainActivity.class);//test其实是login
			data.putString("user",user);
			data.putString("name", name);
			intent.putExtras(data);
			startActivity(intent);
			finish();
    	}
	}


}
