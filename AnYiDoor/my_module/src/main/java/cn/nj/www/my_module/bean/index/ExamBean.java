package cn.nj.www.my_module.bean.index;

import java.util.List;

/**
 * Created by huqing on 2017/7/19.
 */

public class ExamBean
{
    private String typeName;

    private List<ExamDetailBean> examDetailBeanList;

    public ExamBean(String typeName, List<ExamDetailBean> examDetailBeanList)
    {
        this.typeName = typeName;
        this.examDetailBeanList = examDetailBeanList;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public List<ExamDetailBean> getExamDetailBeanList()
    {
        return examDetailBeanList;
    }

    public void setExamDetailBeanList(List<ExamDetailBean> examDetailBeanList)
    {
        this.examDetailBeanList = examDetailBeanList;
    }

    public static class ExamDetailBean
    {
        public int current;

        private String id;

        private String companyID;

        private String trainingID;

        private int type;

        private String question;

        private int mark;

        private String result;

        private String options;

        private String createTime;

        public int getCurrent()
        {
            return current;
        }

        public void setCurrent(int current)
        {
            this.current = current;
        }

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

        public String getTrainingID()
        {
            return trainingID;
        }

        public void setTrainingID(String trainingID)
        {
            this.trainingID = trainingID;
        }

        public int getType()
        {
            return type;
        }

        public void setType(int type)
        {
            this.type = type;
        }

        public String getQuestion()
        {
            return question;
        }

        public void setQuestion(String question)
        {
            this.question = question;
        }

        public int getMark()
        {
            return mark;
        }

        public void setMark(int mark)
        {
            this.mark = mark;
        }

        public String getResult()
        {
            return result;
        }

        public void setResult(String result)
        {
            this.result = result;
        }

        public String getOptions()
        {
            return options;
        }

        public void setOptions(String options)
        {
            this.options = options;
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
