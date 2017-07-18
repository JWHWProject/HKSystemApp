package cn.nj.www.my_module.bean.index;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class OuterTypeResponse extends BaseResponse
{

    private List<String> typeList;

    public List<String> getTypeList()
    {
        return typeList;
    }

    public void setTypeList(List<String> typeList)
    {
        this.typeList = typeList;
    }
}
