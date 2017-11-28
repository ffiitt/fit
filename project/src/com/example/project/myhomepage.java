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

import com.example.project.outdoor.thread;

import android.os.Handler;
import android.support.v4.graphics.drawable.*;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Message;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import android.R.drawable;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class myhomepage extends Activity{
	private Calendar c = null;
	private int row = 1;
	private String name="";
	Handler handler1;
	private String user = "";
	private String temp_time="";
	private String temp_agena="";
	private ArrayList<String> agena = new ArrayList<String>();
	private ArrayList<String> time = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myhomepage);
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		name = data.getString("name");
		TextView ttext = (TextView)findViewById(R.id.ttext);
		ttext.setText("Hello,"+name);
		
		//给头像描黑边 
		//RelativeLayout r = (RelativeLayout)findViewById(R.id.real);
		OvalShape ovalShape = new OvalShape();
		ShapeDrawable drawable0 = new ShapeDrawable(ovalShape);
		drawable0.getPaint().setColor(Color.BLACK);
		drawable0.getPaint().setStyle(Paint.Style.FILL);
		drawable0.getPaint().setAntiAlias(true);
		drawable0.getPaint().setStrokeWidth(5);
		RoundedBitmapDrawable drawable3 = RoundedBitmapDrawableFactory.create(getResources(),BitmapFactory.decodeResource(getResources(),R.drawable.yinzhiboa));
		drawable3.setCornerRadius(300);
		drawable3.setAntiAlias(true);
		Drawable[] layers = new Drawable[2];
		layers[0] = drawable0;
		layers[1] = drawable3;
		LayerDrawable layerDrawable = new LayerDrawable(layers);
		layerDrawable.setLayerInset(0, 5, 5, 5, 5);
		layerDrawable.setLayerInset(1, 10, 10,10, 10);
		final View view = (View)findViewById(R.id.user_ph);
		view.setBackground(layerDrawable);
		final Button insag = (Button)findViewById(R.id.insert_agenga);
		insag.setOnClickListener(new listen_insertagenga());
		final Button back = (Button)findViewById(R.id.back);
		back.setOnClickListener(new listen_back());
		Thread loginThread = new Thread(new thread());
		loginThread.start(); 
//		agena.add("去吃排骨大煲，加1箱方便面");
//		agena.add("学习机器学习，做实验，背英语单词");
//		agena.add("买衣服");
//		agena.add("回家");
//		time.add("2017-1-1");
//		time.add("2014-4-4");
//		time.add("2016-7-1");
//		time.add("2017-6-29");
//		draw_agenga();
		handler1 = new Handler(){
			@Override
			public void handleMessage(Message msg){
					Bundle bundle = msg.getData();
					time = bundle.getStringArrayList("time");
					agena = bundle.getStringArrayList("agena");
                    draw_agenga();
			}
		};
//		for(int i = 0 ; i < agena.size() ; i++){
//			//新加的
//			View vv = new View(this);
//			android.widget.RelativeLayout.LayoutParams param0 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//			param0.setMargins(0, 310+i*130, 0, 1268-i*130);
//			vv.setBackgroundResource(R.drawable.shape);
//			r.addView(vv,param0);
//			//
//			View v = new View(this);
//			android.widget.RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//			param.setMargins(25, 330+i*130, 980, 1280-i*130);
//			v.setBackgroundResource(R.drawable.hot);
//			r.addView(v,param);
//			TextView tt = new TextView(this);
//			android.widget.RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//			param2.setMargins(150, 390+i*130, 10, 1200);
//			tt.setText(time.get(i));
//			tt.setTextSize(10);
//			r.addView(tt,param2);
//			TextView t = new TextView(this);
//			android.widget.RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//			param1.setMargins(150, 320+i*130, 10, 1200);
//			t.setText(agena.get(i));
//			t.setTextSize(15);
//			r.addView(t,param1);
//			View vi = new View(this);
//			android.widget.RelativeLayout.LayoutParams param3 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//			param3.setMargins(0,430+i*130,0,1268-i*130);
//			vi.setBackground(new ColorDrawable(Color.RED));
//			r.addView(vi,param3);
//		}
	}
	class listen_back implements OnClickListener{
		@Override
    	public void onClick(View v){
    		Bundle data = new Bundle();
    		Intent intent = new Intent(myhomepage.this,MainActivity.class);//test其实是login
			data.putString("user",user);
			data.putString("name", name);
			intent.putExtras(data);
			startActivity(intent);
    	}
	}
	public void draw_agenga(){
		RelativeLayout r = (RelativeLayout)findViewById(R.id.rr);
		r.removeAllViews();
		for(int i = 0 ; i < agena.size() ; i++){
				//新加的
			View vv = new View(this);
			android.widget.RelativeLayout.LayoutParams param0 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			param0.setMargins(0, 0+i*130, 0, 1080-i*130);
			//param0.setMargins(0, 10, 100, 100);
			vv.setBackgroundResource(R.drawable.shape);
			r.addView(vv,param0);
				//
			View v = new View(this);
			android.widget.RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			//param.setMargins(25, 330+i*130, 980, 1280-i*130);
			param.setMargins(25, 15+i*130, 970, 1080-i*130);
			v.setBackgroundResource(R.drawable.hot);
			r.addView(v,param);
			TextView tt = new TextView(this);
			android.widget.RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			//param2.setMargins(150, 390+i*130, 10, 1200);
			param2.setMargins(150, 75+i*130, 10, 1200);
			tt.setText(time.get(i));
			tt.setTextSize(10);
			r.addView(tt,param2);
			TextView t = new TextView(this);
			android.widget.RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			param1.setMargins(150, 5+i*130, 10, 1200);
			t.setText(agena.get(i));
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
			param4.setMargins(950,30+i*130,70,1100-i*130);
			bu.setBackgroundResource(R.drawable.delete);
			r.addView(bu,param4);
			bu.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int j = (bu.getBottom() - 100)/ 130;
					System.out.println(bu.getBottom());
					//System.out.println(agena.get(j));
					temp_agena = agena.get(j);
					temp_time = time.get(j);
					agena.remove(j);
					time.remove(j);
					draw_agenga();
					Thread loginThread = new Thread(new deletethread());
					loginThread.start(); 
					
				}
			});
			
		}
	}
//		for(int i = 0 ; i < agena.size(); i++){
//        	TextView text = new TextView(this);
//        	text.setText(agena.get(i));
//        	android.widget.RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//        	param.setMargins(i*100+100, i*100+100, i*100+100, i*100+100);
//        	r.addView(text,param);
//        }
//		android.widget.RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		TextView text1 = new TextView(this);
//    	text1.setText("adawdwa");
//    	param1.setMargins(300, 300, 300, 300);
//    	r.addView(text1,param1);
		
	
	class listen_insertagenga implements OnClickListener{
		@Override
		public void onClick(View v){
			System.out.println("wafafwa");
			showdialog();
		}
	}
	private void showdialog(){
//		final EditText edittext = new EditText(myhomepage.this);
//		AlertDialog.Builder inputdialog = new AlertDialog.Builder(myhomepage.this);
//		inputdialog.setTitle("新建计划");
//		inputdialog.setView(edittext);
//		inputdialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				System.out.println("131");
//			}
//		}).show();
		MyDialog mydia = new MyDialog().newInstance(0);
		mydia.show(getFragmentManager(),"警告");
		
	}
	class MyDialog extends DialogFragment{
		public MyDialog newInstance(int title){
			MyDialog my = new MyDialog();
			Bundle bundle = new Bundle();
			bundle.putInt("cmd", title);
			my.setArguments(bundle);
			return my;
		}
		public Dialog onCreateDialog(Bundle savedInstanceState){
			int id = getArguments().getInt("cmd");
			Dialog dialog = null;
			switch(id){
			case 0:
				c = Calendar.getInstance();
				dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						time.add(year+"-"+(monthOfYear+1)+"-"+(dayOfMonth));
						temp_time=year+"-"+(monthOfYear+1)+"-"+(dayOfMonth);
                        show_inputdialog();
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				break;
			}
			return dialog;
		}
	}
	public void show_inputdialog(){
		final EditText edit = new EditText(this);
		AlertDialog.Builder inputdialog=new AlertDialog.Builder(this);
		inputdialog.setTitle("啥计划呢,老ge?").setView(edit);
		inputdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				agena.add(edit.getText().toString());
				temp_agena=edit.getText().toString();
				draw_agenga();
				Thread loginThread1 = new Thread(new insertthread());
				loginThread1.start(); 
				
			}
		}).show();
	}
    
	public void sendjson(){
		int number = 0;
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		user = data.getString("user");
		String url = "http://10.0.2.2:8080/web/agenaServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		try{
			JSONObject json1 = new JSONObject();
			Object username = user;
			json1.put("username", username);
			StringEntity se = new StringEntity(json1.toString());
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(se);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String s;
			StringBuffer result = new StringBuffer("");
			while((s=reader.readLine()) != null){
				result.append(s);
			}
			reader.close();
			JSONObject json = new JSONObject(result.toString());
		    number = json.getInt("number");
			System.out.println("@@@@@@@@@@");
			System.out.println(number);
			for(int i = 0 ; i < number ; i++){
				agena.add(json.getString(""+i+""+"agena"));
			    time.add(json.getString(""+i+""+"time"));
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	class thread implements Runnable{
		public void run(){
			sendjson();
			Message message=Message.obtain();
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("time",time);
			bundle.putStringArrayList("agena", agena);
			message.setData(bundle);
			handler1.sendMessage(message);
		}
	}
	public void sendjson1(){
		int number = 0;
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		user = data.getString("user");
		String url = "http://10.0.2.2:8080/web/insertServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		try{
			JSONObject json1 = new JSONObject();
			Object username = user;
			Object ag = temp_agena;
			Object ti = temp_time;
			System.out.println(temp_agena+"3333");
			json1.put("username", username);
			json1.put("time", ti);
			json1.put("agena", ag);
			//System.out.println(json1.toString()+"123");
			StringEntity se = new StringEntity(json1.toString());
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(se);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(post);
			
//			HttpEntity entity = response.getEntity();
//			InputStream inputStream = entity.getContent();
//			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//			BufferedReader reader = new BufferedReader(inputStreamReader);
//			String s;
//			StringBuffer result = new StringBuffer("");
//			while((s=reader.readLine()) != null){
//				result.append(s);
//			}
//			reader.close();
//			JSONObject json = new JSONObject(result.toString());
//		    number = json.getInt("number");
//			System.out.println("@@@@@@@@@@");
//			System.out.println(number);
//			for(int i = 0 ; i < number ; i++){
//				agena.add(json.getString(""+i+""+"agena"));
//			    time.add(json.getString(""+i+""+"time"));
//			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	class insertthread implements Runnable{
		public void run(){
			sendjson1();
		}
	}
	public void sendjson2(){
		int number = 0;
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		user = data.getString("user");
		String url = "http://10.0.2.2:8080/web/deleteServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		try{
			JSONObject json1 = new JSONObject();
			Object username = user;
			Object ag = temp_agena;
			Object ti = temp_time;
			System.out.println(temp_agena+"3333");
			json1.put("username", username);
			json1.put("time", ti);
			json1.put("agena", ag);
			System.out.println(json1.toString()+"123");
			StringEntity se = new StringEntity(json1.toString(),HTTP.UTF_8);
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(se);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(post);	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	class deletethread implements Runnable{
		public void run(){
			sendjson2();
		}
	}
}


