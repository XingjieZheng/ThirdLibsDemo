package com.xj;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 登录的初始信息
 */
public class AccountLoginBean implements Serializable {
    private Data data;

    /**
     * 用户id
     */
    private String accountName;
    /**
     * 登录信息-是否记录密码
     */
    private boolean isRememberPassword;

    /**
     * 登录信息-本地账户密码
     */
    private String accountPwd;


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public boolean isRememberPassword() {
        return isRememberPassword;
    }

    public void setRememberPassword(boolean isRememberPassword) {
        this.isRememberPassword = isRememberPassword;
    }

    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }

    public static class Data implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 用户性别
         */
        private int gender;
        /**
         * cookie
         */
        private HashMap<String, String> cookieMap;
        /**
         * 用户唯一标示
         */
        private String userId;


        private boolean isLogin = false;
        /**
         * 分享文案
         */
        private String shareContent;
        /**
         * 分享链接
         */
        private String shareUrl;
        /**
         * 分享标题
         */
        private String shareTitle;
        /**
         * give & take标题
         */
        private String giveTitle;

        private String nickName;

        private String avatar;
        private String head;
        private int coffeeCount;
        /**
         * 1 用户不存在；2 密码错误
         */
        private int state;

        private String green;
        private int coffeeSign;
        private String lastestVersion;
        private int dayCount;//连续登录参数
        /**
         * 0:未审核,1:审核通过,2:审核不通过
         */
        private int headStatus;
        /**
         * 0表示不显示，1表示显示
         */
        private int isShowShare = 1;

        /**
         * 0表示不显示，1表示显示
         */
        @SerializedName("open")
        private int isShowFellowShip = 0;
        /**
         * 0表示没有进去过，1表示进去过
         */
        @SerializedName("seen")
        private int isEnterFellowShip = 1;

        private int giveCoffee = 0;

        private int successInviteCoffee = 0;
        // 工牌状态 0等待审核；1审核通过; 2审核不通过
        private int cardState = -1;
        //邀请状态；-1未邀请；1成功；0失败
        private int inviteState = -1;
        private int photoCount;

        public int getInviteState() {
            return inviteState;
        }

        public int getCardState() {
            return cardState;
        }


        public int getPhotoCount() {
            return photoCount;
        }

        public String getHead() {
            return head;
        }

        public int getHeadStatus() {
            return headStatus;
        }

        public int getSuccessInviteCoffee() {
            return successInviteCoffee;
        }

        public int getGiveCoffee() {
            return giveCoffee;
        }

        public boolean getIsEnterFellowShip() {
            return isEnterFellowShip == 1;
        }

        public void setIsShowFellowShip(int enter) {
            this.isEnterFellowShip = enter;
        }

        public boolean getIsShowFellowShip() {
            return isShowFellowShip == 1;
        }

        public boolean getIsShowShare() {
            return isShowShare == 1;
        }

        public String getLastestVersion() {
            return lastestVersion;
        }

        public int getCoffeeSign() {
            return coffeeSign;
        }

        public String getGreen() {
            return green;
        }

        public void setGreen(String green) {
            this.green = green;
        }

        public int getState() {
            return state;
        }


        public int getCoffeeCount() {
            return coffeeCount;
        }

        public String getNickName() {
            return nickName;
        }

        public String getAvatar() {
            return avatar;
        }


        public String getShareContent() {
            return shareContent;
        }

        public void setShareContent(String shareContent) {
            this.shareContent = shareContent;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }


        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public boolean isLogin() {
            return isLogin;
        }

        public void setLogin(boolean isLogin) {
            this.isLogin = isLogin;
        }


        public HashMap<String, String> getCookieMap() {
            return cookieMap;
        }

        public void setCookieMap(HashMap<String, String> cookieMap) {
            this.cookieMap = cookieMap;
        }


        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }


        public String getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
        }

        public String getGiveTitle() {
            return giveTitle;
        }

        public void setGiveTitle(String giveTitle) {
            this.giveTitle = giveTitle;
        }


        public void setDayCount(int dayCount) {
            this.dayCount = dayCount;
        }

        public int getDayCount() {
            return dayCount;
        }

        @Override
        public String toString() {
            return "nickName:" + nickName + ", userId:" + userId;
        }
    }

}
