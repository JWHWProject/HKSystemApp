package cn.nj.www.my_module.bean.index;


import cn.nj.www.my_module.bean.BaseResponse;

public class LoginResponse extends BaseResponse
{


    /**
     * loginName : root
     * userId : 1
     * userName : 超级用户
     * password : 4QrcOUm6Wau+VuBX8g+IPg==
     * mobile :
     * userRole : 超级管理员;
     * type : 1
     * captcha : null
     * newPassword : null
     * confirmPassword : null
     * permInstanceDtos : [{"dataId":1893,"name":"手机端超标记录","menuText":"超标记录","url":""},{"dataId":1903,"name":"手机端视频","menuText":"视频","url":""},{"dataId":1888,"name":"手机端烟气","menuText":"烟气","url":""},{"dataId":1883,"name":"手机端二噁英","menuText":"二噁英","url":""},{"dataId":1898,"name":"手机端监管考核","menuText":"监管考核","url":""}]
     */

    private UserBean data;

    public UserBean getData()
    {
        return data;
    }

    public void setData(UserBean data)
    {
        this.data = data;
    }



}
