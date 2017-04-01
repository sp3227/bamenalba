package com.moduse.bamenalba;

/**
 * Created by HOMESJ on 2017-03-31.
 */

public class Data_Joperlist
{
    private String idx;              // 인덱스

    private String user_deviceID;  // 유저 식별아이디
    private String user_proimg;    // 유저 프로필 사진 URL
    private String user_sex;        // 유저 성별
    private String user_name;       // 유저 닉네임
    private String user_age;        // 유저 나이
    private String user_ment;       // 유저 한마디
    private String user_loaction;  // 유저 위치


    Data_Joperlist (String idx_,
                    String user_deviceID_,
                    String user_proimg_,
                    String user_sex_,
                    String user_name_,
                    String user_age_,
                    String user_ment_,
                    String user_loaction_)
    {
        idx= idx_;
        user_deviceID=user_deviceID_;
        user_proimg=user_proimg_;
        user_sex=user_sex_;
        user_name=user_name_;
        user_age=user_age_;
        user_ment=user_ment_;
        user_loaction=user_loaction_;
    }

    // GETer
    public String GET_idx()             { return idx; }
    public String GET_user_deviceID()   { return user_deviceID; }
    public String GET_user_proimg()     { return user_proimg;   }
    public String GET_user_sex()        { return user_sex;      }
    public String GET_user_name()       { return user_name;     }
    public String GET_user_age()        { return user_age;      }
    public String GET_user_ment()       { return user_ment;     }
    public String GET_user_loaction()   { return user_loaction; }


    //SETer
    public void SET_idx(String value)               { idx                = value; }
    public void SET_user_deviceID(String value)     { user_deviceID    = value; }
    public void SET_user_proimg(String value)       { user_proimg       = value; }
    public void SET_user_sex(String value)          { user_sex          = value; }
    public void SET_user_name(String value)         { user_name         = value; }
    public void SET_user_age(String value)          { user_age          = value; }
    public void SET_user_ment(String value)         { user_ment         = value; }
    public void SET_user_loaction(String value)     { user_loaction    = value; }

}
