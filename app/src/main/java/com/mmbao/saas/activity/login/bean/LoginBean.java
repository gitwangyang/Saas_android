package com.mmbao.saas.activity.login.bean;

import com.mmbao.saas.bean.BaseBean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 登录Bean
 * Description:com.mmbao.saas.bean.login类
 * Created by Administrator on 2018/1/22.
 * Maxim:There is no smoke without fire
 */
public class LoginBean extends BaseBean {

    private MemberBean member;//普通账号登录
    private PlatFormBean platForm;//三方平台登录

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public PlatFormBean getPlatForm() {
        return platForm;
    }

    public void setPlatForm(PlatFormBean platForm) {
        this.platForm = platForm;
    }

    public static class MemberBean {
        /**
         * memberId : 178553
         * loginName : saas_uehlcw0g
         * userName : saas_uehlcw0g
         * password : f3b6b7633f33883fca6adb63018fe099
         * type : 1
         * createDate : 2017-12-05 14:11:02
         * isUse : 1
         * isDelete : 0
         * phone : 18255503426
         * sourceType : 9
         * encryptType : 0
         * regType : 1
         * phoneType : 3
         * isOem : 0
         * vipLevel : 0
         * vipVal : 1
         */

        private BigDecimal memberId;
        private String loginName;
        private String userName;
        private String password;
        private String type;
        private String createDate;
        private String isUse;
        private String isDelete;
        private String phone;
        private String sourceType;
        private String encryptType;
        private String regType;
        private String phoneType;
        private String isOem;
        private String vipLevel;
        private String vipVal;

        public BigDecimal getMemberId() {
            return memberId;
        }

        public void setMemberId(BigDecimal memberId) {
            this.memberId = memberId;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getIsUse() {
            return isUse;
        }

        public void setIsUse(String isUse) {
            this.isUse = isUse;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSourceType() {
            return sourceType;
        }

        public void setSourceType(String sourceType) {
            this.sourceType = sourceType;
        }

        public String getEncryptType() {
            return encryptType;
        }

        public void setEncryptType(String encryptType) {
            this.encryptType = encryptType;
        }

        public String getRegType() {
            return regType;
        }

        public void setRegType(String regType) {
            this.regType = regType;
        }

        public String getPhoneType() {
            return phoneType;
        }

        public void setPhoneType(String phoneType) {
            this.phoneType = phoneType;
        }

        public String getIsOem() {
            return isOem;
        }

        public void setIsOem(String isOem) {
            this.isOem = isOem;
        }

        public String getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(String vipLevel) {
            this.vipLevel = vipLevel;
        }

        public String getVipVal() {
            return vipVal;
        }

        public void setVipVal(String vipVal) {
            this.vipVal = vipVal;
        }
    }

    public static class PlatFormBean {
        private String id;
        private Date createDate;
        private BigDecimal memberId;
        private String openId;
        private String nickName;
        private String sex;
        private String country;
        private String province;
        private String city;
        private String headImgUrl;
        private String unionId;
        private String password;
        private static final long serialVersionUID = 1L;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id == null ? null : id.trim();
        }

        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }

        public BigDecimal getMemberId() {
            return memberId;
        }

        public void setMemberId(BigDecimal memberId) {
            this.memberId = memberId;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId == null ? null : openId.trim();
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName == null ? null : nickName.trim();
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex == null ? null : sex.trim();
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country == null ? null : country.trim();
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province == null ? null : province.trim();
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city == null ? null : city.trim();
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl == null ? null : headImgUrl.trim();
        }

        public String getUnionId() {
            return unionId;
        }

        public void setUnionId(String unionId) {
            this.unionId = unionId == null ? null : unionId.trim();
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password == null ? null : password.trim();
        }
    }
}
