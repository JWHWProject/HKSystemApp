package cn.nj.www.my_module.bean.index;

import java.util.List;

/**
 * Created by huqing on 2017/7/18.
 */

public class TrainBean
{
    private String typeName;

    public TrainBean(String typeName, List<TrainBeanDetail> trainBeanDetailList)
    {
        this.typeName = typeName;
        this.trainBeanDetailList = trainBeanDetailList;
    }

    private List<TrainBeanDetail> trainBeanDetailList;

    public List<TrainBeanDetail> getTrainBeanDetailList()
    {
        return trainBeanDetailList;
    }

    public void setTrainBeanDetailList(List<TrainBeanDetail> trainBeanDetailList)
    {
        this.trainBeanDetailList = trainBeanDetailList;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public static class TrainBeanDetail
    {
        @Override
        public String toString()
        {
            return "TrainBeanDetail{" +
                    "userID='" + userID + '\'' +
                    ", id='" + id + '\'' +
                    ", nickName='" + nickName + '\'' +
                    '}';
        }

        private String id;

        private String companyID;

        private String companyName;

        private String trainingName;

        private String companyList;

        private String nickName;

        private int type;

        private String userID;

        public String getFileType()
        {
            return fileType;
        }

        public void setFileType(String fileType)
        {
            this.fileType = fileType;
        }

        //1-圖片；2-視頻
        private String fileType;

        private String passScore;

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

        public String getUserID()
        {
            return userID;
        }

        public void setUserID(String userID)
        {
            this.userID = userID;
        }

        public String getPassScore()
        {
            return passScore;
        }

        public void setPassScore(String passScore)
        {
            this.passScore = passScore;
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
