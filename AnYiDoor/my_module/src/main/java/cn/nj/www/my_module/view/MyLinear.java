package cn.nj.www.my_module.view;

import android.content.Context;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 * Created by huqing on 2017/7/17.
 */
public class MyLinear extends LinearLayout implements Checkable
{
    private boolean mChecked;

    public MyLinear(Context context) {
        super(context);
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(mChecked);
    }
}