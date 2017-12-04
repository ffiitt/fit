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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class editinf extends Activity {
	private String name="";
	private String user = "";
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.editinf);
		final Button back = (Button)findViewById(R.id.back);
		back.setOnClickListener(new listen_back());
		final Button select_p = (Button)findViewById(R.id.selectpng);
		select_p.setOnClickListener(new listen_select_p());
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
        user = data.getString("user");
        name = data.getString("name");
	}
	class listen_back implements OnClickListener{
		@Override
    	public void onClick(View v){
    		Bundle data = new Bundle();
    		Intent intent = new Intent(editinf.this,stadium.class);//test其实是login
			data.putString("user",user);
			data.putString("name", name);
			intent.putExtras(data);
			startActivity(intent);
			finish();
    	}
	}
	class listen_select_p implements OnClickListener{
		@Override
    	public void onClick(View v){
    		Bundle data = new Bundle();
    		Intent intent = new Intent(editinf.this,select_pic.class);//test其实是login
			startActivity(intent);
			finish();
    	}
	}
	
}
