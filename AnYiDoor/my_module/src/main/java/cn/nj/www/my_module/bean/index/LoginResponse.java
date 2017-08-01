package cn.nj.www.my_module.bean.index;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class LoginResponse extends BaseResponse
{


    private UserBean user;

    private List<?> departmentList;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<?> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<?> departmentList) {
        this.departmentList = departmentList;
    }

    public static class UserBean {
        private String userID;

        private String userName;

        private String companyID;

        private String department;

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }

        public void setUserName(String userName)
        {
            this.userName = userName;
        }

        public void setCompanyID(String companyID)
        {
            this.companyID = companyID;
        }

        public String getDepartment()
        {
            return department;
        }

        public void setDepartment(String department)
        {
            this.department = department;
        }

        private String password;

        private String companyName;



        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getUserName() {
            return userName;
        }


        public String getCompanyID() {
            return companyID;
        }


        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }



    }
}
