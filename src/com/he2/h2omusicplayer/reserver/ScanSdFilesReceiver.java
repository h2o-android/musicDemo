package com.he2.h2omusicplayer.reserver;

import com.he2.h2omusicplayer.ConstantValue;
import com.he2.h2omusicplayer.utils.HandlerManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * ϵͳˢ��ý���б�Ĺ㲥������
 * 
 * @author Administrator
 * 
 */
public class ScanSdFilesReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (Intent.ACTION_MEDIA_SCANNER_STARTED.equals(action)) {
			//��ʼɨ���ʱ��֪ͨ
			HandlerManager.getHandler().sendEmptyMessage(ConstantValue.STARTED);
		}
		if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action)) {
			//ɨ�������ʱ��֪ͨ
			HandlerManager.getHandler().sendEmptyMessage(ConstantValue.FINISHED);
		}

	}

}
