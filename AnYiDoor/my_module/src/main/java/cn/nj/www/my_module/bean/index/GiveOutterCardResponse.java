package cn.nj.www.my_module.bean.index;


import cn.nj.www.my_module.bean.BaseResponse;

public class GiveOutterCardResponse extends BaseResponse
{


    /**
     * trainingID :
     */

    private String trainingID;
    private String outsidersID;

    public String getOutsidersID() {
        return outsidersID;
    }

    public void setOutsidersID(String outsidersID) {
        this.outsidersID = outsidersID;
    }

    public String getTrainingID()
    {
        return trainingID;
    }

    public void setTrainingID(String trainingID)
    {
        this.trainingID = trainingID;
    }
}
