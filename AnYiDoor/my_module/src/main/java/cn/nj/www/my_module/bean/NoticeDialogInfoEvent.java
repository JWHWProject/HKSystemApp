package cn.nj.www.my_module.bean;

import android.app.Dialog;
import android.widget.TextView;



public class NoticeDialogInfoEvent extends BaseResponse
{
    private String tag;

    private String text;

    private Dialog mDialog;


    private TextView tvComfirm;
    private TextView tvCancel;

    public NoticeDialogInfoEvent(String tag, String text, Dialog mDialog) {
        this.setTag(tag);
        this.setText(text);
        setmDialog(mDialog);
    }


    public Dialog getmDialog() {
        return mDialog;
    }

    public void setmDialog(Dialog mDialog) {
        this.mDialog = mDialog;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
