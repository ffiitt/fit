package com.example.project;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import junit.framework.Assert;
import android.net.wifi.WifiManager.MulticastLock;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.view.Menu;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AbsListView.MultiChoiceModeListener;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.os.StrictMode;

import com.example.project.UploadUtil;
import com.example.project.UploadUtil.OnUploadProcessListener;
import com.example.project.register.thread;

public class editinf extends Activity implements OnClickListener,
		OnUploadProcessListener {
	private String name = "";
	private String user = "";
	private String staname = "";
	private String staoptime = "";
	private String staadddr = "";
	private String statele = "";
	private String stapri = "";
	private String staother = "";
	Handler handler1;
	private static final String TAG = "uploadImage";

	/**
	 * ȥ�ϴ��ļ�
	 */
	protected static final int TO_UPLOAD_FILE = 1;
	/**
	 * �ϴ��ļ���Ӧ
	 */
	protected static final int UPLOAD_FILE_DONE = 2; //
	/**
	 * ѡ���ļ�
	 */
	public static final int TO_SELECT_PHOTO = 3;
	/**
	 * �ϴ���ʼ��
	 */
	private static final int UPLOAD_INIT_PROCESS = 4;
	/**
	 * �ϴ���
	 */
	private static final int UPLOAD_IN_PROCESS = 5;
	/***
	 * ��������URL���ҷ�������javaEE����URL
	 */
	private static String requestURL = "http://192.168.191.1:8080/fileUpload/upload.action";
	private Button selectButton, uploadButton;
	private ImageView imageView;
	private TextView uploadImageResult;
	private String path;

	private String picPath = null;
	private ProgressDialog progressDialog;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.editinf);
		final Button back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new listen_back());
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		user = data.getString("user");
		name = data.getString("name");
		final Button button_edok = (Button) findViewById(R.id.edok);
		button_edok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				AlertDialog dialog = new AlertDialog.Builder(editinf.this)
//						.setTitle("�޸���Ϣ").setMessage("�޸ĳɹ�")
//						.setPositiveButton("ȷ��", null).show();
				success_dialog();
				final EditText stadname = (EditText) findViewById(R.id.staname);
				final EditText opentm = (EditText) findViewById(R.id.opentime);
				final EditText sprice = (EditText) findViewById(R.id.price);
				final EditText adress = (EditText) findViewById(R.id.adress);
				final EditText tele = (EditText) findViewById(R.id.tele);
				final EditText other = (EditText) findViewById(R.id.other);
				staname = stadname.getText().toString();
				staoptime = opentm.getText().toString();
				staadddr = adress.getText().toString();
				statele = tele.getText().toString();
				stapri = sprice.getText().toString();
				staother = other.getText().toString();
				Thread editinfthread = new Thread(new thread());
				editinfthread.start();
				//loginRemoteService();
			}
		});
		initView();
	}
	private void success_dialog(){
		final AlertDialog.Builder normalDialog = new AlertDialog.Builder(editinf.this);
		normalDialog.setTitle("");
		normalDialog.setMessage("�޸ĳɹ�");
		normalDialog.setPositiveButton("ȷ��", 
	            new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                	//...To-do
	            	Bundle data = new Bundle();
	    			Intent intent = new Intent(editinf.this, stadium.class);// test��ʵ��login
	    			data.putString("user", user);
	    			data.putString("name", name);
	    			intent.putExtras(data);
	    			startActivity(intent);
	    			finish();
	            }
	    });
		normalDialog.show();
	}
	class listen_back implements OnClickListener {
		@Override
		public void onClick(View v) {
			Bundle data = new Bundle();
			Intent intent = new Intent(editinf.this, stadium.class);// test��ʵ��login
			data.putString("user", user);
			data.putString("name", name);
			intent.putExtras(data);
			startActivity(intent);
			finish();
		}
	}
	private void initView() {
		selectButton = (Button) this.findViewById(R.id.selectpng);
		uploadButton = (Button) this.findViewById(R.id.loginpng);
		selectButton.setOnClickListener(this);
		uploadButton.setOnClickListener(this);
		imageView = (ImageView) this.findViewById(R.id.imageViewedit);
		uploadImageResult = (TextView) findViewById(R.id.uploadImageResult);
		progressDialog = new ProgressDialog(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.selectpng:
			Intent intent = new Intent(editinf.this, select_pic.class);
			startActivityForResult(intent, TO_SELECT_PHOTO);
			break;
		case R.id.loginpng:
			if (picPath != null) {
				handler.sendEmptyMessage(TO_UPLOAD_FILE);
			} else {
				Toast.makeText(this, "�ϴ����ļ�·������", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
			picPath = data.getStringExtra(select_pic.KEY_PHOTO_PATH);
			Log.i(TAG, "����ѡ���ͼƬ=" + picPath);
			Bitmap bm = BitmapFactory.decodeFile(picPath);
			imageView.setImageBitmap(bm);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * �ϴ���������Ӧ�ص�
	 */
	@Override
	public void onUploadDone(int responseCode, String message) {
		progressDialog.dismiss();
		Message msg = Message.obtain();
		msg.what = UPLOAD_FILE_DONE;
		msg.arg1 = responseCode;
		msg.obj = message;
		handler.sendMessage(msg);
	}

	private void toUploadFile() {
		uploadImageResult.setText("�����ϴ���...");
		progressDialog.setMessage("�����ϴ��ļ�...");
		progressDialog.show();
		String fileKey = "img";
		UploadUtil uploadUtil = UploadUtil.getInstance();
		uploadUtil.setOnUploadProcessListener(this); // ���ü����������ϴ�״̬

		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", "11111");
		uploadUtil.uploadFile(picPath, fileKey, requestURL, params);
	}

	@Override
	public void onUploadProcess(int uploadSize) {
		Message msg = Message.obtain();
		msg.what = UPLOAD_IN_PROCESS;
		msg.arg1 = uploadSize;
		handler.sendMessage(msg);
	}

	@Override
	public void initUpload(int fileSize) {
		Message msg = Message.obtain();
		msg.what = UPLOAD_INIT_PROCESS;
		msg.arg1 = fileSize;
		handler.sendMessage(msg);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TO_UPLOAD_FILE:
				toUploadFile();
				break;

			case UPLOAD_INIT_PROCESS:
				break;

			case UPLOAD_IN_PROCESS:
				break;

			case UPLOAD_FILE_DONE:
				path = ""+msg.obj;
				String result = "��Ӧ�룺" + msg.arg1 + "\n��Ӧ��Ϣ��" + "success"
						+ "\n��ʱ��" + UploadUtil.getRequestTime() + "��";
				uploadImageResult.setText(result);
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	public void sendjson() {
		String url = "http://192.168.191.1:8080/web/editstadServlet";
		HttpPost post = new HttpPost(url);
		try {
			JSONObject json1 = new JSONObject();
			Object username = user;
			json1.put("username", username);
			Object stnm = staname;
			Object picpath = path;
			json1.put("picpath", picpath);
			json1.put("stadname", stnm);
			Object stoptm = staoptime;
			json1.put("stadtime", stoptm);
			Object stadd = staadddr;
			json1.put("stadaddr", stadd);
			Object statel = statele;
			json1.put("stadtele", statel);
			Object stapr = stapri;
			json1.put("stadpri", stapr);
			Object staot = staother;
			json1.put("stadother", staot);
			Object mname = name;
			json1.put("name", mname);
			StringEntity se = new StringEntity(json1.toString(),"UTF-8");
//			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
//					"application/json"));
			post.setEntity(se);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String s;
			StringBuffer result = new StringBuffer("");
			while ((s = reader.readLine()) != null) {
				result.append(s);
			}
			reader.close();
			JSONObject json = new JSONObject(result.toString());
			Bundle data = new Bundle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	class thread implements Runnable {
		@Override
		public void run() {
			sendjson();
		}
	}
}