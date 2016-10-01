package james.com.mag1c_band.Model;

public class Profile {
    //下面是个人信息表
    //主要信息有 1.账号信息 2.昵称 3.性别 4.手机 5.邮箱 6.出生日期 7.状态
    private String username;
    private String nickname;
    private int year;
    private int month;
    private int day;
    private String telephone;
    private String sex;
    private String email;
    private String birthday;
    private String status;

    public Profile() {
    }//懒得写构造函数了

    public void setUsername(String mUsername) {
        this.username = mUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setBirthday(int mYear, int mMouth, int mDay) {
        this.year = mYear;
        this.month = mMouth;
        this.day = mDay;
    }

    public void setBirthday(String time) {
        birthday = time;
    }

    public String getBirthday() {
        return year + "," + month + "," + day;
    }

    public String getBirthday(int wholeStyle) {
        return birthday;
    }

    public void setSex(String mSex) {
        this.sex = mSex;
    }

    public String getSex() {
        return sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    //主要信息有 1.账号信息 2.昵称 3.性别 4.手机 5.邮箱 6.出生日期 7.状态
    public String toJson() {
        return "{username:" + username + ",nickname:" + nickname +
                ",birthday:" + getBirthday(1) + ",telephone:" + telephone +
                ",sex:" + sex + ",email:" + email + ",status:" + status + "}";
    }

}
