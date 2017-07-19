package cn.nj.www.my_module.bean.index;

/**
 * Created by huqing on 2017/7/19.
 */

public class OnlineTrainingAnswer
{
    public String getExamID()
    {
        return examID;
    }

    public void setExamID(String examID)
    {
        this.examID = examID;
    }

    public String getQuestionID()
    {
        return questionID;
    }

    public void setQuestionID(String questionID)
    {
        this.questionID = questionID;
    }

    public String getCreateTimeStr()
    {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr)
    {
        this.createTimeStr = createTimeStr;
    }

    private String examID;
    private String questionID;

    public String getUserAnswer()
    {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer)
    {
        this.userAnswer = userAnswer;
    }

    private String userAnswer;
    private String createTimeStr;

    public OnlineTrainingAnswer(  String questionID)
    {
        this.questionID = questionID;
    }

    @Override
    public String toString()
    {
        return "OnlineTrainingAnswer{" +
                "examID='" + examID + '\'' +
                ", questionID='" + questionID + '\'' +
                ", userAnswer='" + userAnswer + '\'' +
                ", createTimeStr='" + createTimeStr + '\'' +
                '}';
    }
}
