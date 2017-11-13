package com.example.project;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.Handler;
import android.support.v4.graphics.drawable.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Message;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.example.project.test.LoginThread;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
public class register extends Activity{
	private String tishi = "";
	private String reusername = "";
	private String restudent="";
	private String responseMsg = "";
 
	Handler handler1;
	

	protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.register);
    	final Button register_button = (Button)findViewById(R.id.zhuce);  	 
    	final RadioButton radiobutton2=(RadioButton)findViewById(R.id.radiobutton2);   	
		register_button.setOnClickListener(new login_listen());
		
		    
		radiobutton2.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean ischecked)
			{
				if(radiobutton2.isChecked())
				{
					restudent="y";
				}
				else
				{
					restudent="n";
				}
			}
		});
	
	
			
		
		
	}
		
		class login_listen implements OnClickListener{
			@Override
			public void onClick(View v){
				Thread loginThread = new Thread(new LoginThread());
				loginThread.start();
				Intent intent1 = new Intent();
				Intent intent2 = new Intent();
				final TextView text = (TextView)findViewById(R.id.wel);
				Bundle data = new Bundle();
				data.putString("user",reusername);
 
				if(tishi.equals("y"))
				{
					if(restudent.equals("y"))
					{
						intent2 = new Intent(register.this,myhomepage.class);
						intent2.putExtras(data);
						startActivity(intent2);
					}
					else
					{
						intent1 = new Intent(register.this,stadium.class);
						intent1.putExtras(data);
						startActivity(intent1);
					}
				}
				else if(tishi.equals("n"))
				{
					text.setText("用户名已存在");
				}		
			}
				
		
	public void sendjson(){
			String url = "http://10.0.2.2:8080/web/registerServlet";
			HttpPost post = new HttpPost(url);
			final EditText user = (EditText)findViewById(R.id.username);
			final EditText pass = (EditText)findViewById(R.id.password);
			final EditText name1 = (EditText)findViewById(R.id.nicheng);
			String muser = user.getText().toString();
			String mpass = pass.getText().toString();
			String mname = name1.getText().toString();
			reusername=muser;
			try{
				System.out.println(muser);
				System.out.println(mpass);
				System.out.println(mname);
				JSONObject json1 = new JSONObject();
				Object username = muser;
				json1.put("username", username);
				Object pwd = mpass;
				json1.put("password", pwd);
				//System.out.print(json1.toString());
				Object stu = restudent;
				json1.put("student", stu);
				Object name = mname;
				json1.put("name", name);
				System.out.println(1);
				StringEntity se = new StringEntity(json1.toString());
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				post.setEntity(se);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(post);
				HttpEntity entity = response.getEntity();
				System.out.println("ww");
				InputStream inputStream = entity.getContent();
				System.out.println("ww");
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				System.out.println("ww");
				BufferedReader reader = new BufferedReader(inputStreamReader);
				System.out.println("ww");
				String s;
				StringBuffer result = new StringBuffer("");
				while((s=reader.readLine()) != null){
					result.append(s);
				}
				reader.close();
				JSONObject json = new JSONObject(result.toString());
				tishi = json.getString("ok");
				System.out.println("qrqrqrqq");
				System.out.println(tishi);
				System.out.println("111111111111111");
			}catch(Exception e){
				e.printStackTrace();
			}
	
	}
		class LoginThread implements Runnable{
		    	
		    @Override
			public void run(){
				sendjson();
			}
		
		}}}

	


