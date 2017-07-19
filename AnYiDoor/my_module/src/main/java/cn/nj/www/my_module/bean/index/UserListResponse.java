package cn.nj.www.my_module.bean.index;


import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

public class UserListResponse extends BaseResponse
{

    private List<UserListBean> userList;

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean {
        private String userID;

        private String userName;

        private String wxOpenID;

        private String trainingWXOpenID;

        private String smartDeivceWXOpenID;

        private String password;

        private String accountType;

        private int userType;

        private String companyID;

        private String department;

        private String companyList;

        private String companyName;

        private int admin;

        private String jobNumber;

        private int webAccess;

        private int reviewMode;

        private String checkType;

        private String nickName;

        private int gender;

        private String position;

        private String safePosition;

        private String workTitle;

        private String safeQualificationType;

        private int safeEngineer;

        private String safeEngineerNo;

        private String birthday;

        private String phone;

        private String telephone;

        private String idCard;

        private String portrait;

        private String maritalStatus;

        private String politicalOutlook;

        private String specialty;

        private String entryTime;

        private String departureTime;

        private String isSpecialPersonnel;

        private String specialType;

        private String specialPermitNumber;

        private String isFulltimeOfficer;

        private String isParttimeOfficer;

        private String education;

        private String major;

        private String device;

        private String email;

        private String graduationTime;

        private String homeAddress;

        private String graduationCertFile;

        private String idCardFile;

        private String idCardFileB;

        private String qualificationFile;

        private String qualification2File;

        private String qualification3File;

        private String hygieneQualificationFile;

        private String hygieneQualification2File;

        private String hygieneQualification3File;

        private String attachment;

        private int onTheJob;

        private int status;

        private int delFlag;

        private String lastLoginTime;

        private String createTime;

        private String portraitRequestUrl;

        private String graduationCertFileUrl;

        private String idCardFileUrl;

        private String idCardFileBUrl;

        private String qualificationFileUrl;

        private String qualification2FileUrl;

        private String qualification3FileUrl;

        private String attachmentUrl;

        private String hygieneQualificationFileUrl;

        private String hygieneQualification2FileUrl;

        private String hygieneQualification3FileUrl;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getWxOpenID() {
            return wxOpenID;
        }

        public void setWxOpenID(String wxOpenID) {
            this.wxOpenID = wxOpenID;
        }

        public String getTrainingWXOpenID() {
            return trainingWXOpenID;
        }

        public void setTrainingWXOpenID(String trainingWXOpenID) {
            this.trainingWXOpenID = trainingWXOpenID;
        }

        public String getSmartDeivceWXOpenID() {
            return smartDeivceWXOpenID;
        }

        public void setSmartDeivceWXOpenID(String smartDeivceWXOpenID) {
            this.smartDeivceWXOpenID = smartDeivceWXOpenID;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getCompanyID() {
            return companyID;
        }

        public void setCompanyID(String companyID) {
            this.companyID = companyID;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getCompanyList() {
            return companyList;
        }

        public void setCompanyList(String companyList) {
            this.companyList = companyList;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getAdmin() {
            return admin;
        }

        public void setAdmin(int admin) {
            this.admin = admin;
        }

        public String getJobNumber() {
            return jobNumber;
        }

        public void setJobNumber(String jobNumber) {
            this.jobNumber = jobNumber;
        }

        public int getWebAccess() {
            return webAccess;
        }

        public void setWebAccess(int webAccess) {
            this.webAccess = webAccess;
        }

        public int getReviewMode() {
            return reviewMode;
        }

        public void setReviewMode(int reviewMode) {
            this.reviewMode = reviewMode;
        }

        public String getCheckType() {
            return checkType;
        }

        public void setCheckType(String checkType) {
            this.checkType = checkType;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getSafePosition() {
            return safePosition;
        }

        public void setSafePosition(String safePosition) {
            this.safePosition = safePosition;
        }

        public String getWorkTitle() {
            return workTitle;
        }

        public void setWorkTitle(String workTitle) {
            this.workTitle = workTitle;
        }

        public String getSafeQualificationType() {
            return safeQualificationType;
        }

        public void setSafeQualificationType(String safeQualificationType) {
            this.safeQualificationType = safeQualificationType;
        }

        public int getSafeEngineer() {
            return safeEngineer;
        }

        public void setSafeEngineer(int safeEngineer) {
            this.safeEngineer = safeEngineer;
        }

        public String getSafeEngineerNo() {
            return safeEngineerNo;
        }

        public void setSafeEngineerNo(String safeEngineerNo) {
            this.safeEngineerNo = safeEngineerNo;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getPoliticalOutlook() {
            return politicalOutlook;
        }

        public void setPoliticalOutlook(String politicalOutlook) {
            this.politicalOutlook = politicalOutlook;
        }

        public String getSpecialty() {
            return specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public String getEntryTime() {
            return entryTime;
        }

        public void setEntryTime(String entryTime) {
            this.entryTime = entryTime;
        }

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public String getIsSpecialPersonnel() {
            return isSpecialPersonnel;
        }

        public void setIsSpecialPersonnel(String isSpecialPersonnel) {
            this.isSpecialPersonnel = isSpecialPersonnel;
        }

        public String getSpecialType() {
            return specialType;
        }

        public void setSpecialType(String specialType) {
            this.specialType = specialType;
        }

        public String getSpecialPermitNumber() {
            return specialPermitNumber;
        }

        public void setSpecialPermitNumber(String specialPermitNumber) {
            this.specialPermitNumber = specialPermitNumber;
        }

        public String getIsFulltimeOfficer() {
            return isFulltimeOfficer;
        }

        public void setIsFulltimeOfficer(String isFulltimeOfficer) {
            this.isFulltimeOfficer = isFulltimeOfficer;
        }

        public String getIsParttimeOfficer() {
            return isParttimeOfficer;
        }

        public void setIsParttimeOfficer(String isParttimeOfficer) {
            this.isParttimeOfficer = isParttimeOfficer;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGraduationTime() {
            return graduationTime;
        }

        public void setGraduationTime(String graduationTime) {
            this.graduationTime = graduationTime;
        }

        public String getHomeAddress() {
            return homeAddress;
        }

        public void setHomeAddress(String homeAddress) {
            this.homeAddress = homeAddress;
        }

        public String getGraduationCertFile() {
            return graduationCertFile;
        }

        public void setGraduationCertFile(String graduationCertFile) {
            this.graduationCertFile = graduationCertFile;
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

        public String getQualificationFile() {
            return qualificationFile;
        }

        public void setQualificationFile(String qualificationFile) {
            this.qualificationFile = qualificationFile;
        }

        public String getQualification2File() {
            return qualification2File;
        }

        public void setQualification2File(String qualification2File) {
            this.qualification2File = qualification2File;
        }

        public String getQualification3File() {
            return qualification3File;
        }

        public void setQualification3File(String qualification3File) {
            this.qualification3File = qualification3File;
        }

        public String getHygieneQualificationFile() {
            return hygieneQualificationFile;
        }

        public void setHygieneQualificationFile(String hygieneQualificationFile) {
            this.hygieneQualificationFile = hygieneQualificationFile;
        }

        public String getHygieneQualification2File() {
            return hygieneQualification2File;
        }

        public void setHygieneQualification2File(String hygieneQualification2File) {
            this.hygieneQualification2File = hygieneQualification2File;
        }

        public String getHygieneQualification3File() {
            return hygieneQualification3File;
        }

        public void setHygieneQualification3File(String hygieneQualification3File) {
            this.hygieneQualification3File = hygieneQualification3File;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public int getOnTheJob() {
            return onTheJob;
        }

        public void setOnTheJob(int onTheJob) {
            this.onTheJob = onTheJob;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPortraitRequestUrl() {
            return portraitRequestUrl;
        }

        public void setPortraitRequestUrl(String portraitRequestUrl) {
            this.portraitRequestUrl = portraitRequestUrl;
        }

        public String getGraduationCertFileUrl() {
            return graduationCertFileUrl;
        }

        public void setGraduationCertFileUrl(String graduationCertFileUrl) {
            this.graduationCertFileUrl = graduationCertFileUrl;
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

        public String getQualificationFileUrl() {
            return qualificationFileUrl;
        }

        public void setQualificationFileUrl(String qualificationFileUrl) {
            this.qualificationFileUrl = qualificationFileUrl;
        }

        public String getQualification2FileUrl() {
            return qualification2FileUrl;
        }

        public void setQualification2FileUrl(String qualification2FileUrl) {
            this.qualification2FileUrl = qualification2FileUrl;
        }

        public String getQualification3FileUrl() {
            return qualification3FileUrl;
        }

        public void setQualification3FileUrl(String qualification3FileUrl) {
            this.qualification3FileUrl = qualification3FileUrl;
        }

        public String getAttachmentUrl() {
            return attachmentUrl;
        }

        public void setAttachmentUrl(String attachmentUrl) {
            this.attachmentUrl = attachmentUrl;
        }

        public String getHygieneQualificationFileUrl() {
            return hygieneQualificationFileUrl;
        }

        public void setHygieneQualificationFileUrl(String hygieneQualificationFileUrl) {
            this.hygieneQualificationFileUrl = hygieneQualificationFileUrl;
        }

        public String getHygieneQualification2FileUrl() {
            return hygieneQualification2FileUrl;
        }

        public void setHygieneQualification2FileUrl(String hygieneQualification2FileUrl) {
            this.hygieneQualification2FileUrl = hygieneQualification2FileUrl;
        }

        public String getHygieneQualification3FileUrl() {
            return hygieneQualification3FileUrl;
        }

        public void setHygieneQualification3FileUrl(String hygieneQualification3FileUrl) {
            this.hygieneQualification3FileUrl = hygieneQualification3FileUrl;
        }
    }
}
