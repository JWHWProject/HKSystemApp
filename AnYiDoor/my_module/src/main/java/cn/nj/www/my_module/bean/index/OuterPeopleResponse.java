package cn.nj.www.my_module.bean.index;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class OuterPeopleResponse extends BaseResponse
{


    private List<OutsidersListBean> outsidersList;

    public List<OutsidersListBean> getOutsidersList() {
        return outsidersList;
    }

    public void setOutsidersList(List<OutsidersListBean> outsidersList) {
        this.outsidersList = outsidersList;
    }

    public static class OutsidersListBean {
        private String id;

        private String companyID;

        private String companyName;

        private String companyList;

        private String userName;

        private int gender;

        private String fromCompany;

        private String phone;

        private String idCard;

        private String signPicUrl;

        private String idCardFile;

        private String idCardFileB;

        private String cardNo;

        private String outsidersType;

        private String reason;

        private String receiveDepartment;

        private String receiver;

        private String userID;

        private int trainingStatus;

        private String trainingTime;

        private int examStatus;

        private String examTime;

        private int leaveStatus;

        private String leaveTime;

        private String createTime;

        private String idCardFileUrl;

        private String idCardFileBUrl;

        private String signPicRequestUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompanyID() {
            return companyID;
        }

        public void setCompanyID(String companyID) {
            this.companyID = companyID;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyList() {
            return companyList;
        }

        public void setCompanyList(String companyList) {
            this.companyList = companyList;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getFromCompany() {
            return fromCompany;
        }

        public void setFromCompany(String fromCompany) {
            this.fromCompany = fromCompany;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getSignPicUrl() {
            return signPicUrl;
        }

        public void setSignPicUrl(String signPicUrl) {
            this.signPicUrl = signPicUrl;
        }

        public String getIdCardFile() {
            return idCardFile;
        }

        public void setIdCardFile(String idCardFile) {
            this.idCardFile = idCardFile;
        }

        public String getIdCardFileB() {
            return idCardFileB;
        }

        public void setIdCardFileB(String idCardFileB) {
            this.idCardFileB = idCardFileB;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getOutsidersType() {
            return outsidersType;
        }

        public void setOutsidersType(String outsidersType) {
            this.outsidersType = outsidersType;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getReceiveDepartment() {
            return receiveDepartment;
        }

        public void setReceiveDepartment(String receiveDepartment) {
            this.receiveDepartment = receiveDepartment;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public int getTrainingStatus() {
            return trainingStatus;
        }

        public void setTrainingStatus(int trainingStatus) {
            this.trainingStatus = trainingStatus;
        }

        public String getTrainingTime() {
            return trainingTime;
        }

        public void setTrainingTime(String trainingTime) {
            this.trainingTime = trainingTime;
        }

        public int getExamStatus() {
            return examStatus;
        }

        public void setExamStatus(int examStatus) {
            this.examStatus = examStatus;
        }

        public String getExamTime() {
            return examTime;
        }

        public void setExamTime(String examTime) {
            this.examTime = examTime;
        }

        public int getLeaveStatus() {
            return leaveStatus;
        }

        public void setLeaveStatus(int leaveStatus) {
            this.leaveStatus = leaveStatus;
        }

        public String getLeaveTime() {
            return leaveTime;
        }

        public void setLeaveTime(String leaveTime) {
            this.leaveTime = leaveTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getIdCardFileUrl() {
            return idCardFileUrl;
        }

        public void setIdCardFileUrl(String idCardFileUrl) {
            this.idCardFileUrl = idCardFileUrl;
        }

        public String getIdCardFileBUrl() {
            return idCardFileBUrl;
        }

        public void setIdCardFileBUrl(String idCardFileBUrl) {
            this.idCardFileBUrl = idCardFileBUrl;
        }

        public String getSignPicRequestUrl() {
            return signPicRequestUrl;
        }

        public void setSignPicRequestUrl(String signPicRequestUrl) {
            this.signPicRequestUrl = signPicRequestUrl;
        }
    }
}
