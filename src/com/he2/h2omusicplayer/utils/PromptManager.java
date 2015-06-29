package com.he2.h2omusicplayer.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.widget.Toast;

import com.he2.h2omusicplayer.R;


/**
 * 扫描SD进度提示
 */

public class PromptManager {
	private static ProgressDialog dialog;

	public static void showProgressDialog(Context context) {
		dialog = new ProgressDialog(context);
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setTitle(R.string.app_name);

		dialog.setMessage("正在扫描SD卡信息");
		dialog.show();
	}

	public static void closeProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}
	/**
	 * 褰撳垽鏂綋鍓嶆墜鏈烘病鏈夌綉缁滄椂浣跨敤
	 * @param context
	 */
	public static void showNoNetWork(final Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_launcher)//
				.setTitle(R.string.app_name)//
				.setMessage("褰撳墠鏃犵綉缁�").setPositiveButton("璁剧疆", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 璺宠浆鍒扮郴缁熺殑缃戠粶璁剧疆鐣岄潰
						Intent intent=new Intent();
						intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
						context.startActivity(intent);
						
					}
				}).setNegativeButton("鐭ラ亾浜�", null).show();
	}
	
	/**
	 * 閫�嚭绯荤粺
	 * @param context
	 */
	public static void showExitSystem(Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_launcher)//
				.setTitle(R.string.app_name)//
				.setMessage("鏄惁閫�嚭搴旂敤").setPositiveButton("纭畾", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						android.os.Process.killProcess(android.os.Process.myPid());
						//澶氫釜Activity鈥斺�鎳掍汉鍚功锛氭病鏈夊交搴曢�鍑哄簲鐢�
						//灏嗘墍鏈夌敤鍒扮殑Activity閮藉瓨璧锋潵锛岃幏鍙栧叏閮紝骞叉帀
						//BaseActivity鈥斺�onCreated鈥斺�鏀惧埌瀹瑰櫒涓�
					}
				})//
				.setNegativeButton("鍙栨秷", null)//
				.show();

	}
	/**
	 * 显示错误信息
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showErrorDialog(Context context, String msg) {
		new AlertDialog.Builder(context)//
				.setIcon(R.drawable.ic_launcher)//
				.setTitle(R.string.app_name)//
				.setMessage(msg)//
				.setNegativeButton("OK", null)//
				.show();
	}
	
	
	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public static void showToast(Context context, int msgResId) {
		Toast.makeText(context, msgResId, Toast.LENGTH_LONG).show();
	}
	//褰撴祴璇曢樁娈垫椂true
	private static final boolean isShow = true;

	/**
	 * 娴嬭瘯鐢�
	 * 鍦ㄦ寮忔姇鍏ュ競鍦猴細鍒�
	 * @param context
	 * @param msg
	 */
	public static void showToastTest(Context context, String msg) {
		if (isShow) {
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	}
	

}
