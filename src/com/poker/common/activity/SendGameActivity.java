package com.poker.common.activity;

import java.io.File;
import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.poker.common.BaseApplication;
import com.poker.common.R;
import com.poker.common.util.SettingHelper;
import com.poker.common.wifi.WifiHotManager;
import com.poker.common.wifi.SocketServer.WifiCreateListener;
import com.poker.common.wifi.transfer.CopyUtil;
import com.poker.common.wifi.transfer.WebService;

/**
 *
 * ��˵��
 *
 * @author RinfonChen:
 * @Day 2014��8��4�� 
 * @Time ����7:16:39
 * @Declaration :
 *
 */
public class SendGameActivity extends AbsBaseActivity implements WifiCreateListener{

    private ImageButton backBtn;
    
    private ImageView imgQR;
    
    private BaseApplication app;
    
    private Intent intent;
    
    private SettingHelper helper;
    
	private static final int QR_WIDTH = 150;
	
	private static final int QR_HEIGHT = 150;

	private static final String TAG = "QRCode";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_game_activity);
        app = (BaseApplication) getApplication();
        imgQR = (ImageView) findViewById(R.id.img_qr);
        backBtn = (ImageButton)findViewById(R.id.send_game_back_btn);
        helper = new SettingHelper(this);
        initFiles();
        intent = new Intent(this, WebService.class);
        startService(intent);
        backBtn.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        startWifi();
    }
    
    private Handler handler = new Handler();
    
    private void startWifi(){
    	if(app.wm==null){
			app.wm = WifiHotManager.getInstance(app, null);
		}
		if(app.isConnected){
			if(app.isServer())
				app.getServer().clearServer();
			else
				app.getClient().clearClient();
		}
		app.wm.disableWifiHot();
        app.wm.startAWifiHot("Join_Me", this);
    }
    
    private void initFiles() {
		new CopyUtil(this).assetsCopy();
	}
    
    
    private Runnable runWithSDCard = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub

			String strDir = Environment.getExternalStorageDirectory().getPath()
					+"/.poker";
			String strFile = strDir +"/commonpoker.apk";
			File target =new File(strFile);
			createImage("http://192.168.43.1:"+WebService.PORT+target.getPath());
		}
	};
    
    private Runnable runNoSDCard = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
			String path = "";
			for(PackageInfo info:packs){
				if(info.applicationInfo.packageName.contains("com.poker.common")){
					path = info.applicationInfo.publicSourceDir;
					break;
				}
			}
			File file = new File(path);
			
			if(file.exists()){
				createImage("http://192.168.43.1:"+WebService.PORT+path);
			}
		}
	};
    
    private void createImage(String text) {
        try {
            QRCodeWriter writer = new QRCodeWriter();

            Log.i(TAG, "创建二维码内容：" + text);
            if (text == null || "".equals(text) || text.length() < 1) {
                return;
            }

            // ��������ı�תΪ��ά��
            BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
                    QR_WIDTH, QR_HEIGHT);

            System.out.println("w:" + martix.getWidth() + "h:"
                    + martix.getHeight());

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }

                }
            }

            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);

            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            refreshImage(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    
    private void refreshImage(final Bitmap bitmap){
    	handler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
		        imgQR.setImageBitmap(bitmap);
			}
		});
    }

	@Override
	public void onCreateSuccess() {
		// TODO Auto-generated method stub
		Log.i("SendGameActivity", "热点创建成功");
        if(Environment.getExternalStorageState()
        		.equals(android.os.Environment.MEDIA_MOUNTED)){

			String strDir = Environment.getExternalStorageDirectory().getPath()
					+"/.poker";
			String strFile = strDir +"/commonpoker.apk";
			File target =new File(strFile);
        	if(target.exists()){
        		new Thread(runWithSDCard).start();
        	}else{
        		Toast.makeText(this, "WTF", Toast.LENGTH_SHORT).show();
        		//FrankChan:maybe we need a refresh button
        	}
        }else{
        	Toast.makeText(this, "检测到您没有SD卡，传包可能失败", Toast.LENGTH_SHORT).show();
        	new Thread(runNoSDCard).start();
        }
	}

	@Override
	public void OnCreateFailure(String strError) {
		// TODO Auto-generated method stub
		Log.i("SendGameActivity", "创建热点失败");
	}


    
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
		app.wm.disableWifiHot();
		stopService(intent);
        super.onDestroy();
    }
}
