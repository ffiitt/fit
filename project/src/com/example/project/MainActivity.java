package com.example.project;

//import com.example.helloandroid.MainActivity;
//import com.example.helloandroid.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;
import org.apache.http.HttpResponse;

public class MainActivity extends Activity implements ViewFactory,OnTouchListener{
    private ImageSwitcher mImageSwitcher;
    private int[] images;
    private int currentPosition=0;
    private float downX;
    private boolean login = true;
    private String username = "";
    private String name = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		images = new int[]{R.drawable.bai1,R.drawable.bjt,R.drawable.bai3};
		mImageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher1);
		mImageSwitcher.setFactory(this);
		mImageSwitcher.setOnTouchListener(this);
		//currentPosition = getIntent().getIntExtra("positon",0);
		mImageSwitcher.setImageResource(images[currentPosition]);
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		System.out.print(data);
		if(data == null){
			
		}
		else{
			username = data.getString("user");
			if(username == null || username.equals("")){
				login = true;
			}
			else{
				login = false;
				name = data.getString("name");
			}
		}
		final ImageButton my = (ImageButton)findViewById(R.id.myhome);
		my.setOnClickListener(new listen_my());
		final Button fit = (Button)findViewById(R.id.outdoor);
		fit.setOnClickListener(new listen_fit());
		final Button swim = (Button)findViewById(R.id.swim);
		swim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,infopage.class);
				startActivity(intent);				
			}
		});
		final Button bask = (Button)findViewById(R.id.basketball);
		bask.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,infopage.class);
				startActivity(intent);				
			}
		});
		final Button badmin = (Button)findViewById(R.id.badminton);
		badmin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,infopage.class);
				startActivity(intent);				
			}
		});
		final Button tennis = (Button)findViewById(R.id.tennis);
		tennis.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,infopage.class);
				startActivity(intent);				
			}
		});
		final Button fitt = (Button)findViewById(R.id.fit);
		fitt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,infopage.class);
				startActivity(intent);				
			}
		});
		final Button back = (Button)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("yinzhibo");
			}
		});
	}
	class listen_fit implements OnClickListener{
		@Override
		public void onClick(View V){
			Intent intent = new Intent(MainActivity.this,outdoor.class);
			Bundle data = new Bundle();
			data.putString("user",username);
			intent.putExtras(data);
			startActivity(intent);
			finish();
			
		}
	}
	class listen_my implements OnClickListener{
		@Override
		public void onClick(View v){
			if(login == true){
				Intent intent = new Intent(MainActivity.this,test.class);//testÆäÊµÊÇlogin
				startActivity(intent);
				finish();
			}else{
				Intent intent = new Intent(MainActivity.this,myhomepage.class);
				Bundle data = new Bundle();
				data.putString("user",username);
				data.putString("name", name);
				intent.putExtras(data);
				startActivity(intent);
				finish();
			}
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
