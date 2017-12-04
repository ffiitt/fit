package com.example.project;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class matchifo extends Activity{
	String user="";
    String name="";
	Handler handler1;
    @Override 
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.infodevice);
		final Button listen_back = (Button)findViewById(R.id.back);
	    listen_back.setOnClickListener(new listen_back());
    }
    class listen_back implements OnClickListener{
		@Override
    	public void onClick(View v){
    		Bundle data = new Bundle();
    		Intent intent = new Intent(matchifo.this,match.class);//test��ʵ��login
			data.putString("user",user);
			data.putString("name", name);
			intent.putExtras(data);
			startActivity(intent);
			finish();
    	}
	}
    


}