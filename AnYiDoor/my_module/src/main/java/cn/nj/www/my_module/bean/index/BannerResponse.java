package cn.nj.www.my_module.bean.index;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class BannerResponse extends BaseResponse
{

    private int cardCount;

    private int trainingCount;

    private int examCount;


    private List<BannerListBean> bannerList;


    public int getCardCount()
    {
        return cardCount;
    }

    public void setCardCount(int cardCount)
    {
        this.cardCount = cardCount;
    }

    public int getTrainingCount()
    {
        return trainingCount;
    }

    public void setTrainingCount(int trainingCount)
    {
        this.trainingCount = trainingCount;
    }

    public int getExamCount()
    {
        return examCount;
    }

    public void setExamCount(int examCount)
    {
        this.examCount = examCount;
    }

    public List<BannerListBean> getBannerList()
    {
        return bannerList;
    }

    public void setBannerList(List<BannerListBean> bannerList)
    {
        this.bannerList = bannerList;
    }

    public static class BannerListBean
    {
        private String recordID;

        private String title;

        private String cover;

        private int type;

        private String link;

        private String content;

        private int status;

        private String createTime;

        private String requestUrl;

        public String getRecordID()
        {
            return recordID;
        }

        public void setRecordID(String recordID)
        {
            this.recordID = recordID;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getCover()
        {
            return cover;
        }

        public void setCover(String cover)
        {
            this.cover = cover;
        }

        public int getType()
        {
            return type;
        }

        public void setType(int type)
        {
            this.type = type;
        }

        public String getLink()
        {
            return link;
        }

        public void setLink(String link)
        {
            this.link = link;
        }

        public String getContent()
        {
            return content;
        }

        public void setContent(String content)
        {
            this.content = content;
        }

        public int getStatus()
        {
            return status;
        }

        public void setStatus(int status)
        {
            this.status = status;
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
