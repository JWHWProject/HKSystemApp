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
import cn.nj.www.my_module.tools.DialogUtil;

/**
 * 单选
 */
public class SingleListAdapter extends BaseAdapter
{

    LayoutInflater inflater;

    public List<ExamBean.ExamDetailBean> getList()
    {
        return list;
    }

    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();

    private String time;
    private String examID;
    Context context;
    public SingleListAdapter(Context context, List<ExamBean.ExamDetailBean> data,String examID)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = data;
        this.examID = examID;
        if (list.size()>0){
            time = list.get(0).getCreateTime();
        }
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
            //TODO:
            if (getItem(i).current>getItem(i).getOptionList().size()){
                DialogUtil.showCloseDialogOneButton(context,"客户端答案传错了","知道了","");
            }
            answer.setCreateTimeStr(time);
            answerList.add(answer);
        }
        return answerList;
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
            v = inflater.inflate(R.layout.item_single_choose, parent, false);
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
                        case R.id.answer0:
                            element.current = Model.ANSWER_ONE_SELECTED;
                            break;
                        case R.id.answer1:
                            element.current = Model.ANSWER_TWO_SELECTED;
                            break;
                        case R.id.answer2:
                            element.current = Model.ANSWER_THREE_SELECTED;
                            break;
                        case R.id.answer3:
                            element.current = Model.ANSWER_FOUR_SELECTED;
                            break;
                        case R.id.answer4:
                            element.current = Model.ANSWER_FOUR_SELECTED;
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

        holder.t.setText((position+1)+"."+list.get(position).getQuestion()); // Set the question body
        holder.answer2.setVisibility(View.GONE);
        holder.answer3.setVisibility(View.GONE);
        holder.answer4.setVisibility(View.GONE);
        for (int i = 0; i < list.get(position).getOptionList().size(); i++)
        {
            holder.group.getChildAt(i).setVisibility(View.VISIBLE);
            if (i == 0)
            {
                holder.answer0.setText(list.get(position).getOptionList().get(i));
            }
            else if (i == 1)
            {
                holder.answer1.setText(list.get(position).getOptionList().get(i));
            }
            else if (i == 2)
            {
                holder.answer2.setText(list.get(position).getOptionList().get(i));
            }
            else if (i == 3)
            {
                holder.answer3.setText(list.get(position).getOptionList().get(i));
            }
            else if (i == 4)
            {
                holder.answer4.setText(list.get(position).getOptionList().get(i));
            }
        }

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

        RadioButton answer0;

        RadioButton answer1;

        RadioButton answer2;

        RadioButton answer3;

        RadioButton answer4;

        ViewHolder(View v)
        {
            t = (TextView) v.findViewById(R.id.textView1);
            group = (RadioGroup) v.findViewById(R.id.group_me);
            answer0 = (RadioButton) v.findViewById(R.id.answer0);
            answer1 = (RadioButton) v.findViewById(R.id.answer1);
            answer2 = (RadioButton) v.findViewById(R.id.answer2);
            answer3 = (RadioButton) v.findViewById(R.id.answer3);
            answer4 = (RadioButton) v.findViewById(R.id.answer4);
        }

    }

}
