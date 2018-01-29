package com.cherish.livewallpaper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by lijianglong on 2018/1/29.
 */

public class LiveActivity extends Activity {

	public static void actionToLiveActivity(Context pContext) {
		Intent intent = new Intent(pContext, LiveActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		pContext.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		L.d("onCreate");

		Window window = getWindow();
		//放在左上角
		window.setGravity(Gravity.START | Gravity.TOP);
		WindowManager.LayoutParams attributes = window.getAttributes();
		//宽高设计为1个像素
		attributes.width = 1;
		attributes.height = 1;
		//起始坐标
		attributes.x = 0;
		attributes.y = 0;
		window.setAttributes(attributes);

		ScreenManager.getInstance(this).setActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		L.d("onDestroy");
	}
}