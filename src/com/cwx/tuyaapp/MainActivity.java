package com.cwx.tuyaapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cwx.tuyaapp.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	private ImageView imageView;
	private Button saveButton;
	private Button btclear;
	private Bitmap panel;
	private Canvas canvas;
	private Paint paint;
	
	private int downX;//���µ�x
	private int downY; //���µ�y
	private int moveX;
	private int moveY;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		imageView=(ImageView) findViewById(R.id.image_view);
		saveButton= (Button) findViewById(R.id.save_button);
		btclear=(Button)findViewById(R.id.bt_clear);
		//��ImageView���һ�������¼�
		imageView.setOnTouchListener(new MyOnTouchListener());
		
		//��Button����һ������ļ����¼�
		saveButton.setOnClickListener(this);
		//�������ť����һ������¼�
		btclear.setOnClickListener(this);
	}
	class MyOnTouchListener implements OnTouchListener{
		
		/**
		 * ��ImageView���û�����ָ����ʱ�������˷���
		 * 
		 * MotionEvent.ACTION_DOWN �Ȱ���
		 * MotionEvent.ACTION_MOVE �ƶ�
		 * MotionEvent.ACTION_UP ���̧�� 
		 * 
		 */

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// ��ȡ��ǰ�¼������ж�һ�£���ǰ�ǰ��� �ƶ� ̧��
			int action=event.getAction();//��ǰ�û������Ķ���
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				System.out.println("����");
				initpanel();
				
				//ȡ������ʱ��x��y���ֵ
				downX=(int) event.getX();
				downY=(int) event.getY();
				break;
			case MotionEvent.ACTION_UP:
				System.out.println("̧��");
				break;
			case MotionEvent.ACTION_MOVE:
				System.out.println("�ƶ�");
				
				//ȡ���ƶ�ʱ��x��y���ֵ
				moveX=(int) event.getX();
				moveY=(int) event.getY();
				
				//�Ѱ��µĴ�����ƶ��ĵ�����һ����
				
				canvas.drawLine(downX, downY, moveX, moveY,paint);
				imageView.setImageBitmap(panel);
				
				//�Ѱ��µ�x�Ͱ��µ�y���³��ƶ����x��y���ֵ
				downX=moveX;
				downY=moveY;
				break;

			default:
				break;
			}
			return true;
		}
		
	}
	private void initpanel(){
		if (panel==null) {
			//Canvas���� bitmap��ֽ ����
			
			//����һ�ſհ׵Ļ�ֽ��ָ����͸�:��ImageView�Ŀ�͸�һģһ����
			panel=Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Config.ARGB_8888);
			
			//��ʼ��һ������
			canvas=new Canvas(panel);
			
			//��ʼ������,ָ�����ʵ���ɫ��ָ�����ʻ��������ߵĴ�ϸ�̶�
			paint=new Paint();
			paint.setColor(Color.RED);
			paint.setStrokeWidth(5);
			
			//�û�����ֽ��һ����ɫ����ɫ
			canvas.drawColor(Color.MAGENTA);
			//�ѻ�ֽ��ImageView�ؼ�����ʾ
			imageView.setImageBitmap(panel);
		}
	}

	@Override
	public void onClick(View v) {
		
		int id=v.getId();
		if (id==R.id.save_button) {
			save();
		}else if (id==R.id.bt_clear) {
			//�ѻ����ϻ������ж������
			canvas.drawColor(Color.WHITE); //�ѻ�ֽ���ɰ�ɫ����ɫ
			//�ѻ��׵�ͼƬ���ø�ImageView��ʾ
			imageView.setImageBitmap(panel);
		}
	}
	/**
	 * ����ͼƬ
	 */
	private void save(){
		//�ѻ�������ͼƬ�������� 
			
		
				File cacheDir=Environment.getExternalStorageDirectory();
				File cacheFile=new File(cacheDir, "322.jpg");
				
				System.out.println("·����"+cacheFile.getPath());
				try {
					FileOutputStream out = new FileOutputStream(cacheFile);
					boolean isSuccess =panel.compress(CompressFormat.JPEG, 100, out);
					if (isSuccess) {
						Toast.makeText(this, "����ɹ�", 0).show();
					}else {
						Toast.makeText(this, "����ʧ��", 0).show();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	}
}
