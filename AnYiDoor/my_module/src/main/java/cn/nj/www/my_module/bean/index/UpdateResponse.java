package cn.nj.www.my_module.bean.index;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class UpdateResponse extends BaseResponse
{

    private String serverTime;


    private AppVersionInfoBean appVersionInfo;

    private List<?> appInitInfoList;

    public String getServerTime()
    {
        return serverTime;
    }

    public void setServerTime(String serverTime)
    {
        this.serverTime = serverTime;
    }

    public AppVersionInfoBean getAppVersionInfo()
    {
        return appVersionInfo;
    }

    public void setAppVersionInfo(AppVersionInfoBean appVersionInfo)
    {
        this.appVersionInfo = appVersionInfo;
    }

    public List<?> getAppInitInfoList()
    {
        return appInitInfoList;
    }

    public void setAppInitInfoList(List<?> appInitInfoList)
    {
        this.appInitInfoList = appInitInfoList;
    }

    public static class AppVersionInfoBean
    {
        private String versionID;

        private int versionCode;

        private String versionCodeShow;

        private String updateDescription;

        private int updateType;

        private String fileUrl;

        private String createTime;

        private String requestUrl;

        public String getVersionID()
        {
            return versionID;
        }

        public void setVersionID(String versionID)
        {
            this.versionID = versionID;
        }

        public int getVersionCode()
        {
            return versionCode;
        }

        public void setVersionCode(int versionCode)
        {
            this.versionCode = versionCode;
        }

        public String getVersionCodeShow()
        {
            return versionCodeShow;
        }

        public void setVersionCodeShow(String versionCodeShow)
        {
            this.versionCodeShow = versionCodeShow;
        }

        public String getUpdateDescription()
        {
            return updateDescription;
        }

        public void setUpdateDescription(String updateDescription)
        {
            this.updateDescription = updateDescription;
        }

        public int getUpdateType()
        {
            return updateType;
        }

        public void setUpdateType(int updateType)
        {
            this.updateType = updateType;
        }

        public String getFileUrl()
        {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl)
        {
            this.fileUrl = fileUrl;
        }

        public String getCreateTime()
        {
            return createTime;
        }

        public void setCreateTime(String createTime)
        {
            this.createTime = createTime;
        }

        public String getRequestUrl()
        {
            return requestUrl;
        }

        public void setRequestUrl(String requestUrl)
        {
            this.requestUrl = requestUrl;
        }
    }
}
