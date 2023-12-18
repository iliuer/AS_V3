package com.example.client_internet;

import java.io.Serializable;



public class Identify_result implements Serializable {
    // 存储身份证的各信息，包括姓名、性别、民族、出生日期、地址、身份证号
    private String Name;
    private String Gender;
    private String Nation;
    private String Birthday;
    private String Address;
    private String Identify;
    private int errorcode;
    private String errormsg;
    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getSex() {
        return Gender;
    }

    public void setSex(String sex) {
        this.Gender = sex;
    }

    public String getNation() {
        return Nation;
    }

    public void setNation(String nation) {
        this.Nation = nation;
    }

    public String getBirth() {
        return Birthday;
    }

    public void setBirth(String birth) {
        this.Birthday = birth;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getId() {
        return Identify;
    }

    public void setId(String id) {
        this.Identify = id;
    }
}
