package cn.nj.www.my_module.bean.index;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class UploadFileResponse extends BaseResponse
{
    /**
     * url : /file/user_15251813022/20dc468a-3a39-4296-b610-9b2794a9a628.jpg
     * urlList : ["/file/user_15251813022/20dc468a-3a39-4296-b610-9b2794a9a628.jpg"]
     */

    private String url;

    private List<String> urlList;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public List<String> getUrlList()
    {
        return urlList;
    }

    public void setUrlList(List<String> urlList)
    {
        this.urlList = urlList;
    }
}
