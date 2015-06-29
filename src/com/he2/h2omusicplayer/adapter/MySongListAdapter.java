package com.he2.h2omusicplayer.adapter;

import com.he2.h2omusicplayer.ConstantValue;
import com.he2.h2omusicplayer.R;
import com.he2.h2omusicplayer.utils.MediaUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MySongListAdapter extends BaseAdapter {

	private Context context;
	
	//private static int myposition = 0; 
	
	public MySongListAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {

		return MediaUtil.getInstacen().getSongList().size();
	}

	@Override
	public Object getItem(int position) {

		return MediaUtil.getInstacen().getSongList().get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listitem, null);
			holder.tx1 = (TextView) convertView.findViewById(R.id.ListItemName);
			holder.tx2 = (TextView) convertView
					.findViewById(R.id.ListItemContent);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		
		holder.tx1.setTextColor(Color.WHITE);
		holder.tx1.setTag(position);
		if (position == MediaUtil.CURRENTPOS
				&& (MediaUtil.PLAYSTATE == ConstantValue.OPTION_PAUSE || MediaUtil.PLAYSTATE == ConstantValue.OPTION_PLAY)) {
			holder.tx1.setTextColor(Color.GREEN);
		}
		//holder.tx1.setTag(myposition);
		//*.getINstacen()是实例化
		//获取歌曲名字
		holder.tx1.setText((position + 1)
				+ "."
				+ MediaUtil.getInstacen().getSongList().get(position)
						.getTitle());
		//获取演奏者
		holder.tx2
				.setText((MediaUtil.getInstacen().getSongList().get(position))
						.getArtist());
		//myposition++;
		//System.out.println(position);
		return convertView;
	}

	class ViewHolder {

		public TextView tx1;
		public TextView tx2;
	}
}
