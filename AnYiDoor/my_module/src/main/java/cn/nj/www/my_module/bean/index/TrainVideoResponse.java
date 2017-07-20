package cn.nj.www.my_module.bean.index;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class TrainVideoResponse extends BaseResponse
{


    /**
     * id : 3
     * companyID : 1640
     * companyName : 杨舜软件测试有限公司
     * trainingName : 使用培训测试
     * companyList :
     * nickName : 系统管理员
     * type : 1
     * outsidersType :
     * userID : 1
     * fileType : 2
     * videoUrl : /file/20/screenContent/ad8230d2-39e8-4926-ad6e-e98a109ff4f1.mp4
     * passScore :
     * status :
     * delFlag :
     * createTime : 2017-07-18
     */

    private TrainingBean training;

    /**
     * training : {"id":"3","companyID":"1640","companyName":"杨舜软件测试有限公司","trainingName":"使用培训测试","companyList":"","nickName":"系统管理员","type":1,"outsidersType":"","userID":"1","fileType":2,"videoUrl":"/file/20/screenContent/ad8230d2-39e8-4926-ad6e-e98a109ff4f1.mp4","passScore":"","status":"","delFlag":"","createTime":"2017-07-18"}
     * videoUrl : http://www.12365aq.cn/f/file/20/screenContent/ad8230d2-39e8-4926-ad6e-e98a109ff4f1.mp4
     * picUrlList : []
     */

    private String videoUrl;

    private List<?> picUrlList;

    public TrainingBean getTraining()
    {
        return training;
    }

    public void setTraining(TrainingBean training)
    {
        this.training = training;
    }

    public String getVideoUrl()
    {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl)
    {
        this.videoUrl = videoUrl;
    }

    public List<?> getPicUrlList()
    {
        return picUrlList;
    }

    public void setPicUrlList(List<?> picUrlList)
    {
        this.picUrlList = picUrlList;
    }

    public static class TrainingBean
    {
        private String id;

        private String companyID;

        private String companyName;

        private String trainingName;

        private String companyList;

        private String nickName;

        private int type;

        private String outsidersType;

        private String userID;

        private int fileType;

        private String videoUrl;

        private String passScore;

        private String status;

        private String delFlag;

        private String createTime;

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public String getCompanyID()
        {
            return companyID;
        }

        public void setCompanyID(String companyID)
        {
            this.companyID = companyID;
        }

        public String getCompanyName()
        {
            return companyName;
        }

        public void setCompanyName(String companyName)
        {
            this.companyName = companyName;
        }

        public String getTrainingName()
        {
            return trainingName;
        }

        public void setTrainingName(String trainingName)
        {
            this.trainingName = trainingName;
        }

        public String getCompanyList()
        {
            return companyList;
        }

        public void setCompanyList(String companyList)
        {
            this.companyList = companyList;
        }

        public String getNickName()
        {
            return nickName;
        }

        public void setNickName(String nickName)
        {
            this.nickName = nickName;
        }

        public int getType()
        {
            return type;
        }

        public void setType(int type)
        {
            this.type = type;
        }

        public String getOutsidersType()
        {
            return outsidersType;
        }

        public void setOutsidersType(String outsidersType)
        {
            this.outsidersType = outsidersType;
        }

        public String getUserID()
        {
            return userID;
        }

        public void setUserID(String userID)
        {
            this.userID = userID;
        }

        public int getFileType()
        {
            return fileType;
        }

        public void setFileType(int fileType)
        {
            this.fileType = fileType;
        }

        public String getVideoUrl()
        {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl)
        {
            this.videoUrl = videoUrl;
        }

        public String getPassScore()
        {
            return passScore;
        }

        public void setPassScore(String passScore)
        {
            this.passScore = passScore;
        }

        public String getStatus()
        {
            return status;
        }

        public void setStatus(String status)
        {
            this.status = status;
        }

        public String getDelFlag()
        {
            return delFlag;
        }

        public void setDelFlag(String delFlag)
        {
            this.delFlag = delFlag;
        }

        public String getCreateTime()
        {
            return createTime;
        }

        public void setCreateTime(String createTime)
        {
            this.createTime = createTime;
        }
    }
}
