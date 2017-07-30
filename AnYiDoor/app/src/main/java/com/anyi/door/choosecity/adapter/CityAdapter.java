package com.anyi.door.choosecity.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyi.door.R;
import com.anyi.door.choosecity.widget.ContactItemInterface;
import com.anyi.door.choosecity.widget.ContactListAdapter;

import java.util.List;


public class CityAdapter extends ContactListAdapter
{

	public CityAdapter(Context _context, int _resource,
			List<ContactItemInterface> _items)
	{
		super(_context, _resource, _items);
	}

	public void populateDataForRow(View parentView, ContactItemInterface item,
			int position)
	{
		View infoView = parentView.findViewById(R.id.infoRowContainer);
		TextView nicknameView = (TextView) infoView
				.findViewById(R.id.cityName);
		ImageView type_iv=(ImageView)infoView.findViewById(R.id.type_iv);
		if(item.getDisplayType()==0){
			type_iv.setImageResource(R.mipmap.type0);
		}else{
			type_iv.setImageResource(R.mipmap.type1);
		}
		nicknameView.setText(item.getDisplayInfo());
	}

}
