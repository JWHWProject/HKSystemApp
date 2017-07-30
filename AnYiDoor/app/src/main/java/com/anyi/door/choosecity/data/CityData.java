package com.anyi.door.choosecity.data;

import com.anyi.door.choosecity.model.CityItem;
import com.anyi.door.choosecity.widget.ContactItemInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * 357���й�����
 * @author ck
 * @since 2014��2��7�� 16:20:32
 */
public class CityData
{
//	static String cityJson1 = "{'cities':['#efg','#dd','abc','zzz','hqqq']}";
//	public static List<ContactItemInterface> getSampleContactList(String cityJson)
//	{
//		Log.i("hq", cityJson);
//		List<ContactItemInterface> list = new ArrayList<ContactItemInterface>();
//
//		try
//		{
//			JSONObject jo1 = new JSONObject(cityJson);
//
//			JSONArray ja1 = jo1.getJSONArray("cities");
//
//			for(int i = 0; i < ja1.length(); i++)
//			{
//				String cityName = ja1.getString(i);
//
//				list.add(new CityItem(cityName, PinYin.getPinYin(cityName)));
//			}
//		}
//		catch (JSONException e)
//		{
//			e.printStackTrace();
//		}
//
//		return list;
//	}
//
//	public static List<ContactItemInterface> getSampleContactList()
//	{
//		List<ContactItemInterface> list = new ArrayList<ContactItemInterface>();
//
//		try
//		{
//			JSONObject jo1 = new JSONObject(cityJson1);
//
//			JSONArray ja1 = jo1.getJSONArray("cities");
//
//			for(int i = 0; i < ja1.length(); i++)
//			{
//				String cityName = ja1.getString(i);
//
//				list.add(new CityItem(cityName, PinYin.getPinYin(cityName)));
//			}
//		}
//		catch (JSONException e)
//		{
//			e.printStackTrace();
//		}
//
//		return list;
//	}
public static List<ContactItemInterface> getSampleContactList(List<CityItem> plist)
{
	List<ContactItemInterface> list = new ArrayList<ContactItemInterface>();

	try
	{
//		for(int i = 0; i < plist.size(); i++)
//		{
////			Log.e("jw","id="+plist.get(i).getId()+"name="+plist.get(i).getName());
//			list.add(new CityItem(plist.get(i).getName(), PinYin.getPinYin(plist.get(i).getName()),plist.get(i).getId(),plist.get(i).getType()));
//		}
		list.addAll(plist);
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}

	return list;
}
}
