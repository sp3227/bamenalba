package com.moduse.bamenalba;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by sejung on 2017-03-15.
 */

public class Main extends AppCompatActivity {

    // 프로그레스
    ProgressDialog loading;

    // 탭관련 부분
    LinearLayout Background_tab1, Background_tab2, Background_tab3, Background_tab4, Background_tab5;
    ImageView Icon_tab1, Icon_tab2, Icon_tab3, Icon_tab4, Icon_tab5;
    TextView Text_tab1, Text_tab2,Text_tab3, Text_tab4, Text_tab5;
    LinearLayout Line_tab1, Line_tab2, Line_tab3, Line_tab4, Line_tab5;

    // 메인 CONTEXT
    public static Context MainContext;

    // 탭 클래스 정의
    public Tab_1 tab_1;
    public Tab_2 tab_2;
    public Tab_3 tab_3;
    public Tab_4 tab_4;
    public Tab_5 tab_5;

    LinearLayout add_Linear;  // 내부 삽입 레이아웃
    LayoutInflater Inflater;
    LinearLayout.LayoutParams layoutParams;

    // 선택된 탭
    int select_tabNum = 1;

    // 선택되는 군구 (변화)
    String[] select_address2 = null;

    // 다이얼로그 상수
    final int Dialog_Search_address1           = 0;  // 시도
    final int Dialog_Search_address2           = 1;  // 군구
    final int Dialog_Search_adtpye             = 2;  // 광고 타입 (프리미엄, 일반)
    final int Dialog_Search_adloadtpye        = 3;  // 정렬 (거리순, 등록순)
    final int Dialog_Search_joperloadtpye     = 4;  // 정렬 (전체, 여자, 남자)

    final int Dialog_write_select_sector         = 5;   // 업종 선택
    final int Dialog_write_select_adress1        = 6;   // 시도 선택(작성)
    final int Dialog_write_select_adress2        = 7;   // 군구 선택(작성)
    final int Dialog_write_select_paytype        = 8;   // 지급 방법 선택
    final int Dialog_write_select_sex             = 9;   // 성별 선택
    final int Dialog_write_select_adtype         = 10;  // 광고 유형 선택
    final int Dialog_write_select_addate         = 11;  // 광고 기간 선택
    final int Dialog_write_select_imgupload      = 12;    // 이미지 업로드 (이미지 눌렀을떄)

    // 탭1 상단 레이아웃
    TextView tab1_search_adress1;
    TextView tab1_search_adress2;
    TextView tab1_search_adtype;
    TextView tab1_search_adloadtype;

    // 탭2 상단 레이아웃
    TextView tab2_search_adress1;
    TextView tab2_search_adress2;
    TextView tab2_search_joperloadtype;

    // 탭3 레이아웃
    ImageView write_picture;
    EditText write_name;
    TextView write_sector;
    TextView write_adress1;
    TextView write_adress2;
    TextView write_payvalue;
    TextView write_paytype;
    TextView write_age;
    TextView write_sex;
    EditText write_call;
    CheckBox[] options;
    EditText write_content;

    TextView write_adtype;
    TextView write_addate;
    TextView write_usepoint;
    TextView write_mypoint;

    // 탭 3 DATA
    String write_name_value = null;            //- 작성 닉네임
    String write_sector_value = null;          //- 선택 업종
    String write_adress1_value = null;         //- 선택 시도
    String write_adress2_value = null;         //- 선택 군구
    String write_payvalue_value = null;        //- 작성 금액
    String write_paytype_value = null;         //- 선택 지급방법
    String write_age_value = null;              //- 작성 나이
    String write_sex_value = null;              //- 선택 성별
    String write_call_value = null;             //- 작성 연락처
    String[] options_value = new String[9];      //- 체크 테마
    String write_content_value = null;          //- 가게 소개
    String write_adtype_value = null;           //- 선택 광고 유형 (프리미엄, 일반)
    String write_addate_value = null;           //- 선택 광고 기간
    String write_usepoint_value = null;         //- 자동 계산 소모 포인트


    //탭 3 카메라 부분
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;

    Bitmap photo = null;
    String photo_path;
    Uri mImageCaptureUri;
    private int id_view;
    private String absoultePath;
    private byte[] imgbyte = null;


    //이미지 업로드 부분
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    URL connectUrl = null;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MainContext = this;
        loading = new ProgressDialog(this);
        InitShow();

        // 버튼 초기화
        init_tablist();

        // ADD 레이아웃 파라메터 정의
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        add_Linear = (LinearLayout) findViewById(R.id.inLayout);

        Inflater = getLayoutInflater();

        Inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //초기 1번탭 실행
        Tab1_road();
    }

    /*-------------------------------------------------------------탭 ADD 함수 정의--------------------------------------------------------------*/

    public void Tab1_road()
    {
        tab_1 = new Tab_1();

        //레이아웃 인플레어
        add_Linear.removeAllViews();
        add_Linear.addView(tab_1.in_layout,layoutParams);

        tab1_initlayout();
        Tampimgdelete();   // 3번쨰 탭 임시 이미지 삭제

        // 임시
        tab_1.Tamps();

        select_tabNum = 1;
    }

    public void Tab2_road()
    {
        tab_2 = new Tab_2();

        //레이아웃 인플레어
        add_Linear.removeAllViews();
        add_Linear.addView(tab_2.in_layout,layoutParams);

        tab2_initlayout();
        Tampimgdelete();   // 3번쨰 탭 임시 이미지 삭제

        // 임시
        tab_2.Tamps();

        select_tabNum = 2;
    }

    public void Tab3_road()
    {
        tab_3 = new Tab_3();

        //레이아웃 인플레어
        add_Linear.removeAllViews();
        add_Linear.addView(tab_3.in_layout,layoutParams);

        tab3_initlayout();
        select_tabNum = 3;

    }

    public void Tab4_road()
    {
        tab_4 = new Tab_4();

        //레이아웃 인플레어
        add_Linear.removeAllViews();
        add_Linear.addView(tab_4.in_layout,layoutParams);

        Tampimgdelete();   // 3번쨰 탭 임시 이미지 삭제

        select_tabNum = 4;
    }

    public void Tab5_road()
    {
        tab_5 = new Tab_5();

        //레이아웃 인플레어
        add_Linear.removeAllViews();
        add_Linear.addView(tab_5.in_layout,layoutParams);

        Tampimgdelete();   // 3번쨰 탭 임시 이미지 삭제

        select_tabNum = 5;

    }


    public void init_tablist()
    {
        Background_tab1 = (LinearLayout) findViewById(R.id.main_tab1_background);
        Background_tab2 = (LinearLayout) findViewById(R.id.main_tab2_background);
        Background_tab3 = (LinearLayout) findViewById(R.id.main_tab3_background);
        Background_tab4 = (LinearLayout) findViewById(R.id.main_tab4_background);
        Background_tab5 = (LinearLayout) findViewById(R.id.main_tab5_background);

        Icon_tab1 = (ImageView) findViewById(R.id.main_tab1_icon);
        Icon_tab2 = (ImageView) findViewById(R.id.main_tab2_icon);
        Icon_tab3 = (ImageView) findViewById(R.id.main_tab3_icon);
        Icon_tab4 = (ImageView) findViewById(R.id.main_tab4_icon);
        Icon_tab5 = (ImageView) findViewById(R.id.main_tab5_icon);

        Text_tab1 = (TextView) findViewById(R.id.main_tab1_text);
        Text_tab2 = (TextView) findViewById(R.id.main_tab2_text);
        Text_tab3 = (TextView) findViewById(R.id.main_tab3_text);
        Text_tab4 = (TextView) findViewById(R.id.main_tab4_text);
        Text_tab5 = (TextView) findViewById(R.id.main_tab5_text);

        Line_tab1 = (LinearLayout) findViewById(R.id.main_tab1_line);
        Line_tab2 = (LinearLayout) findViewById(R.id.main_tab2_line);
        Line_tab3 = (LinearLayout) findViewById(R.id.main_tab3_line);
        Line_tab4 = (LinearLayout) findViewById(R.id.main_tab4_line);
        Line_tab5 = (LinearLayout) findViewById(R.id.main_tab5_line);

    }


    /*-------------------------------------------------------------버튼 클릭--------------------------------------------------------------*/

    public void click_main_tab1(View v)
    {
        SET_tab_refash(1);

        Tab1_road();
    }

    public void click_main_tab2(View v)
    {
        SET_tab_refash(2);

        Tab2_road();
    }

    public void click_main_tab3(View v)
    {
        SET_tab_refash(3);

        Tab3_road();
    }

    public void click_main_tab4(View v)
    {
        SET_tab_refash(4);

        Tab4_road();
    }

    public void click_main_tab5(View v)
    {
        SET_tab_refash(5);

        Tab5_road();
    }

/*--------------------------------------------------------------------------공용 함수  ----------------------------------------   */

    //업체로 쪽지 보내기
    public void sand_massage(String tagetidx, String adtype, String sex, String nickname, String age, String loaction)
    {
        Intent intent = new Intent(this, Popup_letter.class);
        intent.putExtra("ad_type",adtype);
        intent.putExtra("taget_idx",tagetidx);
        intent.putExtra("taget_sex",sex);
        intent.putExtra("taget_nickname",nickname);
        intent.putExtra("taget_age",age);
        intent.putExtra("taget_loaction",loaction);

        startActivity(intent);
    }


    // 이미지 뷰어
    public void ImageViewer(String url, String nickname, String age)
    {
        Intent intent = new Intent(Main.MainContext, ImageView_Activity.class);

        intent.putExtra("adimg_url",url);
        intent.putExtra("user_nickname",nickname);
        intent.putExtra("user_age",age);

        startActivity(intent);
    }



/*--------------------------------------------------------------------------탭 1 함수  ----------------------------------------   */

    // 탭1 이벤트 리스너 + 텍스트 업데이트
    public void tab1_initlayout()
    {
        // 탭1 레이아웃 연결
        tab1_search_adress1 = (TextView) findViewById(R.id.tab1_text_adress1);
        tab1_search_adress2 = (TextView) findViewById(R.id.tab1_text_adress2);
        tab1_search_adtype = (TextView) findViewById(R.id.tab1_text_adtype);
        tab1_search_adloadtype = (TextView) findViewById(R.id.tab1_text_adloadtype);
    }

    // 검색 시도 클릭
    public void tab1_click_adress1(View v)
    {
        select_address2 = null;
        showDialog(Dialog_Search_address1);
    }

    // 검색 군구 클릭
    public void tab1_click_adress2(View v)
    {
        if(select_address2 == null)
        {
            Toast.makeText(Main.MainContext,"시도를 먼저 선택해주세요.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            showDialog(Dialog_Search_address2);
        }
    }

    // 광고 타입 클릭
    public void tab1_click_adtype(View v)
    {
        showDialog(Dialog_Search_adtpye);
    }

    // 광고 정렬 타입 클릭
    public void tab1_click_adloadtype(View v)
    {
        showDialog(Dialog_Search_adloadtpye);
    }


    // 업체 상세보기
    public void company_detail(String adidx, String tagetidx, String adtype, String sex, String nickname, String age, String loaction)
    {
        Intent intent = new Intent(this, ReadAds_Activity.class);
        intent.putExtra("ad_idx",adidx);
        intent.putExtra("ad_type",adtype);
        intent.putExtra("taget_idx",tagetidx);
        intent.putExtra("taget_sex",sex);
        intent.putExtra("taget_nickname",nickname);
        intent.putExtra("taget_age",age);
        intent.putExtra("taget_loaction",loaction);

        startActivity(intent);

    }



/*--------------------------------------------------------------------------탭 2 함수  ----------------------------------------   */

    // 탭2 이벤트 리스너 + 텍스트 업데이트
    public void tab2_initlayout()
    {
        // 탭2 레이아웃 연결
        tab2_search_adress1 = (TextView) findViewById(R.id.tab2_text_adress1);
        tab2_search_adress2 = (TextView) findViewById(R.id.tab2_text_adress2);
        tab2_search_joperloadtype = (TextView) findViewById(R.id.tab2_text_joperloadtype);
    }

    // 시도 검색
    public void tab2_click_adress1(View v)
    {
        select_address2 = null;
        showDialog(Dialog_Search_address1);
    }

    // 군구 검색
    public void tab2_click_adress2(View v)
    {
        if(select_address2 == null)
        {
            Toast.makeText(Main.MainContext,"시도를 먼저 선택해주세요.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            showDialog(Dialog_Search_address2);
        }
    }

    // 검색 타입 (전체, 남자, 여자)
    public void tab2_click_joperloadtype(View v)
    {
        showDialog(Dialog_Search_joperloadtpye);
    }

    // 구직자 상세보기
    public void joper_detail(String deviceid, String sex, String nickname, String age, String loaction)
    {

        Intent intent = new Intent(this, Readjoper_Activity.class);
        intent.putExtra("taget_idx",deviceid);
        intent.putExtra("taget_sex",sex);
        intent.putExtra("taget_nickname",nickname);
        intent.putExtra("taget_age",age);
        intent.putExtra("taget_loaction",loaction);

        startActivity(intent);

    }

/*--------------------------------------------------------------------------탭 3 함수  ----------------------------------------   */

    // 탭3 이벤트 리스너 + 텍스트 업데이트
    public void tab3_initlayout()
    {
        // 프로필 이미지
        write_picture = (ImageView) findViewById(R.id.tab3_write_mainpicture);
        Glide.with(this).load(R.drawable.default_picture).centerCrop().bitmapTransform(new CropCircleTransformation(this)).into(write_picture);
        // 프로필 이미지 클릭 리스너
        write_picture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDialog(Dialog_write_select_imgupload);
            }
        });

        // (EDIT) 가게 이름
        write_name = (EditText) findViewById(R.id.tab3_write_edit_company_name);

        // (TEXT) 업종
        write_sector = (TextView) findViewById(R.id.tab3_write_text_company_sector);

        // (TEXT) 시도
        write_adress1 = (TextView) findViewById(R.id.tab3_write_text_company_adress1);

        // (TEXT) 시도
        write_adress2 = (TextView) findViewById(R.id.tab3_write_text_company_adress2);

        // (EDIT) 지급금액
        write_payvalue = (EditText) findViewById(R.id.tab3_write_edit_company_payvalue);

        // (TEXT) 지급 타입
        write_paytype = (TextView) findViewById(R.id.tab3_write_text_company_pay);

        // (EDIT) 나이
        write_age = (EditText) findViewById(R.id.tab3_write_edit_company_ageminmax);

        // (TEXT) 성별
        write_sex = (TextView) findViewById(R.id.tab3_write_text_company_sex);

        // (EDIT) 연락처
        write_call = (EditText) findViewById(R.id.tab3_write_edit_company_phonenumber);

        //(CHACK) 테마 선택
        options_value = new String[]{"false", "false", "false", "false", "false", "false", "false", "false", "false"};
        options = new CheckBox[9];
        options[0] = (CheckBox) findViewById(R.id.tab3_write_option1);
        options[1] = (CheckBox) findViewById(R.id.tab3_write_option2);
        options[2] = (CheckBox) findViewById(R.id.tab3_write_option3);
        options[3] = (CheckBox) findViewById(R.id.tab3_write_option4);
        options[4] = (CheckBox) findViewById(R.id.tab3_write_option5);
        options[5] = (CheckBox) findViewById(R.id.tab3_write_option6);
        options[6] = (CheckBox) findViewById(R.id.tab3_write_option7);
        options[7] = (CheckBox) findViewById(R.id.tab3_write_option8);
        options[8] = (CheckBox) findViewById(R.id.tab3_write_option9);

        //(EDIT) 가게 소개
        write_content = (EditText) findViewById(R.id.tab3_write_edit_company_content);

        // (TEXT) 광고 유형
        write_adtype = (TextView) findViewById(R.id.tab3_write_text_ad_type);

        // (TEXT) 광고 기간
        write_addate = (TextView) findViewById(R.id.tab3_write_text_ad_date);

        // (TEXT) 소모 포인트
        write_usepoint = (TextView) findViewById(R.id.tab3_write_text_ad_usepoint);

        // (TEXT) 나의 포인트
        write_mypoint = (TextView) findViewById(R.id.tab3_write_text_ad_mypoint);
    }


    //업종 선택
    public void tab3_click_select_sector(View v)
    {
        showDialog(Dialog_write_select_sector);
    }

    //시도 선택
    public void tab3_click_select_adress1(View v)
    {
        showDialog(Dialog_write_select_adress1);
    }

    //군구 선택
    public void tab3_click_select_adress2(View v)
    {
        showDialog(Dialog_write_select_adress2);
    }

    //지급 방법 선택
    public void tab3_click_select_pay(View v)
    {
        showDialog(Dialog_write_select_paytype);
    }

    // 성별 선택
    public void tab3_click_select_sex(View v)
    {
        showDialog(Dialog_write_select_sex);
    }

    // 광고 유형 선택(프리미엄, 일반)
    public void tab3_click_select_adtype(View v)
    {
        showDialog(Dialog_write_select_adtype);
    }

    // 광고 기간 선택
    public void tab3_click_select_addate(View v)
    {
        if(write_adtype.getText().toString().equals("") || write_adtype.getText().toString().equals(null))
        {
            Toast.makeText(this, "광고 유형을 먼저 선택해주세요.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            showDialog(Dialog_write_select_addate);
        }
    }

    // 광고 등록  submit
    public void tab3_click_submit(View v)
    {
        if(chackvalue())
        {
            Toast.makeText(this, "닉네임 : "+ write_name_value
                    +"\n 업종 : "+ write_sector_value
                    +"\n 시도 : "+ write_adress1_value
                    +"\n 군구 : "+ write_adress2_value
                    +"\n 금액 : "+ write_payvalue_value
                    +"\n 지급 : "+ write_paytype_value
                    +"\n 나이 : "+ write_age_value
                    +"\n 성별 : "+ write_sex_value
                    +"\n 연락처 : "+ write_call_value
                    +"\n 테마-초보가능 : "+ options_value[0]
                    +"\n 테마-당일지급 : "+ options_value[1]
                    +"\n 테마-경력우대 : "+ options_value[2]
                    +"\n 테마-출퇴근자유 : "+ options_value[3]
                    +"\n 테마-파트타임 : "+ options_value[4]
                    +"\n 테마-차비지원 : "+ options_value[5]
                    +"\n 테마-숙식제공 : "+ options_value[6]
                    +"\n 테마-성형지원 : "+ options_value[7]
                    +"\n 테마-선불가능 : "+ options_value[8]
                    +"\n 가게소개 : "+ write_content_value
                    +"\n 광고유형 : "+ write_adtype_value
                    +"\n 광고기간 : "+ write_addate_value
                    +"\n 소모 포인트 : "+ write_usepoint_value,Toast.LENGTH_LONG).show();


            SET_tab_refash(1);

            Tab1_road();
        }
    }



    // 입력값 NULL , 입력없음 체크
    public boolean chackvalue()
    {
        //  가게 이름 적용
        write_name_value = write_name.getText().toString();

        // 업종 적용
        write_sector_value = write_sector.getText().toString();

        // 시도 적용
        write_adress1_value = write_adress1.getText().toString();

        // 군구 적용
        write_adress2_value = write_adress2.getText().toString();

        // 페이 금액 적용
        write_payvalue_value = write_payvalue.getText().toString();

        // 페이 지급방식 적용
        write_paytype_value = write_paytype.getText().toString();

        // 나이 적용
        write_age_value = write_age.getText().toString();

        //성별 적용
        write_sex_value = write_sex.getText().toString();

        //연락처 적용
        write_call_value = write_call.getText().toString();

        //옵션 적용
        for(int i =0; i< options.length; i++)
        {
            if(options[i].isChecked())
            {
                options_value[i] = "true";
            }
            else
            {
                options_value[i] = "false";
            }

        }

        //가게 소개 적용
        write_content_value = write_content.getText().toString();

        //광고 옵션 적용
        write_adtype_value = write_adtype.getText().toString();

        //광고 기간 적용
        write_addate_value = write_addate.getText().toString();

////////////////////////////////////////////////////////////////////////////////////

        boolean chack = false;

        if(write_name_value.toString().equals("") || write_name_value.toString().equals(null))
        {
            Toast.makeText(this, "가게 이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(write_sector_value.toString().equals("") || write_sector_value.toString().equals(null))
        {
            Toast.makeText(this, "가게 업종을 선택해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(write_adress1_value.toString().equals("") || write_adress1_value.toString().equals(null))
        {
            Toast.makeText(this, "가게 위치(시도)를 선택해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(write_adress2_value.toString().equals("") || write_adress2_value.toString().equals(null))
        {
            Toast.makeText(this, "가게 위치(군구)를 선택해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(write_payvalue_value.toString().equals("") || write_payvalue_value.toString().equals(null))
        {
            Toast.makeText(this, "지급 금액을 입력해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(write_paytype_value.toString().equals("") || write_paytype_value.toString().equals(null))
        {
            Toast.makeText(this, "지급 방식을 선택해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(write_age_value.toString().equals("") || write_age_value.toString().equals(null))
        {
            Toast.makeText(this, "나이를 입력해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(write_sex_value.toString().equals("") || write_sex_value.toString().equals(null))
        {
            Toast.makeText(this, "성별을 선택해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(write_call_value.toString().equals("") || write_call_value.toString().equals(null))
        {
            Toast.makeText(this, "연락처를 입력해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(write_content_value.toString().equals("") || write_content_value.toString().equals(null))
        {
            Toast.makeText(this, "가게 소개를 작성해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(write_content_value.toString().equals("") || write_content_value.toString().equals(null))
        {
            Toast.makeText(this, "가게 소개를 작성해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(write_adtype_value.toString().equals("") || write_adtype_value.toString().equals(null))
        {
            Toast.makeText(this, "광고 유형을 선택해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else if(write_addate_value.toString().equals("") || write_addate_value.toString().equals(null))
        {
            Toast.makeText(this, "광고 기간을 선택해주세요.",Toast.LENGTH_SHORT).show();
            chack = false;
        }
        else
        {
            chack = true;
        }

        return chack;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 사진, 갤러리 부분


    //////////////////////////////////// 사진 카메라, 앨범 //////////////////////////////////////////////

    public void doTakePhotoAction() // 카메라 촬영 후 이미지 가져오기
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".png";
        //mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        // Crop된 이미지를 저장할 파일의 경로를 생성
        mImageCaptureUri = createSaveCropFile();

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }


    // 앨범에서 사진 가져오기
    public void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case PICK_FROM_ALBUM: {   //앨범 이미지
                mImageCaptureUri = data.getData();
                File original_file = getImageFile(mImageCaptureUri);

                mImageCaptureUri = createSaveCropFile();
                File cpoy_file = new File(mImageCaptureUri.getPath());

                // SD카드에 저장된 파일을 이미지 Crop을 위해 복사한다.
                copyFile(original_file, cpoy_file);

            }

            case PICK_FROM_CAMERA: {   //촬영 이미지
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("output", mImageCaptureUri);

                /*
                intent.putExtra("outputX", 1000);
                intent.putExtra("outputY", 1000);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                */
                startActivityForResult(intent, CROP_FROM_iMAGE);

                break;
            }
            case CROP_FROM_iMAGE: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.

                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에

                // 임시 파일을 삭제합니다.

                if (resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();
                // CROP된 이미지를 저장하기 위한 FILE 경로

                // String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/SmartWheel/"+System.currentTimeMillis()+".jpg";

                if (extras != null) {
                    /*
                    Bitmap photo = (Bitmap) data.getExtras().get("data"); // CROP된 BITMAP
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG,100,stream);
                    */
                    String full_path = mImageCaptureUri.getPath();
                    photo_path = full_path.substring(0, full_path.length());
                    photo = BitmapFactory.decodeFile(photo_path);

                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 50, byteArray);
                    imgbyte = byteArray.toByteArray();


                    //write_img_select.setImageBitmap(photo);
                    Glide.with(this.getApplicationContext()).load(photo_path).centerCrop().bitmapTransform(new CropCircleTransformation(this.getApplicationContext()))
                            .error(R.drawable.default_companyimg).into(write_picture);
                    //                Log.i("TAG1,", "포토 :" + photo + "   갯 :"+ photo.getGenerationId());
                   // Toast.makeText(getBaseContext(), "잘생겼는지 체크중..", Toast.LENGTH_SHORT).show();


                }
                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
/*
                if (f.exists()) {
                    f.delete();
                }
*/
                break;
            }
        }
    }

    /**
     * Crop된 이미지가 저장될 파일을 만든다.
     *
     * @return Uri
     */

    private Uri createSaveCropFile() {
        Uri uri;
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        return uri;
    }

    /**
     * 선택된 uri의 사진 Path를 가져온다.
     * uri 가 null 경우 마지막에 저장된 사진을 가져온다.
     *
     * @param uri
     * @return
     */
    private File getImageFile(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        if (uri == null) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        Cursor mCursor = getContentResolver().query(uri, projection, null, null,
                MediaStore.Images.Media.DATE_MODIFIED + " desc");
        if (mCursor == null || mCursor.getCount() < 1) {
            return null; // no cursor or no record
        }
        int column_index = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        mCursor.moveToFirst();

        String path = mCursor.getString(column_index);

        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }

        return new File(path);
    }

    public static boolean copyFile(File srcFile, File destFile) {
        boolean result = false;
        try {
            InputStream in = new FileInputStream(srcFile);
            try {
                result = copyToFile(in, destFile);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    /**
     * Copy data from a source stream to destFile.
     * Return true if succeed, return false if failed.
     */
    private static boolean copyToFile(InputStream inputStream, File destFile) {
        try {
            OutputStream out = new FileOutputStream(destFile);
            try {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) >= 0) {
                    out.write(buffer, 0, bytesRead);
                }
            } finally {
                out.close();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void Tampimgdelete()
    {
        // 임시 파일 삭제

        if(imgbyte != null)
        {
            File f = new File(mImageCaptureUri.getPath());
            if (f.exists())
            {
                f.delete();
            }
        }
    }



    //------------------------------------------------------------------탭 4 ----------------------------------------------------------//








    //------------------------------------------------------------------탭 5 ----------------------------------------------------------//



    // 버튼 리스너
    // 이용약관
    public void tab5_click_useagee(View v)
    {
        Intent intent = new Intent(this, Agee_Activity.class);
        intent.putExtra("agee_type","agee1");

        startActivity(intent);
    }

    // 충전하기
    public void tab5_click_charge(View v)
    {
        Intent intent = new Intent(this, Charge_Activity.class);
        startActivity(intent);
    }

    // 내정보
    public void tab5_click_mypage(View v)
    {
        Intent intent = new Intent(this, Mypage_Activity.class);
        startActivity(intent);
    }

    // 설정
    public void tab5_click_setting(View v)
    {
        Intent intent = new Intent(this, Setting_Activity.class);
        startActivity(intent);
    }




    //------------------------------------------------------------------다이얼로그 ----------------------------------------------------------//
    public Dialog onCreateDialog(int id)
    {
        switch(id)
        {
            case Dialog_Search_address1: // 시도
            {
                final CharSequence[] item = mergeArrays(getResources().getStringArray(R.array.all), getResources().getStringArray(R.array.area));
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("검색 하려는 지역을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if(select_tabNum == 1)
                                {
                                    tab1_search_adress1.setText(item[i].toString());
                                    tab1_search_adress2.setText("전체");
                                }
                                else
                                {
                                    tab2_search_adress1.setText(item[i].toString());
                                    tab2_search_adress2.setText("전체");
                                }

                                    //  text_address_1.setText(item[i].toString());
                                    //  DATA_address_1 = item[i].toString();

                                 select_address(item[i].toString());
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;

            }
            case Dialog_Search_address2: // 군구
            {
                final CharSequence[] item = mergeArrays(getResources().getStringArray(R.array.all), select_address2);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("세부 지역을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if(select_tabNum == 1)
                                {
                                    tab1_search_adress2.setText(item[i].toString());
                                }
                                else
                                {
                                    tab2_search_adress2.setText(item[i].toString());
                                }

                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;

            }
            case Dialog_Search_adtpye: // 애드 타입
            {
                final CharSequence[] item = {"프리미엄","일반"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("광고 유형을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {

                                tab1_search_adtype.setText(item[i].toString());

                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;

            }
            case Dialog_Search_adloadtpye: // 애드 로드 타입
            {
                final CharSequence[] item = {"거리순", "등록순"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("광고 정렬을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                tab1_search_adloadtype.setText(item[i].toString());
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;

            }
            case Dialog_Search_joperloadtpye:
            {
                final CharSequence[] item = {"전체", "여자", "남자"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("검색 성별을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                tab2_search_joperloadtype.setText(item[i].toString());
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }
            case Dialog_write_select_sector:
            {
                final CharSequence[] item = getResources().getStringArray(R.array.sector);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("업종을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {

                                write_sector.setText(item[i].toString());

                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }
            case Dialog_write_select_adress1:
            {
                final CharSequence[] item = getResources().getStringArray(R.array.area);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("검색 하려는 지역을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                write_adress1.setText(item[i].toString());
                                select_address(item[i].toString());
                                write_adress2.setText(null);
                            }
                        });

                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }
            case Dialog_write_select_adress2:
            {
                final CharSequence[] item = select_address2;
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("검색 하려는 지역을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                write_adress2.setText(item[i].toString());
                            }
                        });

                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }
            case Dialog_write_select_paytype:
            {
                final CharSequence[] item = getResources().getStringArray(R.array.paytype);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("지급 유형을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {

                                write_paytype.setText(item[i].toString());

                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }
            case Dialog_write_select_sex:
            {
                final CharSequence[] item = {"여자", "남자"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("성별을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                write_sex.setText(item[i].toString());
                                if(item[i].toString().equals("여자"))
                                {
                                    write_sex.setTextColor(getResources().getColor(R.color.girl));
                                }
                                else
                                {
                                    write_sex.setTextColor(getResources().getColor(R.color.man));
                                }
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }
            case Dialog_write_select_adtype:
            {
                final CharSequence[] item = {"프리미엄", "일반"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("게시 유형을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                write_adtype.setText(item[i].toString());
                                if(item[i].toString().equals("프리미엄"))
                                {
                                    write_adtype.setTextColor(getResources().getColor(R.color.option_on));
                                }
                                else
                                {
                                    write_adtype.setTextColor(getResources().getColor(R.color.textcolor));
                                }
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }
            case Dialog_write_select_addate:
            {
                final CharSequence[] item = {"30일", "60일", "90일", "1년"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("광고 기간을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                write_addate.setText(item[i].toString());
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }
            case Dialog_write_select_imgupload:
                final CharSequence[] item = {"카메라", "갤러리", "취소"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("사진 불러오기") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (item[i].toString().equals(item[0])) {
                                    doTakePhotoAction();
                                }
                                else if (item[i].toString().equals(item[1]))
                                {
                                    doTakeAlbumAction();
                                }
                                else
                                {
                                    if (mImageCaptureUri != null) {
                                        // 임시 파일 삭제
                                        File f = new File(mImageCaptureUri.getPath());
                                        if (f.exists()) {
                                            f.delete();

                                        }
                                    }
                                    dialogInterface.dismiss();
                                }
                            }
                        });

                AlertDialog alert = builder.create();  //알림 객체 생성
                return alert;
        }

        return null;
    }

    @Deprecated
    protected void onPrepareDialog(int id, Dialog dialog)
    {
        super.onPrepareDialog(id, dialog);
        //해당 다이얼로그는 매번 다시 부름.
        switch(id)
        {
            case Dialog_Search_address1:
            {
                removeDialog(Dialog_Search_address2);

                //없에면 다시 새로 그림
            }
            case Dialog_write_select_adress1:
            {
                removeDialog(Dialog_write_select_adress2);
            }

        }
    }






    //-------------------------------------------------------- 배열 합치는 메서드-------------------------------//
    public CharSequence[] mergeArrays(CharSequence[] arrA, CharSequence[] arrB)
    {

        int sumLength = arrA.length + arrB.length;
        CharSequence[] arrSum = new CharSequence[sumLength];

        for (int i = 0; i < sumLength; i++)
        {
            if (i < arrA.length)
            {
                arrSum[i] = arrA[i];
            }
            else
            {
                arrSum[i] = arrB[i - arrA.length];
            }
        }
        return arrSum;
    }


    //--------------------------------------------------------------- 군구 선택 함수----------------------//
    public void select_address(String value)
    {

        Log.i("ttt","select_tabNum : "+select_tabNum + "\n value : "+value);
        select_address2 = null;


        if (value.toString().equals("전체"))
        {
            select_address2 = null;
        }
        else if(value.toString().equals("서울"))
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

/*--------------------------------------------------------------------------탭 체인지 이미지 변경 막코딩 ----------------------------------------   */
    public void SET_tab_refash(int value)
    {
        switch (value)
        {
            case 1:
            {
                Background_tab1.setBackgroundColor(getResources().getColor(R.color.tab_on));
                Background_tab2.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab3.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab4.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab5.setBackgroundColor(getResources().getColor(R.color.tab_off));

                Icon_tab1.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab1_1));
                Icon_tab2.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab2_2));
                Icon_tab3.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab3_2));
                Icon_tab4.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab4_2));
                Icon_tab5.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab5_2));

                Text_tab1.setTextColor(getResources().getColor(R.color.tab_on_text));
                Text_tab2.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab3.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab4.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab5.setTextColor(getResources().getColor(R.color.tab_off_text));

                Line_tab1.setVisibility(View.VISIBLE);
                Line_tab2.setVisibility(View.INVISIBLE);
                Line_tab3.setVisibility(View.INVISIBLE);
                Line_tab4.setVisibility(View.INVISIBLE);
                Line_tab5.setVisibility(View.INVISIBLE);
                break;
            }
            case 2:
            {
                Background_tab1.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab2.setBackgroundColor(getResources().getColor(R.color.tab_on));
                Background_tab3.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab4.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab5.setBackgroundColor(getResources().getColor(R.color.tab_off));

                Icon_tab1.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab1_2));
                Icon_tab2.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab2_1));
                Icon_tab3.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab3_2));
                Icon_tab4.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab4_2));
                Icon_tab5.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab5_2));

                Text_tab1.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab2.setTextColor(getResources().getColor(R.color.tab_on_text));
                Text_tab3.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab4.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab5.setTextColor(getResources().getColor(R.color.tab_off_text));

                Line_tab1.setVisibility(View.INVISIBLE);
                Line_tab2.setVisibility(View.VISIBLE);
                Line_tab3.setVisibility(View.INVISIBLE);
                Line_tab4.setVisibility(View.INVISIBLE);
                Line_tab5.setVisibility(View.INVISIBLE);
                break;
            }
            case 3:
            {
                Background_tab1.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab2.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab3.setBackgroundColor(getResources().getColor(R.color.tab_on));
                Background_tab4.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab5.setBackgroundColor(getResources().getColor(R.color.tab_off));

                Icon_tab1.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab1_2));
                Icon_tab2.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab2_2));
                Icon_tab3.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab3_1));
                Icon_tab4.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab4_2));
                Icon_tab5.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab5_2));

                Text_tab1.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab2.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab3.setTextColor(getResources().getColor(R.color.tab_on_text));
                Text_tab4.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab5.setTextColor(getResources().getColor(R.color.tab_off_text));

                Line_tab1.setVisibility(View.INVISIBLE);
                Line_tab2.setVisibility(View.INVISIBLE);
                Line_tab3.setVisibility(View.VISIBLE);
                Line_tab4.setVisibility(View.INVISIBLE);
                Line_tab5.setVisibility(View.INVISIBLE);
                break;
            }
            case 4:
            {
                Background_tab1.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab2.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab3.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab4.setBackgroundColor(getResources().getColor(R.color.tab_on));
                Background_tab5.setBackgroundColor(getResources().getColor(R.color.tab_off));

                Icon_tab1.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab1_2));
                Icon_tab2.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab2_2));
                Icon_tab3.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab3_2));
                Icon_tab4.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab4_1));
                Icon_tab5.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab5_2));

                Text_tab1.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab2.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab3.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab4.setTextColor(getResources().getColor(R.color.tab_on_text));
                Text_tab5.setTextColor(getResources().getColor(R.color.tab_off_text));

                Line_tab1.setVisibility(View.INVISIBLE);
                Line_tab2.setVisibility(View.INVISIBLE);
                Line_tab3.setVisibility(View.INVISIBLE);
                Line_tab4.setVisibility(View.VISIBLE);
                Line_tab5.setVisibility(View.INVISIBLE);
                break;
            }
            case 5:
            {
                Background_tab1.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab2.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab3.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab4.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab5.setBackgroundColor(getResources().getColor(R.color.tab_on));

                Icon_tab1.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab1_2));
                Icon_tab2.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab2_2));
                Icon_tab3.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab3_2));
                Icon_tab4.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab4_2));
                Icon_tab5.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab5_1));

                Text_tab1.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab2.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab3.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab4.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab5.setTextColor(getResources().getColor(R.color.tab_on_text));

                Line_tab1.setVisibility(View.INVISIBLE);
                Line_tab2.setVisibility(View.INVISIBLE);
                Line_tab3.setVisibility(View.INVISIBLE);
                Line_tab4.setVisibility(View.INVISIBLE);
                Line_tab5.setVisibility(View.VISIBLE);
                break;
            }
        }
    }


    // 뒤로가기 (종료 메인이라서)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            //하드웨어 뒤로가기 버튼에 따른 이벤트 설정
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("[앱 종료]") // 제목 설정
                        .setMessage("밤엔알바를 종료 하시겠습니까?")
                        .setCancelable(false)  //뒤로 버튼 클릭시 취소 설정
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            // 예 버튼 클릭시 설정
                            public void onClick(DialogInterface dialogInterface, int i)
                            {

                                // 프로세스 종료.
                                Tampimgdelete(); // 임시저장 작성 이미지 삭제
                                finish();
                            }
                        })
                        .setNegativeButton("아니요", null).show();

                AlertDialog alert = builder.create();  //알림 객체 생성
        }
        return super.onKeyDown(keyCode, event);
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
