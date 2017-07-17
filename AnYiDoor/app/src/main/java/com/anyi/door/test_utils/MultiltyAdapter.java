package com.anyi.door.test_utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.anyi.door.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nj.www.my_module.tools.CMLog;

/**
 * 单选
 */
public class MultiltyAdapter extends BaseAdapter
{
    private List<String> list;

    private Context mContext;


    private Map<Integer, Map<Integer, Boolean>> checkMap;

    public Map<Integer, Map<Integer, Boolean>> getcheckMap()
    {
        for (int i = 0; i < checkMap.size(); i++)
        {
            CMLog.e("http",i+" "+checkMap.get(i).get(1)+ " "+checkMap.get(i).get(2)+ " "+checkMap.get(i).get(3)+ " "+checkMap.get(i).get(4));
        }
        return checkMap;
    }

    public MultiltyAdapter(Context context)
    {
        this.mContext = context;
        list = new ArrayList<String>();
        for (int i = 0; i < 30; i++)
        {
            list.add("" + i + i + i + i);
        }
        checkMap = new HashMap<Integer, Map<Integer, Boolean>>();
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = null;
        ViewHolder holder = null;
        if (convertView != null)
        {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        else
        {
            holder = new ViewHolder();
            view = View.inflate(mContext, R.layout.item_choose, null);
            holder.cb1 = (CheckBox) view.findViewById(R.id.cb1);
            holder.cb2 = (CheckBox) view.findViewById(R.id.cb2);
            holder.cb3 = (CheckBox) view.findViewById(R.id.cb3);
            holder.cb4 = (CheckBox) view.findViewById(R.id.cb4);
            view.setTag(holder);
        }
        //给每个checkbox设置这个checkbox属于的item的position,以便在点击事件中获取所属的item 的索引值
        holder.cb1.setTag(position);
        holder.cb2.setTag(position);
        holder.cb3.setTag(position);
        holder.cb4.setTag(position);
        //用于存放每个item中checkbox的状态
        final Map<Integer, Boolean> mapList = new HashMap<Integer, Boolean>();

        holder.cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                //获取checkbox所属的item 的position
                int i = (Integer) buttonView.getTag();
                //将checkbox的状态放到一个map中
                mapList.put(1, isChecked);
                //将这个map作为值放到大的map中,用position作为键
                checkMap.put(i, mapList);
            }
        });
        holder.cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                int i = (Integer) buttonView.getTag();
                mapList.put(2, isChecked);
                checkMap.put(i, mapList);
            }
        });
        holder.cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                int i = (Integer) buttonView.getTag();
                mapList.put(3, isChecked);
                checkMap.put(i, mapList);
            }
        });
        holder.cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                int i = (Integer) buttonView.getTag();
                mapList.put(4, isChecked);
                checkMap.put(i, mapList);
            }
        });
        Map<Integer, Boolean> map3 = checkMap.get(position);
        if (map3 != null)
        {
            if (map3.get(1) != null)
            {
                holder.cb1.setChecked(map3.get(1));
            }
            else
            {
                holder.cb1.setChecked(false);
            }
            if (map3.get(2) != null)
            {
                holder.cb2.setChecked(map3.get(2));
            }
            else
            {
                holder.cb2.setChecked(false);
            }
            if (map3.get(3) != null)
            {
                holder.cb3.setChecked(map3.get(3));
            }
            else
            {
                holder.cb3.setChecked(false);
            }
            if (map3.get(4) != null)
            {
                holder.cb4.setChecked(map3.get(4));
            }
            else
            {
                holder.cb4.setChecked(false);
            }
        }
        else
        {
            holder.cb1.setChecked(false);
            holder.cb2.setChecked(false);
            holder.cb3.setChecked(false);
            holder.cb4.setChecked(false);
        }
        return view;
    }


    class ViewHolder
    {
        CheckBox cb1;

        CheckBox cb2;

        CheckBox cb3;

        CheckBox cb4;
    }
}