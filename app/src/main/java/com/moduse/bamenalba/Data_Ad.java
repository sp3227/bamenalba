package com.moduse.bamenalba;

/**
 * Created by sejung on 2017-03-28.
 */

public class Data_Ad
{
    private String idx;
    private String adidx;
    private String deviceID;
    private String ad_type;
    private String ad_name;
    private String ad_sector;
    private String ad_img;
    private String ad_sex;
    private String ad_pay;
    private String ad_payvalue;
    private String ad_adress1;
    private String ad_adress2;
    private String ad_location;
    private String user_sex;
    private String user_name;
    private String user_age;

    public Data_Ad(String idx_,String adidx_, String deviceID_, String ad_type_, String ad_name_, String ad_sector_, String ad_img_, String ad_sex_, String ad_pay_, String ad_payvalue_, String ad_adress1_, String ad_adress2_, String ad_location_
            , String user_sex_, String user_name_, String user_age_)
    {
        idx=idx_;
        adidx= adidx_;

        deviceID=deviceID_;

        ad_type=ad_type_;
        ad_name=ad_name_;
        ad_sector=ad_sector_;
        ad_img=ad_img_;
        ad_sex=ad_sex_;
        ad_pay=ad_pay_;
        ad_payvalue=ad_payvalue_;
        ad_adress1=ad_adress1_;
        ad_adress2=ad_adress2_;
        ad_location=ad_location_;

        user_sex=user_sex_;
        user_name=user_name_;
        user_age=user_age_;
    }


    // GETer
    public String GET_idx(){ return idx; }
    public String GET_adidx(){ return adidx; }
    public String GET_deviceID(){ return deviceID; }
    public String GET_ad_type(){ return ad_type; }
    public String GET_ad_name(){ return ad_name; }
    public String GET_ad_sector(){ return ad_sector; }
    public String GET_ad_img(){ return ad_img; }
    public String GET_ad_sex(){ return ad_sex; }
    public String GET_ad_pay(){ return ad_pay; }
    public String GET_ad_payvalue(){ return ad_payvalue; }
    public String GET_ad_adress1(){ return ad_adress1; }
    public String GET_ad_adress2(){ return ad_adress2; }
    public String GET_ad_location(){ return ad_location; }
    public String GET_user_sex(){ return user_sex; }
    public String GET_user_name(){ return user_name; }
    public String GET_user_age(){ return user_age; }


    //SETer
    public void SET_idx(String value){ idx = value; }
    public void SET_adidx(String value){ adidx = value; }
    public void SET_deviceID(String value){ deviceID = value; }
    public void SET_ad_type(String value){ ad_type = value; }
    public void SET_ad_name(String value){ ad_name = value; }
    public void SET_ad_sector(String value){ ad_sector = value; }
    public void SET_ad_img(String value){ ad_img = value; }
    public void SET_ad_sex(String value){ ad_sex = value; }
    public void SET_ad_pay(String value){ ad_pay = value; }
    public void SET_ad_payvalue(String value){ ad_payvalue = value; }
    public void SET_ad_adress1(String value){ ad_adress1 = value; }
    public void SET_ad_adress2(String value){ ad_adress2 = value; }
    public void SET_ad_location(String value){ ad_location = value; }
    public void SET_user_sex(String value){ user_sex = value; }
    public void SET_user_name(String value){ user_name = value; }
    public void SET_user_age(String value){ user_age = value; }

}
