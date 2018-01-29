package com.cherish.livewallpaper;

import android.util.Log;

public class L {

	private static final String sTag = "logger";

	public static void d(String msg, Object... params) {

		Log.d(sTag, String.format(msg, params));

	}
}
