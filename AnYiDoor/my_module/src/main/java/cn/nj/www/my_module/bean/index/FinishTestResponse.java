package cn.nj.www.my_module.bean.index;


import cn.nj.www.my_module.bean.BaseResponse;

public class FinishTestResponse extends BaseResponse
{

    /**
     * url :
     * mark : 25
     * pass : 0
     */

    private String url;

    private int mark;

    private int pass; //pass是否通过，1-通过；0-不通过

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getMark()
    {
        return mark;
    }

    public void setMark(int mark)
    {
        this.mark = mark;
    }

    public String getPass()
    {
//        1-通过；0-不通过
        if (pass == 0)
        {
            return "不通过";
        }
        else
        {
            return "通过";
        }
    }

    public void setPass(int pass)
    {
        this.pass = pass;
    }
}
