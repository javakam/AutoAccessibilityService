package com.ando.autoservice;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class MainActivity extends Activity {

    TextView tvStart;
    TextView tvStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SECURE_SETTINGS}, 12);

        setContentView(R.layout.activity_main);
        tvStart = (TextView) findViewById(R.id.tv_start);
        tvStop = (TextView) findViewById(R.id.tv_stop);
        tvStart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AutoClickService.class);
                startService(i);
            }
        });

        tvStop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AutoClickService.class);
                stopService(i);
                tvStart.setText("开启服务");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (12 == requestCode) {
            if (permissions.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                AutoApplication.getInstance().openPermission();
            }
        }
    }
//
//	/**
//	 * 打印点击的点的坐标
//	 *
//	 * @param event
//	 * @return
//	 */
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//
//		int x = (int) event.getX();
//		int y = (int) event.getY();
//		tvStart.setText("X at " + x + ";Y at " + y);
//		return true;
//	}


}
