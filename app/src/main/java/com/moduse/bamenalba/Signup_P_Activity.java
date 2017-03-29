package com.moduse.bamenalba;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Vector;

public class Signup_P_Activity extends AppCompatActivity {


    String Sign_Type;

    AppInfo appInfo;

    ProgressDialog loading;

    phpdown StartSever;


    // 다이얼로그 상수
    public final int Dialog_Select_age          = 0;   // 나이
    public final int Dialog_Select_address1    = 1;   // 시도
    public final int Dialog_Select_address2    = 2;   // 군구
    public final int Dialog_Select_sector       = 3;  // 업종
    public final int Dialog_Select_ment         = 4;  //한마디

    // 선택되는 군구 (변화)
    String[] select_address2 = null;

    // 선택되는 멘트 (변화)
    String[] select_ment = null;


    // 레이아웃 선언
    /*------ 흐르는 공지 --------*/
    TextView text_notice;

    /*------ 닉네임 --------*/
    EditText edit_nickname;

    /*------ 나이 (스피너) --------*/
    LinearLayout btn_spenner_age;
    TextView text_age;

    /*------ 성별 (체크) --------*/
    LinearLayout btn_chcak_man;
    LinearLayout btn_chcak_girl;
    CheckBox checkBox_man;
    CheckBox checkBox_girl;

    /*------ 주소 (스피너) --------*/
    LinearLayout btn_spenner_address_1;
    TextView text_address_1;
    LinearLayout btn_spenner_address_2;
    TextView text_address_2;

    /*------ 업종 (스피너) --------*/
    String sector_title;
    LinearLayout btn_spenner_sector;
    TextView text_sector;

    /*------ 한마디 (스피너) --------*/
    LinearLayout btn_spenner_ment;
    TextView text_ment;

    /*------ 이용약관 동의 (체크, 텍스트 버튼) --------*/
    CheckBox btn_chcak_tos1;
    CheckBox btn_chcak_tos2;
    TextView btn_tos1;
    TextView btn_tos2;


    // 회원양식 DATA
    String DATA_deviceID;
    String DATA_pushID;

    String DATA_latitude;
    String DATA_longitude;

    String DATA_nickname = null;
    String DATA_age = null;
    String DATA_sex = null;
    String DATA_address_1 = null;
    String DATA_address_2 = null;
    String DATA_sector = null;
    String DATA_ment = null;
    String DATA_build;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        loading = new ProgressDialog(this);
        SetmsgShow("잠시만 기다려주세요.");

        appInfo = new AppInfo();


        Intent intent = getIntent();

        Sign_Type = intent.getStringExtra("signup_type");

        if(Sign_Type.toString().equals("joper"))
        {
            setContentView(R.layout.signup_jophunter_activity);
        }
        else if(Sign_Type.toString().equals("company"))
        {
            setContentView(R.layout.signup_company_activity);
        }

        init_signup(Sign_Type);

    }


    //-----------------------------------------------------레이아웃 연결 / 이벤트 리스너 설정-------------------------//

   public void init_signup(String value)
   {
       if(value.toString().equals("joper"))
       {
           //구직자 레이아웃 연결
           text_notice = (TextView) findViewById(R.id.signup_jober_text_notice);
           edit_nickname = (EditText) findViewById(R.id.signup_jober_edit_nickname);

           btn_spenner_age = (LinearLayout) findViewById(R.id.signup_jober_spenner_age);
           text_age = (TextView) findViewById(R.id.signup_jober_text_age);

           btn_chcak_man = (LinearLayout) findViewById(R.id.signup_jober_click_chack_man);
           btn_chcak_girl = (LinearLayout) findViewById(R.id.signup_jober_click_chack_girl);
           checkBox_man = (CheckBox) findViewById(R.id.signup_jober_chackbox_man);
           checkBox_girl = (CheckBox) findViewById(R.id.signup_jober_chackbox_girl);

           btn_spenner_address_1 = (LinearLayout) findViewById(R.id.signup_jober_spenner_address_1);
           btn_spenner_address_2 = (LinearLayout) findViewById(R.id.signup_jober_spenner_address_2);
           text_address_1 = (TextView) findViewById(R.id.signup_jober_text_address_1);
           text_address_2 = (TextView) findViewById(R.id.signup_jober_text_address_2);

           sector_title = "희망 업종을 선택해주세요";
           btn_spenner_sector = (LinearLayout) findViewById(R.id.signup_jober_spenner_sector);
           text_sector = (TextView) findViewById(R.id.signup_jober_text_sector);

           btn_spenner_ment = (LinearLayout) findViewById(R.id.signup_jober_spenner_ment);
           text_ment = (TextView) findViewById(R.id.signup_jober_text_ment);
           select_ment = getResources().getStringArray(R.array.ment);

           btn_chcak_tos1 = (CheckBox) findViewById(R.id.signup_jober_chackbox_tos_1);
           btn_chcak_tos2 = (CheckBox) findViewById(R.id.signup_jober_chackbox_tos_2);
           btn_tos1 = (TextView) findViewById(R.id.signup_jober_click_chack_tos_1);
           btn_tos2 = (TextView) findViewById(R.id.signup_jober_click_chack_tos_2);
       }
       else if(value.toString().equals("company"))
       {
           //업체 레이아웃 연결
           text_notice = (TextView) findViewById(R.id.signup_company_text_notice);
           edit_nickname = (EditText) findViewById(R.id.signup_company_edit_nickname);

           btn_spenner_age = (LinearLayout) findViewById(R.id.signup_company_spenner_age);
           text_age = (TextView) findViewById(R.id.signup_comapny_text_age);

           btn_chcak_man = (LinearLayout) findViewById(R.id.signup_company_click_chack_man);
           btn_chcak_girl = (LinearLayout) findViewById(R.id.signup_company_click_chack_girl);
           checkBox_man = (CheckBox) findViewById(R.id.signup_company_chackbox_man);
           checkBox_girl = (CheckBox) findViewById(R.id.signup_company_chackbox_girl);

           btn_spenner_address_1 = (LinearLayout) findViewById(R.id.signup_company_spenner_address_1);
           btn_spenner_address_2 = (LinearLayout) findViewById(R.id.signup_company_spenner_address_2);
           text_address_1 = (TextView) findViewById(R.id.signup_company_text_address_1);
           text_address_2 = (TextView) findViewById(R.id.signup_company_text_address_2);

           sector_title = "업종을 선택해주세요";
           btn_spenner_sector = (LinearLayout) findViewById(R.id.signup_company_spenner_sector);
           text_sector = (TextView) findViewById(R.id.signup_company_text_sector);

           btn_spenner_ment = (LinearLayout) findViewById(R.id.signup_company_spenner_ment);
           text_ment = (TextView) findViewById(R.id.signup_company_text_ment);
           select_ment = getResources().getStringArray(R.array.company_ment);

           btn_chcak_tos1 = (CheckBox) findViewById(R.id.signup_company_chackbox_tos_1);
           btn_chcak_tos2 = (CheckBox) findViewById(R.id.signup_company_chackbox_tos_2);
           btn_tos1 = (TextView) findViewById(R.id.signup_company_click_chack_tos_1);
           btn_tos2 = (TextView) findViewById(R.id.signup_company_click_chack_tos_2);
       }

       /// 이벤트 리스너 설정
       // 공지 흐르기
       text_notice.setSelected(true);


       //나이
       btn_spenner_age.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {
               showDialog(Dialog_Select_age);
           }
       });

       // 성별(남)
       btn_chcak_man.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               DATA_sex = null;
               checkBox_man.setChecked(true);
               checkBox_girl.setChecked(false);

               DATA_sex = "남자";
           }
       });

       // 성별(여)
       btn_chcak_girl.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               DATA_sex = null;
               checkBox_man.setChecked(false);
               checkBox_girl.setChecked(true);

               DATA_sex = "여자";
           }
       });

       //시도
       btn_spenner_address_1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               text_address_2.setText(null);
               DATA_address_2 = null;
               showDialog(Dialog_Select_address1);
           }
       });

       //군구
       btn_spenner_address_2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               if(select_address2 != null)
               {
                   showDialog(Dialog_Select_address2);
               }
               else
               {
                   Toast.makeText(getApplicationContext(),"시도를 먼저 선택해주세요.",Toast.LENGTH_SHORT).show();
               }
           }
       });

       //업종
       btn_spenner_sector.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               showDialog(Dialog_Select_sector);
           }
       });

       //업종
       btn_spenner_ment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               showDialog(Dialog_Select_ment);
           }
       });

       //동의 1번
       btn_tos1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               Intent intent = new Intent(getApplicationContext(), Agee_Activity.class);
               intent.putExtra("agee_type","agee1");

               startActivity(intent);
           }
       });

       //동의 2번
       btn_tos2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               Intent intent = new Intent(getApplicationContext(), Agee_Activity.class);
               intent.putExtra("agee_type","agee2");

               startActivity(intent);
           }
       });
   }








    //-----------------------------------------------------회원 가입 submit 버튼-------------------------//

    // 구직자
    public void click_jober_signup_submit(View v)
    {

        if(chackvalue())
        {
            Toast.makeText(getApplicationContext(),"가입 성공",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(),Main.class);
            startActivity(intent);
            finish();
        }
        else
        {

        }

    }

    // 업체
    public void click_company_signup_submit(View v)
    {
        if(chackvalue())
        {
            Toast.makeText(getApplicationContext(),"가입 성공",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),Main.class);
            startActivity(intent);
            finish();
        }
        else
        {

        }
    }



    //-----------------------------------------------------Value 체크-------------------------//

    public boolean chackvalue()
    {

        boolean chack = false;

        //닉네임
        DATA_nickname = edit_nickname.getText().toString();


        if(DATA_nickname == null || DATA_nickname.toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"닉네임을 입력해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(DATA_age == null)
        {
            Toast.makeText(getApplicationContext(),"나이를 선택해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(DATA_sex == null)
        {
            Toast.makeText(getApplicationContext(),"성별을 선택해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(DATA_address_1 == null || DATA_address_2 == null)
        {
            Toast.makeText(getApplicationContext(),"시도, 군구를 선택해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(DATA_sector == null)
        {
            Toast.makeText(getApplicationContext(),"업종을 선택해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(DATA_ment == null)
        {
            Toast.makeText(getApplicationContext(),"한마디를 선택해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(!btn_chcak_tos1.isChecked())
        {
            Toast.makeText(getApplicationContext(),"이용약관 동의가 필요합니다.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(!btn_chcak_tos2.isChecked())
        {
            Toast.makeText(getApplicationContext(),"위치정보약관 동의가 필요합니다.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else
        {
            Toast.makeText(getApplicationContext(),"닉네임 : "+ DATA_nickname +"\n나이 : "+ DATA_age + "\n성별 : "+ DATA_sex + "\n시도 : "+ DATA_address_1 + "\n군구 : "+ DATA_address_2
                    + "\n업종 : "+ DATA_sector + "\n한마디 : "+ DATA_ment,Toast.LENGTH_LONG).show();
            chack = true;
        }

        return chack;
    }



    //-----------------------------------------------------타입별 불러오기-------------------------//
    //
    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id) {
            case Dialog_Select_age:  // 나이
            {
                final CharSequence[] item = getResources().getStringArray(R.array.age);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("나이를 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                text_age.setText(item[i].toString() + "세");
                                DATA_age = item[i].toString();

                            }
                        });

                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }

            case Dialog_Select_address1: // 시도
            {
                final CharSequence[] item = getResources().getStringArray(R.array.area);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("시도를 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                text_address_1.setText(item[i].toString());
                                DATA_address_1 = item[i].toString();

                                select_address(item[i].toString());
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;

            }

            case Dialog_Select_address2: //군구
            {
                final CharSequence[] item = select_address2;
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("군구를 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                text_address_2.setText(item[i].toString());
                                DATA_address_2 = item[i].toString();

                            }
                        });

                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }

            case Dialog_Select_sector: //업종
            {
                final CharSequence[] item = getResources().getStringArray(R.array.sector);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle(sector_title) // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                text_sector.setText(item[i].toString());
                                DATA_sector = item[i].toString();

                            }
                        });

                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }
            case Dialog_Select_ment: //멘트
            {
                final CharSequence[] item = select_ment;
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("한마디를 선택해주세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                text_ment.setText(item[i].toString());
                                DATA_ment = item[i].toString();

                            }
                        });

                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }

        }
        return null;
    }

    @Override
    @Deprecated
    protected void onPrepareDialog(int id, Dialog dialog)
    {
        super.onPrepareDialog(id, dialog);
        //해당 다이얼로그는 매번 다시 부름.
        switch(id)
        {
            case Dialog_Select_address1:
                removeDialog(Dialog_Select_address2);

            //없에면 다시 새로 그림
        }
    }



    // 군구 선택 함수

    public void select_address(String value)
    {
        select_address2 = null;
        text_address_2.setText(null);
        DATA_address_2 = null;

        if(value.toString().equals("서울"))
        {
            select_address2 = getResources().getStringArray(R.array.seoul);
        }
        else if(value.toString().equals("경기"))
        {
            select_address2 = getResources().getStringArray(R.array.gyeonggi);
        }
        else if(value.toString().equals("부산"))
        {
            select_address2 = getResources().getStringArray(R.array.busan);
        }
        else if(value.toString().equals("인천"))
        {
            select_address2 = getResources().getStringArray(R.array.incheon);
        }
        else if(value.toString().equals("대구"))
        {
            select_address2 = getResources().getStringArray(R.array.daegu);
        }
        else if(value.toString().equals("광주"))
        {
            select_address2 = getResources().getStringArray(R.array.gwangju);
        }
        else if(value.toString().equals("경남"))
        {
            select_address2 = getResources().getStringArray(R.array.gyeongnam);
        }
        else if(value.toString().equals("충남"))
        {
            select_address2 = getResources().getStringArray(R.array.chungnam);
        }
        else if(value.toString().equals("대전"))
        {
            select_address2 = getResources().getStringArray(R.array.daejeon);
        }
        else if(value.toString().equals("충북"))
        {
            select_address2 = getResources().getStringArray(R.array.chungbuk);
        }
        else if(value.toString().equals("경북"))
        {
            select_address2 = getResources().getStringArray(R.array.gyeongsangbuk);
        }
        else if(value.toString().equals("울산"))
        {
            select_address2 = getResources().getStringArray(R.array.ulsan);
        }
        else if(value.toString().equals("세종"))
        {
            select_address2 = getResources().getStringArray(R.array.sejong);
        }
        else if(value.toString().equals("전북"))
        {
            select_address2 = getResources().getStringArray(R.array.jeonbuk);
        }
        else if(value.toString().equals("강원"))
        {
            select_address2 = getResources().getStringArray(R.array.gangwon);
        }
        else if(value.toString().equals("전남"))
        {
            select_address2 = getResources().getStringArray(R.array.jeonnam);
        }
        else if(value.toString().equals("제주"))
        {
            select_address2 = getResources().getStringArray(R.array.jeju);
        }
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







    //----------------------------------------------------- 프로그레스 설정------------------------//
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
