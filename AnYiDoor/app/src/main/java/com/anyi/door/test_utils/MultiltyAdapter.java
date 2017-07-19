package com.anyi.door.test_utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.anyi.door.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nj.www.my_module.bean.index.ExamBean;
import cn.nj.www.my_module.bean.index.OnlineTrainingAnswer;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;

/**
 * 多选
 */
public class MultiltyAdapter extends BaseAdapter
{
    private List<ExamBean.ExamDetailBean> list;

    private Context mContext;

    private String time;

    private Map<Integer, Map<Integer, Boolean>> checkMap;

    public boolean isFinishAll()
    {
        if (checkMap.size() == list.size())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public List<OnlineTrainingAnswer> getAnswerList()
    {
        List<OnlineTrainingAnswer> answerList = new ArrayList<>();
        for (int i = 0; i < checkMap.size(); i++)
        {

            String currentAnswer = "";
            if (GeneralUtils.isNotNull(checkMap.get(i).get(1)) && checkMap.get(i).get(1))
            {
                currentAnswer += "1|";
            }
            if (GeneralUtils.isNotNull(checkMap.get(i).get(2)) &&checkMap.get(i).get(2))
            {
                currentAnswer += "2|";
            }
            if (GeneralUtils.isNotNull(checkMap.get(i).get(3)) &&checkMap.get(i).get(3))
            {
                currentAnswer += "3|";
            }
            if (GeneralUtils.isNotNull(checkMap.get(i).get(4)) &&checkMap.get(i).get(4))
            {
                currentAnswer += "4|";
            }
            if (GeneralUtils.isNotNull(checkMap.get(i).get(5)) &&checkMap.get(i).get(5))
            {
                currentAnswer += "5|";
            }
            if (currentAnswer.endsWith("|"))
            {
                currentAnswer = currentAnswer.substring(0, currentAnswer.length() - 1);
            }
            OnlineTrainingAnswer answer = getItem(i).getOnlineTrainingAnswer();
            answer.setExamID(examID);
            answer.setUserAnswer(currentAnswer);
            answer.setCreateTimeStr(time);
            answerList.add(answer);
            CMLog.e("hq","多选："+answer.toString());
        }
        return answerList;
    }

    public Map<Integer, Map<Integer, Boolean>> getcheckMap()
    {
        for (int i = 0; i < checkMap.size(); i++)
        {
            CMLog.e("http", i + " " + checkMap.get(i).get(1) + " " + checkMap.get(i).get(2) + " " + checkMap.get(i).get(3) + " " + checkMap.get(i).get(4));

        }
        return checkMap;
    }

    String examID;

    public MultiltyAdapter(Context context, List<ExamBean.ExamDetailBean> valueList, String examID)
    {
        this.mContext = context;
        this.list = valueList;
        this.examID = examID;
        if (list.size() > 0)
        {
            time = list.get(0).getCreateTime();
        }
        checkMap = new HashMap<Integer, Map<Integer, Boolean>>();
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public ExamBean.ExamDetailBean getItem(int position)
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
            holder.textView1 = (TextView) view.findViewById(R.id.textView1);
            holder.cb1 = (CheckBox) view.findViewById(R.id.cb1);
            holder.cb2 = (CheckBox) view.findViewById(R.id.cb2);
            holder.cb3 = (CheckBox) view.findViewById(R.id.cb3);
            holder.cb4 = (CheckBox) view.findViewById(R.id.cb4);
            holder.cb5 = (CheckBox) view.findViewById(R.id.cb5);
            view.setTag(holder);
        }
        //给每个checkbox设置这个checkbox属于的item的position,以便在点击事件中获取所属的item 的索引值
        holder.cb1.setTag(position);
        holder.cb2.setTag(position);
        holder.cb3.setTag(position);
        holder.cb4.setTag(position);
        holder.textView1.setTag(position);

        holder.textView1.setText((position + 1) + getItem(position).getQuestion());
        holder.cb4.setVisibility(View.GONE);
        holder.cb5.setVisibility(View.GONE);
        List<String> currentOption = getItem(position).getOptionList();
        for (int i = 0; i < currentOption.size(); i++)
        {

            if (i == 0)
            {
                holder.cb1.setText(currentOption.get(i));
            }
            else if (i == 1)
            {
                holder.cb2.setText(currentOption.get(i));
            }
            else if (i == 2)
            {
                holder.cb3.setText(currentOption.get(i));

            }
            else if (i == 3)
            {
                holder.cb4.setText(currentOption.get(i));
                holder.cb4.setVisibility(View.VISIBLE);
            }
            else if (i == 4)
            {
                holder.cb5.setText(currentOption.get(i));
                holder.cb5.setVisibility(View.VISIBLE);
            }


        }
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
        holder.cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                int i = (Integer) buttonView.getTag();
                mapList.put(5, isChecked);
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
            if (map3.get(5) != null)
            {
                holder.cb5.setChecked(map3.get(5));
            }
            else
            {
                holder.cb5.setChecked(false);
            }
        }
        else
        {
            holder.cb1.setChecked(false);
            holder.cb2.setChecked(false);
            holder.cb3.setChecked(false);
            holder.cb4.setChecked(false);
            holder.cb5.setChecked(false);
        }
        return view;
    }


    class ViewHolder
    {
        TextView textView1;

        CheckBox cb1;

        CheckBox cb2;

        CheckBox cb3;

        CheckBox cb4;

        CheckBox cb5;
    }
}