package cn.nj.www.my_module.bean.index;


import cn.nj.www.my_module.bean.BaseResponse;

public class FinishTrainResponse extends BaseResponse
{


    /**
     * needExam : 1
     */

    private int needExam;

    public int getNeedExam()
    {
        return needExam;
    }

    public void setNeedExam(int needExam)
    {
        this.needExam = needExam;
    }
}
