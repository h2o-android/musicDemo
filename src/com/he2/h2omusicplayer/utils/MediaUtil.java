package com.he2.h2omusicplayer.utils;

import java.util.ArrayList;
import java.util.List;

import com.he2.h2omusicplayer.ConstantValue;
import com.he2.h2omusicplayer.bean.Music;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;


public class MediaUtil {
	private List<Music> songList = new ArrayList<Music>();
	// 当前列表所在位置
	public static int CURRENTPOS = 0;
	//constanvalue是常量信息
	public static int PLAYSTATE = ConstantValue.OPTION_PAUSE;
	//实例化一个MediaUtil，并且静态，让所以程序获取的数据都一致
	private static MediaUtil instance = new MediaUtil();

	private MediaUtil() {
	}

	public static MediaUtil getInstacen() {
		return instance;
	}

	public List<Music> getSongList() {
		return songList;
	}
	
	/**
	 * 初始化音乐播放列表
	 * @param context
	 */
	public void initMusics(Context context) {
		songList.clear();
		//获取系统提取的sd卡的媒体信息，呈现到列表里面
		Cursor cur = context
				.getContentResolver()
				.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
						new String[] { MediaStore.Audio.Media.TITLE,
								MediaStore.Audio.Media.DURATION,
								MediaStore.Audio.Media.ARTIST,
								MediaStore.Audio.Media._ID,
								MediaStore.Audio.Media.DATA }, null, null, null);
		try {
			if (cur != null) {

				while (cur.moveToNext()) {
					Music m = new Music();
					m.setTitle(cur.getString(0));
					m.setDuration(cur.getString(1));
					m.setArtist(cur.getString(2));
					m.setId(cur.getString(3));
					m.setPath(cur.getString(4));
					songList.add(m);
				}
			}
		} catch (Exception e) {
		} finally {
			if (cur != null)
				cur.close();
		}

	}

	public Music getCurrent() {
		return songList.get(CURRENTPOS);
	}

}
