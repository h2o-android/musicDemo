package com.he2.h2omusicplayer;

public interface ConstantValue {
	/**
	 * 系统扫描SD的影音文件开始的通知
	 */
	int STARTED = 0;
	/**
	 * 系统扫描Sd卡上影音文件结束的通知
	 */
	int FINISHED = 1;
	/**
	 * 播发结束
	 */
	int PLAY_END = 2;
	/**
	 * 播放
	 */
	int OPTION_PLAY = 50;
	/**
	 * 暂停
	 */
	int OPTION_PAUSE = 51;
	/**
	 * 继续播放
	 */
	int OPTION_CONTINUE = 52;
	/**
	 * 淇敼杩涘害
	 */
	int OPTION_UPDATE_PROGESS = 53;
	/**
	 * 璁剧疆seekbar鐨勬渶澶у�
	 */
//	int SEEKBAR_MAX = 100;
	/**
	 * 淇敼seekbar浣嶇疆
	 */
	int SEEKBAR_CHANGE=101;
}
