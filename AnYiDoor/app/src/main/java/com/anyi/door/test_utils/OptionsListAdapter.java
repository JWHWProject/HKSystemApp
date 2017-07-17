package com.anyi.door.test_utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.anyi.door.R;

import java.util.List;


public class OptionsListAdapter extends BaseAdapter {
	private Context mContext;
	ListView lv ;
	int index;
	public List<QuestionOptionBean> options ;
	
	public OptionsListAdapter(Context context, ListView lv) {
		this.mContext = context;
		this.lv = lv;
		 
	}

	public int getCount() {
		return 4;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		 
		return true;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
	 
			View view = LayoutInflater.from(mContext).inflate(
					R.layout.list_item_option, null);
			CheckBox ctv = (CheckBox) view.findViewById(R.id.ctv_name);
			TextView option = (TextView) view.findViewById(R.id.tv_option);
			
			option.setText("描述       1111");
			updateBackground(position, ctv);
			return view;
	 
	}

	public void updateBackground(int position, View view) {
		int backgroundId;
		if (lv.isItemChecked(position )) {
			backgroundId = R.mipmap.about_icon;
			view.setBackgroundResource(R.color.red_color);
		} else {
			backgroundId =  R.mipmap.address_icon;
			view.setBackgroundResource(R.color.gray);
		}
//		Drawable background = mContext.getResources().getDrawable(backgroundId);
//		view.setBackgroundDrawable(background) ;
	}

}
