package com.anyi.door;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.nj.www.my_module.bean.index.TrainBean;

/**
 * Created by huqing on 2017/7/14.
 */

public class MyExpandableListAdapter implements ExpandableListAdapter
{
    private Context mContext;

    List<TrainBean> trainBeanList;

    public MyExpandableListAdapter(Context mContext, List<TrainBean> trainBeanList)
    {
        this.mContext = mContext;
        this.trainBeanList = trainBeanList;
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer)
    {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer)
    {

    }

    @Override
    public int getGroupCount()
    {
        return trainBeanList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return trainBeanList.get(groupPosition).getTrainBeanDetailList().size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return trainBeanList.get(groupPosition);
    }

    @Override
    public TrainBean.TrainBeanDetail getChild(int groupPosition, int childPosition)
    {
        return trainBeanList.get(groupPosition).getTrainBeanDetailList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        GHolder holder;
        if (convertView == null)
        {
            convertView = View.inflate(mContext, R.layout.item_group_title, null);
            holder = new GHolder();

            holder.textView = (TextView) convertView.findViewById(R.id.group_name);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon_down);

            convertView.setTag(holder);
        }
        else
        {
            holder = (GHolder) convertView.getTag();
        }

        if (!isExpanded)
        {
            holder.imageView.setImageResource(R.mipmap.icon_down);
        }
        else
        {
            holder.imageView.setImageResource(R.mipmap.icon_up);
        }

        holder.textView.setText(trainBeanList.get(groupPosition).getTypeName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        CHolder holder;
        if (convertView == null)
        {
            convertView = View.inflate(mContext, R.layout.item_child_title, null);
            holder = new CHolder();

            holder.textView = (TextView) convertView.findViewById(R.id.child_name);

            convertView.setTag(holder);
        }
        else
        {
            holder = (CHolder) convertView.getTag();
        }
        holder.textView.setText(getChild(groupPosition, childPosition).getTrainingName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return false;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition)
    {

    }

    @Override
    public void onGroupCollapsed(int groupPosition)
    {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId)
    {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId)
    {
        return 0;
    }

    class GHolder
    {
        TextView textView;

        ImageView imageView;
    }

    class CHolder
    {
        TextView textView;
    }
}