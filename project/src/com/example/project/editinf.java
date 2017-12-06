package com.example.project;



import java.util.HashMap;
import java.util.Map;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.UploadUtil;
import com.example.project.UploadUtil.OnUploadProcessListener;

public class editinf extends Activity implements OnClickListener,OnUploadProcessListener{
	private String name="";
	private String user = "";
	private static final String TAG = "uploadImage";
	
	/**
	 * ȥ�ϴ��ļ�
	 */
	protected static final int TO_UPLOAD_FILE = 1;  
	/**
	 * �ϴ��ļ���Ӧ
	 */
	protected static final int UPLOAD_FILE_DONE = 2;  //
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
	private static String requestURL = "http://10.0.2.2:8080/fileUpload/file_upload";
	private Button selectButton,uploadButton;
	private ImageView imageView;
	private TextView uploadImageResult;
	
	private String picPath = null;
	private ProgressDialog progressDialog;
	
    /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editinf);
		final Button back = (Button)findViewById(R.id.back);
		back.setOnClickListener(new listen_back());
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
        user = data.getString("user");
        name = data.getString("name");
        initView();
	}
	class listen_back implements OnClickListener{
		@Override
    	public void onClick(View v){
    		Bundle data = new Bundle();
    		Intent intent = new Intent(editinf.this,stadium.class);//test��ʵ��login
			data.putString("user",user);
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
			Intent intent = new Intent(editinf.this,select_pic.class);
			startActivityForResult(intent, TO_SELECT_PHOTO);
			break;
		case R.id.loginpng:
			if(picPath!=null)
			{
				handler.sendEmptyMessage(TO_UPLOAD_FILE);
			}else{
				Toast.makeText(this, "�ϴ����ļ�·������", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO)
		{
			picPath = data.getStringExtra(select_pic.KEY_PHOTO_PATH);
			Log.i(TAG, "����ѡ���ͼƬ="+picPath);
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
	
	private void toUploadFile()
	{
		uploadImageResult.setText("�����ϴ���...");
		progressDialog.setMessage("�����ϴ��ļ�...");
		progressDialog.show();
		String fileKey = "img";
		UploadUtil uploadUtil = UploadUtil.getInstance();
		uploadUtil.setOnUploadProcessListener(this);  //���ü����������ϴ�״̬
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", "11111");
		uploadUtil.uploadFile( picPath,fileKey, requestURL,params);
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
		handler.sendMessage(msg );
	}
		
	private Handler handler = new Handler(){
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
				String result = "��Ӧ�룺"+msg.arg1+"\n��Ӧ��Ϣ��"+msg.obj+"\n��ʱ��"+UploadUtil.getRequestTime()+"��";
				uploadImageResult.setText(result);
				break;
				
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
}
