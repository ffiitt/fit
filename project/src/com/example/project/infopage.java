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

import com.example.project.myhomepage.listen_back;
import com.example.project.test.LoginThread;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;


public class infopage extends Activity implements ViewFactory,OnTouchListener{
	private ImageSwitcher mImageSwitcher;
    private int[] images;
    private int flag = 1;
    private String user = "";
    private String name = "";
    private int currentPosition=0;
    private float downX;
    Handler handler1;
    private String infodevice = "";
    private boolean free = true;
    private String stadium = "";
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.infopage);
		String kind = getIntent().getStringExtra("kind");
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		name = data.getString("name");
		user = data.getString("user");
		stadium = data.getString("stadium");
		images = new int[]{R.drawable.sta1,R.drawable.stta2,R.drawable.sta3};
		mImageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcherinfo);
		mImageSwitcher.setFactory(this);
		mImageSwitcher.setOnTouchListener(this);
		//currentPosition = getIntent().getIntExtra("positon",0);
		mImageSwitcher.setImageResource(images[currentPosition]);
		final Button back = (Button)findViewById(R.id.back);
		back.setOnClickListener(new listen_back());
		final Button yuding = (Button)findViewById(R.id.yuding);
		yuding.setOnClickListener(new listen_yuding());
		if(free){
			Thread loginThread = new Thread(new LoginThread());
			loginThread.start();
		}
		handler1 = new Handler(){
			@Override
			public void handleMessage(Message msg){
					Bundle bundle = msg.getData();
                    infodevice = bundle.getString("infodevice");
			}
		};
	}
	class listen_yuding implements OnClickListener{
		@Override
		public void onClick(View v){
			if(free){
				showdialog();
			}
		}
	}
	public void sendjson(){
		String url = "http://10.0.2.2:8080/web/infodeviceServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		String muser = stadium;
		try{
			JSONObject json1 = new JSONObject();
			Object username = muser;
			json1.put("username", username);
			//System.out.print(json1.toString());
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
		    infodevice = json.getString("infodevice");
		    System.out.println(infodevice);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void showdialog(){
		final AlertDialog.Builder dialog = new AlertDialog.Builder(infopage.this);
		dialog.setTitle("设施使用信息");
		dialog.setMessage(infodevice);
		dialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		dialog.show();
		
	}
	class LoginThread implements Runnable{
		public void run(){
			sendjson();
			Message message=Message.obtain();
			Bundle bundle = new Bundle();
            bundle.putString("infodevice",infodevice);
			message.setData(bundle);
			handler1.sendMessage(message);
		}
	}
	class listen_back implements OnClickListener{
		@Override
    	public void onClick(View v){
    		Bundle data = new Bundle();
    		Intent intent = new Intent(infopage.this,MainActivity.class);//test其实是login
    		data.putString("user", user);
    		data.putString("name", name);
			intent.putExtras(data);
			startActivity(intent);
    	}
	}
	@Override
    public View makeView(){
    	final ImageView i = new ImageView(this);
    	i.setBackgroundColor(0xff000000);
    	i.setScaleType(ImageView.ScaleType.CENTER_CROP);
    	i.setLayoutParams(new ImageSwitcher.LayoutParams(android.widget.TableLayout.LayoutParams.FILL_PARENT,android.widget.TableLayout.LayoutParams.FILL_PARENT));
    	return i;
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    @Override
    public boolean onTouch(View v,MotionEvent event){
    	switch(event.getAction()){
    	  case MotionEvent.ACTION_DOWN:{
    		  downX = event.getX();
    		  break;
    	  }
    	  case MotionEvent.ACTION_UP:{
    		  float lastX = event.getX();
    		  if(lastX > downX){
    			  if(currentPosition > 0){
    				  mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
    				  mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
    				  currentPosition--;
    				  mImageSwitcher.setImageResource(images[currentPosition]);
    			  }
    			  else{
    				  currentPosition = images.length-1;
    				  mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
    				  mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
    				  mImageSwitcher.setImageResource(images[currentPosition]);
    				  //Toast.makeText(getApplication(), "the first picture", Toast.LENGTH_SHORT).show();
    			  }
    		  }
    		  if(lastX < downX){
    			  if(currentPosition < images.length-1){
    				  mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_in));
    				  mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_out));
    				  currentPosition++;
    				  mImageSwitcher.setImageResource(images[currentPosition]);
    			  }
    			  else{
    				  //Toast.makeText(getApplication(), "the last picture",Toast.LENGTH_SHORT).show();
    				  currentPosition = 0;
    				  mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_in));
    				  mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_out));
    				  mImageSwitcher.setImageResource(images[currentPosition]);
    			  }
    		  }
    	  }
    	  break;
    	}
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
}

