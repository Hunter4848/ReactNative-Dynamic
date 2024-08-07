package com.dynamicreactnative2.model;

import com.google.gson.annotations.SerializedName;

public class UserDetail {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("user_name")
    private String username;

    @SerializedName("user_password")
    private String password;

    @SerializedName("access")
    private String access;

    @SerializedName("isActive")
    private int isActive;

    @SerializedName("otpCode")
    private int otpCode;

    @SerializedName("saldo")
    private int saldo;

    @SerializedName("userCode")
    private String userCode;

    // Constructor
    public UserDetail(int id, String name, String username, String password, String access, int isActive, int otpCode, int saldo, String userCode) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.access = access;
        this.isActive = isActive;
        this.otpCode = otpCode;
        this.saldo = saldo;
        this.userCode = userCode;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(int otpCode) {
        this.otpCode = otpCode;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}

