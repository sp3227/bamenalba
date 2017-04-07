package com.moduse.bamenalba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Vector;

public class Intro_Activity extends AppCompatActivity {


    ProgressDialog loading;

    AppInfo appInfo;

    phpdown StartSever;

    //GPS
    private LocationManager locationManager;
    LocationListener locationListener;
    boolean isGPSEnabled;


    //GCM
    GoogleCloudMessaging gcm;
    String SENDER_ID;
    String token;


    // 유저 세팅 불러오기
    SharedPreferences setting;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        loading = new ProgressDialog(this);
        InitShow();
        SetmsgShow("밤엔알바에 접속중입니다.");

        appInfo = new AppInfo();

        // 유저 세팅값 불러오기 푸시 스위치
        setting = getSharedPreferences("setting", 0);  // 로컬 불러오기 선언
        if(setting.getBoolean("PUSH_STATE",true))
        {
            AppInfo.My_PUSH_STATE = true;
        }
        else
        {
            AppInfo.My_PUSH_STATE = false;
        }

        //GPS 가져오기
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  // LocationManager  객체 얻어오기
        //isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);   // 사용 체크 GPS1
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);   // 사용 체크 GPS2  (GPS1)은 정확하나 받아오기 실패 할 수 있음

        userGET_GPS(); // 위치 수신
        AppInfo.MY_LOGINID = Get_DeviceID();   // GET 디바이스 아이디
        Pushtoken();  // GET 푸시 아이디


        startmain();
    }



    //----------------------------------------------------디바이스 ID 가져오기-----------------------------------------------//
    public String Get_DeviceID()
    {
        // 디바이스 ID 검사
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String deviceid;

        if(manager.getDeviceId() != null)
        {
            deviceid = manager.getDeviceId();
        }
        else
        {
            deviceid = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        }

        return deviceid;

    }

    //----------------------------------------------------푸시 아이디 가져오기-----------------------------------------------//
    public void Pushtoken()
    {
        SENDER_ID = getString(R.string.gcm_defaultSenderId);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {

                    if(gcm == null)
                    {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }

                    token = gcm.register(SENDER_ID);

                    AppInfo.MY_PUSHID = gcm.register(SENDER_ID);
                    // Log.i("Pushtoken",token);
                } catch (IOException ex) {
                }
                return "";
            }

            @Override
            protected void onPostExecute(String msg)
            {
            }
        }.execute(null, null, null);

    }

    //----------------------------------------------------ㅇGPS 유저 위치 가져오기-----------------------------------------------//
    public void userGET_GPS()
    {
        if(!isGPSEnabled)
        {
            // GPS가 꺼져있을 시 앱이 수행할 작업 코드
            Toast.makeText(this,"GPS가 꺼져있습니다. 확인해주세요.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //status.setText("위도: "+ location.getLatitude() + "\n경도: " + location.getLongitude() + "\n고도: " + location.getAltitude());

                    AppInfo.My_Latitude = location.getLatitude();
                    AppInfo.My_Longitude = location.getLongitude();


                    //appInfo.Set_Latitude(location.getLatitude());
                    //appInfo.Set_Longitude(location.getLongitude());

                    // 위치 정보를 가져올 수 있는 메소드입니다.
                    // 위치 이동이나 시간 경과 등으로 인해 호출됩니다.
                    // 최신 위치는 location 파라메터가 가지고 있습니다.
                    //최신 위치를 가져오려면, location 파라메터를 이용하시면 됩니다.

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                } // 위치 공급자의 상태가 바뀔 때 호출 됩니다.

                @Override
                public void onProviderEnabled(String s) {
                } // 위치 공급자가 사용 가능해질(enabled) 때 호출 됩니다.

                @Override
                public void onProviderDisabled(String s) {
                } // 위치 공급자가 사용 불가능해질(disabled) 때 호출 됩니다.
            };

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 오차범위 있음
                    1000, // 통지사이의 최소 시간간격 (miliSecond)
                    1000, locationListener); // 통지사이의 최소 변경거리 (m)

            //locationManager.removeUpdates(locationListener);  // GPS 닫기
        }

    }


    //----------------------------------------------------메인 시작 딜레이 타이머-----------------------------------------------//
    public void startmain()
    {
        Handler hd = new Handler();
        hd.postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                StopShow();
                //accreditationToDatabase(appInfo.Get_AppVer(),appInfo.Get_CertificationKey(),appInfo.Get_CertificationURL());

                //----------임시 ----------------//
                Intent intent = new Intent(getApplicationContext(),Main.class);

                /*
                Log.i("get","MY_LOGINID : "+AppInfo.MY_LOGINID);
                Log.i("get","MY_PUSHID : "+AppInfo.MY_PUSHID);
                Log.i("get","My_Latitude : "+AppInfo.My_Latitude);
                Log.i("get","My_Latitude : "+AppInfo.My_Longitude);

                */

                startActivity(intent);
                finish();
            }
        }, 2500);
    }


    //-----------------------------------------------------통신 인자값 설정-------------------------//

    public void Signup_php()
    {
        StartShow();  // 다이얼로그 시작

        //post 인자값 전달
        Vector<NameValuePair> list = new Vector<NameValuePair>();

        //여기에 전달할 인자를 담는다. String으로 넣는것이 안전하다.
        list.add(new BasicNameValuePair("POST_테그", "값"));

        try
        {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
            String url = "접속 URL";  // url 설정
            HttpPost request = new HttpPost(url);
            request.setEntity(entity);

            StartSever = new phpdown();  // 쓰레드 생성
            StartSever.execute(request);
        }
        catch (Exception e)
        {
            // 서버에 연결할 수 없습니다 토스트 메세지 보내기
            Toast.makeText(this.getApplicationContext(), "서버접속이 불안정합니다. 인터넷 환경을 확인해주세요.", Toast.LENGTH_SHORT).show();
            Log.e("Exception Error", e.toString());
        }


    }



    //----------------------------------------------------- 통신 ----------------------------------//

    private class phpdown extends AsyncTask<HttpPost, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(HttpPost... urls) {
            String returnData = "";
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = new HttpResponse() {
                @Override
                public StatusLine getStatusLine() {
                    return null;
                }

                @Override
                public void setStatusLine(StatusLine statusLine) {
                }

                @Override
                public void setStatusLine(ProtocolVersion protocolVersion, int i) {
                }

                @Override
                public void setStatusLine(ProtocolVersion protocolVersion, int i, String s) {
                }

                @Override
                public void setStatusCode(int i) throws IllegalStateException {
                }

                @Override
                public void setReasonPhrase(String s) throws IllegalStateException {
                }

                @Override
                public HttpEntity getEntity() {
                    return null;
                }

                @Override
                public void setEntity(HttpEntity httpEntity) {
                }

                @Override
                public Locale getLocale() {
                    return null;
                }

                @Override
                public void setLocale(Locale locale) {
                }

                @Override
                public ProtocolVersion getProtocolVersion() {
                    return null;
                }

                @Override
                public boolean containsHeader(String s) {
                    return false;
                }

                @Override
                public Header[] getHeaders(String s) {
                    return new Header[0];
                }

                @Override
                public Header getFirstHeader(String s) {
                    return null;
                }

                @Override
                public Header getLastHeader(String s) {
                    return null;
                }

                @Override
                public Header[] getAllHeaders() {
                    return new Header[0];
                }

                @Override
                public void addHeader(Header header) {
                }

                @Override
                public void addHeader(String s, String s1) {
                }

                @Override
                public void setHeader(Header header) {
                }

                @Override
                public void setHeader(String s, String s1) {
                }

                @Override
                public void setHeaders(Header[] headers) {
                }

                @Override
                public void removeHeader(Header header) {
                }

                @Override
                public void removeHeaders(String s) {
                }

                @Override
                public HeaderIterator headerIterator() {
                    return null;
                }

                @Override
                public HeaderIterator headerIterator(String s) {
                    return null;
                }

                @Override
                public HttpParams getParams() {
                    return null;
                }

                @Override
                public void setParams(HttpParams httpParams) {
                }
            };


            try {
                response = httpclient.execute(urls[0]);
            } catch (Exception e) {
                // 서버에 연결할 수 없습니다 토스트 메세지 보내기
//                Toast.makeText((MainActivity) MainActivity.mContext, ((MainActivity) MainActivity.mContext).getResources().getText(R.string.server_connect_error), Toast.LENGTH_SHORT).show();
                Log.e("TalkPagePost Exception", e.toString());
            }

            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder builder = new StringBuilder();
                String str = "";

                while ((str = rd.readLine()) != null) {
                    builder.append(str);
                }

                returnData = builder.toString();
            } catch (Exception e) {
//                Toast.makeText((MainActivity) MainActivity.mContext, ((MainActivity) MainActivity.mContext).getResources().getText(R.string.server_connect_error), Toast.LENGTH_SHORT).show();
                Log.e("TalkPagePost Exception", e.toString());
            }

            return returnData;
        }

        @Override
        protected void onPostExecute(String str) {

            // 보기 좋은 형태로 변수에 대입
            String idx__;

            String point_idx__;
            String point_state__;

            String loginID__;
            String deviceID__;

            String latitude__;
            String longtitude__;

            String addtime__;
            String nickname__;

            if (str.toString().equals("CHARFALSE"))
            {


            }

            else
            {
                try {
                    JSONObject root = new JSONObject(str);

                    JSONArray ja = root.getJSONArray("result");

                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject jo = ja.getJSONObject(i);
                        idx__ = jo.getString("idx");
                        point_idx__ = jo.getString("point_idx");
                        point_state__ = jo.getString("point_state");
                        loginID__ = jo.getString("loginID");
                        deviceID__ = jo.getString("deviceID");
                        latitude__ = jo.getString("latitude");
                        longtitude__ = jo.getString("longtitude");
                        addtime__ = jo.getString("addtime");
                        nickname__ = jo.getString("nickname");

                        // listItem.add(new PointData(idx__, point_idx__, point_state__, loginID__, deviceID__, latitude__, longtitude__, addtime__, nickname__));
                    }

                } catch (JSONException e)
                {
                    Log.i("errer1",e.toString());
                    e.printStackTrace();
                }


            }
        }
    }







    // 프로그레스 설정
    public void InitShow()
    {
        loading.setProgress(ProgressDialog.STYLE_SPINNER);
        loading.setCanceledOnTouchOutside(false);
        loading.setMessage("정보를 불러오는 중입니다..");
    }
    public void SetmsgShow(String value)
    {
        loading.setMessage(value);
    }
    public void StartShow() {loading.show();}
    public void StopShow() {loading.dismiss();}
}
