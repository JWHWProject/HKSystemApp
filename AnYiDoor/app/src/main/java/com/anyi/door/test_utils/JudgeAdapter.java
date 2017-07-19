package com.anyi.door.test_utils;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.anyi.door.R;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.bean.index.ExamBean;
import cn.nj.www.my_module.bean.index.OnlineTrainingAnswer;
import cn.nj.www.my_module.tools.CMLog;

import static com.anyi.door.R.id.answer0;
import static com.anyi.door.R.id.answer1;

/**
 * 单选
 */
public class JudgeAdapter extends BaseAdapter
{

    private  String time;

    LayoutInflater inflater;

    public List<ExamBean.ExamDetailBean> getList()
    {
        return list;
    }

    List<ExamBean.ExamDetailBean> list;

    public boolean isFinishAll()
    {
        for (int i = 0; i < getCount(); i++)
        {
            if (getItem(i).current == Model.NONE)
            {
                return false;
            }
        }
        return true;
    }

    public List<OnlineTrainingAnswer> getAnswerList()
    {
        List<OnlineTrainingAnswer> answerList = new ArrayList<>();
        for (int i = 0; i < getCount(); i++)
        {
            OnlineTrainingAnswer answer = getItem(i).getOnlineTrainingAnswer();
            answer.setExamID(examID);
            answer.setUserAnswer(getItem(i).current+"");
            answer.setCreateTimeStr(time);
            answerList.add(answer);
            CMLog.e("hq","判断："+answer.toString());
        }
        return answerList;
    }

    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();

    private String examID;

    public JudgeAdapter(Context context, List<ExamBean.ExamDetailBean> data, String examID)
    {
        inflater = LayoutInflater.from(context);
        this.list = data;
        this.examID = examID;
        if (list.size()>0){
            time = list.get(0).getCreateTime();
        }
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
        View v = convertView;
        ViewHolder holder = null;

        if (v == null)
        {
            v = inflater.inflate(R.layout.item_judge, parent, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
            holder.group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group,
                                             int checkedId)
                {
                    Integer pos = (Integer) group.getTag();
                    ExamBean.ExamDetailBean element = list.get(pos);
                    switch (checkedId)
                    {
                        case answer0:
                            element.current = Model.ANSWER_ONE_SELECTED;
                            break;
                        case answer1:
                            element.current = Model.ANSWER_TWO_SELECTED;
                            break;
                        default:
                            element.current = Model.NONE;
                    }

                }
            });
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }
        holder.group.setTag(new Integer(position)); // I passed the current position as a tag

        holder.t.setText((position + 1) + "." + list.get(position).getQuestion()); // Set the question body
        if (list.get(position).current != Model.NONE)
        {
            RadioButton r = (RadioButton) holder.group.getChildAt(list.get(position).current);
            r.setChecked(true);
        }
        else
        {
            holder.group.clearCheck();

        }
        return v;
    }

    class ViewHolder
    {
        TextView t = null;

        RadioGroup group;

        ViewHolder(View v)
        {
            t = (TextView) v.findViewById(R.id.textView1);
            group = (RadioGroup) v.findViewById(R.id.group_me);
        }

    }

}
