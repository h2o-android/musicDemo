package com.he2.h2omusicplayer.reserver;

import com.he2.h2omusicplayer.ConstantValue;
import com.he2.h2omusicplayer.utils.HandlerManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * 系统刷新媒体列表的广播接收者
 * 
 * @author Administrator
 * 
 */
public class ScanSdFilesReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (Intent.ACTION_MEDIA_SCANNER_STARTED.equals(action)) {
			//开始扫描的时候通知
			HandlerManager.getHandler().sendEmptyMessage(ConstantValue.STARTED);
		}
		if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action)) {
			//扫描结束的时候通知
			HandlerManager.getHandler().sendEmptyMessage(ConstantValue.FINISHED);
		}

	}

}
