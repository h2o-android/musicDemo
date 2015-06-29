package com.he2.h2omusicplayer;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.he2.h2omusicplayer.adapter.MySongListAdapter;
import com.he2.h2omusicplayer.bean.Music;
import com.he2.h2omusicplayer.reserver.ScanSdFilesReceiver;
import com.he2.h2omusicplayer.service.MediaService;
import com.he2.h2omusicplayer.utils.HandlerManager;
import com.he2.h2omusicplayer.utils.MediaUtil;
import com.he2.h2omusicplayer.utils.PromptManager;



public class MainActivity extends Activity {
	/************ 加载资源 ****************/
	private ListView songListView;
	private MySongListAdapter songAdapter;
	private ScanSdFilesReceiver scanReceiver;
	
	private Handler handler = new Handler(){
		public void handlerMessage(Message msg){
			switch (msg.what) {
			case ConstantValue.STARTED:
					//Sd卡媒体数据扫描开始,显示进度
					PromptManager.showProgressDialog(MainActivity.this);
				break;
			case ConstantValue.FINISHED:
					//SD卡媒体数据扫描结束，开始加载界面
					MediaUtil.getInstacen().initMusics(MainActivity.this);
					PromptManager.closeProgressDialog();
					songAdapter.notifyDataSetChanged();
					//取消监听
					unregisterReceiver(scanReceiver);
				break;
			case ConstantValue.PLAY_END:
					/*播放结束
					 * 播放模式：单曲循环，顺序播放，循环播放，顺序播放，随机播放
					 * 单曲循环：记录当前播放位置
					 * 顺序播放：当前位置+1
					 * 循环播放：判断，如果大于songlist的大小，置播放位置为0
					 * 随机播放：Random.nextIn() songList.size();
					 */
					changeNotice(Color.WHITE);
					MediaUtil.CURRENTPOS++;
					
					//当音乐文件未到列表尾部时，向下一首播放
					if(MediaUtil.CURRENTPOS<MediaUtil.getInstacen().getSongList().size()){
						Music music = MediaUtil.getInstacen().getSongList().get(MediaUtil.CURRENTPOS);
						startPlayService(music, ConstantValue.OPTION_PLAY);
						changeNotice(Color.GREEN);
					}
				break;
			default:
				break;
			}
		}
	};
	
	/************* 音乐播放控制 ****************/
	private ImageView playPause;// 播放暂停
	private ImageView playNext;// 下一首
	private ImageView playPrev;// 上一首
	private ImageView playMode;// 修改播放模式

	/**********************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		HandlerManager.putHandler(handler);
		
		init();
		setListener();
	}

	private void setListener() {
		playPause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				switch (MediaUtil.PLAYSTATE) {
				case ConstantValue.OPTION_PLAY:
				case ConstantValue.OPTION_CONTINUE:
					startPlayService(null, ConstantValue.OPTION_PAUSE);
					playPause.setImageResource(R.drawable.img_playback_bt_play);
					break;
				case ConstantValue.OPTION_PAUSE:
					if (MediaUtil.CURRENTPOS >= 0
							&& MediaUtil.CURRENTPOS < MediaUtil.getInstacen()
									.getSongList().size()) {
						startPlayService(MediaUtil.getInstacen().getSongList()
								.get(MediaUtil.CURRENTPOS),
								ConstantValue.OPTION_CONTINUE);
						playPause
								.setImageResource(R.drawable.appwidget_pause);

					}
					break;
				}
			}
		});

		playNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// int temp=MediaUtil.CURRENTPOS;
				if (MediaUtil.getInstacen().getSongList().size() > MediaUtil.CURRENTPOS + 1) {
					changeNotice(Color.WHITE);
					MediaUtil.CURRENTPOS++;
					startPlayService(
							MediaUtil.getInstacen().getSongList()
									.get(MediaUtil.CURRENTPOS),
							ConstantValue.OPTION_PLAY);
					playPause.setImageResource(R.drawable.appwidget_pause);
					MediaUtil.PLAYSTATE = ConstantValue.OPTION_PLAY;
					changeNotice(Color.GREEN);
				}

			}
		});
		playPrev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (MediaUtil.CURRENTPOS > 0) {
					changeNotice(Color.WHITE);
					MediaUtil.CURRENTPOS--;
					startPlayService(
							MediaUtil.getInstacen().getSongList()
									.get(MediaUtil.CURRENTPOS),
							ConstantValue.OPTION_PLAY);
					playPause.setImageResource(R.drawable.appwidget_pause);
					MediaUtil.PLAYSTATE = ConstantValue.OPTION_PLAY;

					changeNotice(Color.GREEN);
				}

			}
		});

		songListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				changeNotice(Color.WHITE);

				MediaUtil.CURRENTPOS = position;
				Music music = MediaUtil.getInstacen().getSongList()
						.get(MediaUtil.CURRENTPOS);
				startPlayService(music, ConstantValue.OPTION_PLAY);
				playPause.setImageResource(R.drawable.appwidget_pause);
				// songAdapter.notifyDataSetChanged();
				changeNotice(Color.GREEN);
				System.out.println(MediaUtil.CURRENTPOS);

			}
		});
		
	}
	
	
	private void startPlayService(Music music, int option) {
		Intent intent = new Intent(getApplicationContext(), MediaService.class);
		if (music != null) {
			intent.putExtra("file", music.getPath());
		}
		intent.putExtra("option", option);
		startService(intent);
	}
	
	private void init() {
		loadSongList();
		mediaController();
	}

	/**
	 * 注册系统刷新媒体列表的广播接收者
	 */
	public void reflash() {
		// Intent intent = new Intent();
		// intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
		// intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
		// sendBroadcast(intent);
		//Intent.ACTION_MEDIA_SCANNER_STARTED 表示MeidaScanner开始扫描
		IntentFilter intentFilter = new IntentFilter(
				Intent.ACTION_MEDIA_SCANNER_STARTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		intentFilter.addDataScheme("file");
		scanReceiver = new ScanSdFilesReceiver();
		registerReceiver(scanReceiver, intentFilter);
		sendBroadcast(new Intent(
				Intent.ACTION_MEDIA_MOUNTED,
				Uri.parse("file://" + Environment.getExternalStorageDirectory())));
	}
	
	/**
	 * 音乐控制
	 */
	private void mediaController() {
		// TODO Auto-generated method stub
		playPause = (ImageView) findViewById(R.id.imgPlay);
		playPrev = (ImageView) findViewById(R.id.imgPrev);
		playNext = (ImageView) findViewById(R.id.imgNext);

		if (MediaUtil.PLAYSTATE == ConstantValue.OPTION_PAUSE) {
			playPause.setImageResource(R.drawable.img_playback_bt_play);
		}
	}

	/**
	 * 加载声音列表
	 */
	private void loadSongList() {
		// 在手机的多媒体数据库中查询声音，数量过多，所以建议在子线程里面完成
		// MediaUtil.getInstacen().initMusics(getApplicationContext());
		songAdapter = new MySongListAdapter(getApplicationContext());
		songListView = (ListView) findViewById(R.id.play_list);
		songListView.setAdapter(songAdapter);
		// new InitDataTask().execute();//线程池，如果操作线程过多，出现等待的情况
		new InitDataTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);// 无需等待线程池释放，可以直接调用
	}

	/**
	 * 音乐资源过多，采取异步加载
	 */
	class InitDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			PromptManager.showProgressDialog(MainActivity.this);
		}

		// 在后台运行
		@Override
		protected Void doInBackground(Void... params) {
			// 加载多媒体信息
			MediaUtil.getInstacen().initMusics(MainActivity.this);
			// SystemClock.sleep(100);
			return null;
		}

		// 通知列表进行更新
		@Override
		protected void onPostExecute(Void result) {
			PromptManager.closeProgressDialog();
			songAdapter.notifyDataSetChanged();
		}
	}
	
	/**
	 * 改变文字颜色
	 * @param color
	 */
	private void changeNotice(int color) {
		// TODO Auto-generated method stub
		TextView tv = (TextView) songListView.findViewWithTag(MediaUtil.CURRENTPOS);
		
		if(tv != null){
			tv.setTextColor(color);
		}
	}
	
}
