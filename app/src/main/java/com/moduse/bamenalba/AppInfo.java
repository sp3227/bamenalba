package com.moduse.bamenalba;

/**
 * Created by sejung on 2017-03-27.
 */

public class AppInfo
{
    //------------------------------------ 앱 인증------------------------------------------------------//
    private String AppVer = "";                 //앱 버전
    private String CertificationKey = "";      //앱 키


    //-----------------------------------앱 주소 URL---------------------------------------------------//
    private String Bamenalba_Domain = "";       //서버 통합 도메인







    //---------------------------- 로그인시 정보--------------------------------------------------------//

    // 식별 아이디 (디바이스 ID 체크 & 다른거??상의)
    public static String MY_LOGINID;


    //MY GCM
    public static String MY_PUSHID;   // 푸시 아이디
    public static String MY_TYPE;     // 계정 타입 (구직자, 업체)

    // 내 위치 정보(로그인시 한번만 기록됨)
    public static  boolean GPS_state = false;   // GPS 상태
    public static double My_Latitude;    //경도
    public static double My_Longitude;   //위도




    //---------------------------- 설정(푸시 ON/OFF)--------------------------------------------------------//
    // 내 푸시 상태 (ON/OFF)
    public static boolean Push_state = true;


    //---------------------------- GETER--------------------------------------------------------//






}
