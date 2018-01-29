package com.cherish.livewallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {

	private CheckBox mCbVoice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ScreenManager screenManager = ScreenManager.getInstance(MainActivity.this);
		ScreenBroadcastListener listener = new ScreenBroadcastListener(this);
		listener.registerListener(new ScreenBroadcastListener.ScreenStateListener() {
			@Override
			public void onScreenOn() {
				screenManager.finishActivity();
			}

			@Override
			public void onScreenOff() {
				screenManager.startActivity();
			}
		});
		LiveService.toLiveService(this);
		mCbVoice = (CheckBox) findViewById(R.id.id_cb_voice);
		Button btnSet = (Button) findViewById(R.id.btn_set);
		mCbVoice.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(
							CompoundButton buttonView, boolean isChecked) {

						if (isChecked) {
							VideoLiveWallpaper.voiceSilence(getApplicationContext());
						} else {
							VideoLiveWallpaper.voiceNormal(getApplicationContext());
						}
					}
				});
		btnSet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setVideoToWallPaper();
			}
		});
	}

	public void setVideoToWallPaper() {
		VideoLiveWallpaper.setToWallPaper(this, 111);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 111 && resultCode == RESULT_OK) {
			if (mCbVoice.isChecked()) {
				VideoLiveWallpaper.voiceSilence(getApplicationContext());
			} else {
				VideoLiveWallpaper.voiceNormal(getApplicationContext());
			}
			finish();
		}
	}
}
