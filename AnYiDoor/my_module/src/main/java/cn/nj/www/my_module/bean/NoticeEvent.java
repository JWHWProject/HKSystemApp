package cn.nj.www.my_module.bean;

import android.widget.EditText;

public class NoticeEvent extends BaseResponse {
    private String tag;

    private int position;

    private String text;
    private String url1;
    private String url2;


    private EditText tempTV;

    public EditText getTempTV() {
        return tempTV;
    }

    public void setTempTV(EditText tempTV) {
        this.tempTV = tempTV;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
        this.setDuration(duration);
    }

    private long duration;
    public NoticeEvent(String tag) {
        this.setTag(tag);
    }
    public NoticeEvent(String tag,EditText tempTV) {
        this.setTag(tag);
        this.tempTV=tempTV;
    }
    public NoticeEvent(String tag,long duration) {
        this.setTag(tag);
    }

    public NoticeEvent(String tag, int position) {
        this.setTag(tag);
        this.setPosition(position);
    }

    public NoticeEvent(String tag, String text) {
        this.setTag(tag);
        this.setText(text);
    }

    public NoticeEvent(String tag, int position, String text) {
        this.setTag(tag);
        this.setPosition(position);
        this.setText(text);
    }

    public NoticeEvent(String tag, String url1, String url2) {
        this.setTag(tag);
        this.url1 = url1;
        this.url2 = url2;
    }
    public NoticeEvent(String tag,int position, String url1, String url2) {
        this.setTag(tag);
        this.position=position;
        this.url1 = url1;
        this.url2 = url2;
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

    public int getPosition() {
        return position;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
