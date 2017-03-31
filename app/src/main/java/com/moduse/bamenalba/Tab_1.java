package com.moduse.bamenalba;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Vector;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by sejung on 2017-03-22.
 */

public class Tab_1 extends Activity
{
    ProgressDialog loading;

    AppInfo appInfo;

    public LinearLayout in_layout;
    public LayoutInflater Inflater;


    ArrayList<Data_Ad> listItem = new ArrayList<Data_Ad>();
    private ListView list = null;
    private CustomAdapter customAdapter = null;

    // 페이지 넘머 기록
    int Last_ListIndex = 0;
    boolean LastTalkVisibleFlag = false;

    // 거리 임시
    int Tamp_location = 0;



    Tab_1()
    {
        loading = new ProgressDialog(Main.MainContext);
        InitShow();

        Inflater = ((Main) Main.MainContext).getLayoutInflater();
        Inflater = (LayoutInflater) Main.MainContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        in_layout = (LinearLayout) Inflater.inflate(R.layout.tab_1, null);
    }


    //------------------------------------------------------- AD 불러오기------------------------------------------//

    public void LoadADs()    //  내글 불러오기  (내글)
    {
        StartShow();

        appInfo = new AppInfo();

        // post 전달 인자
        Vector<NameValuePair> list = new Vector<NameValuePair>();
        //여기에 전달할 인자를 담는다. String으로 넣는것이 안전하다.
        list.add(new BasicNameValuePair("type",""));


        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
            String url = "";  // url 설정
            HttpPost request = new HttpPost(url);
            request.setEntity(entity);

            //downsever = new phpdown();  // 쓰레드 생성
          //  downsever.execute(request);
        }
        catch(Exception e)
        {
            // 서버에 연결할 수 없습니다 토스트 메세지 보내기
            Toast.makeText(Main.MainContext, "서버접속이 불안정합니다. 인터넷 환경을 확인해주세요.", Toast.LENGTH_SHORT).show();
            Log.e("Exception Error", e.toString());
        }

        // Remove_list();
        Make_Talk();
        // 리스트 아이템 지우기
    }

    public void Tamps()
    {
        AppInfo.MY_TYPE = "user";
        AppInfo.MY_LOGINID = "345345345345";

        String idx           = "1";
        String adidx         = "4576456345thfghfg";
        String deviceID      = "12412412";
        String ad_type       = "premium";
        String ad_name       = "가락동1번지";
        String ad_sector     = "노래방";
        String ad_img        = "none";
        String ad_sex        = "남자";
        String ad_pay        = "일급";
        String ad_payvalue   = "500,000";
        String ad_adress1    = "부산";
        String ad_adress2    = "달동";
        String ad_location   = "10";
        String user_sex      = "남자";
        String user_name     = "김실장";
        String user_age      = "33";

        listItem.add(new Data_Ad(idx, adidx, deviceID, ad_type, ad_name, ad_sector, ad_img, ad_sex, ad_pay, ad_payvalue, ad_adress1, ad_adress2, ad_location, user_sex, user_name, user_age));

         idx             = "2";
         adidx           = "657567567567567567";
         deviceID        = "352722070234532";
         ad_type         = "none";
         ad_name         = "황금동2번지";
         ad_sector       = "룸사롱/주점";
         ad_img          = "http://nakk20.raonnet.com/profileimg/sample_119.png";
         ad_sex          = "여자";
         ad_pay          = "일급";
         ad_payvalue     = "1,000,000";
         ad_adress1      = "광주";
         ad_adress2      = "광산구";
         ad_location     = "56";
         user_sex        = "여자";
         user_name       = "김마담";
         user_age        = "42";

        listItem.add(new Data_Ad(idx, adidx, deviceID, ad_type, ad_name, ad_sector, ad_img, ad_sex, ad_pay, ad_payvalue, ad_adress1, ad_adress2, ad_location, user_sex, user_name, user_age));

        idx              = "3";
        adidx            = "12156546567";
        deviceID         = "456456";
        ad_type          = "premium";
        ad_name          = "황금동2번지";
        ad_sector        = "가라오케";
        ad_img           = "http://nakk20.raonnet.com/profileimg/sample_110.png";
        ad_sex           = "여자";
        ad_pay           = "주급";
        ad_payvalue      = "500,000";
        ad_adress1       = "제주";
        ad_adress2       = "제주시";
        ad_location      = "456";
        user_sex         = "남자";
        user_name        = "미미짱";
        user_age         = "66";

        listItem.add(new Data_Ad(idx, adidx, deviceID, ad_type, ad_name, ad_sector, ad_img, ad_sex, ad_pay, ad_payvalue, ad_adress1, ad_adress2, ad_location, user_sex, user_name, user_age));

        Make_Talk();
    }



    //------------------------------------------------------- 리스트 어댑터 함수----------------------------------//

    private class CustomAdapter extends ArrayAdapter<Data_Ad>
    {
        private LayoutInflater m_inflager = null;
        private ArrayList<Data_Ad> items;
        private int m_recource_id;
        ViewHolder holder;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<Data_Ad> objects)
        {
            super(context, textViewResourceId, objects);
            items = objects;
            m_recource_id = textViewResourceId;

            m_inflager = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Inflate xml 에 씌여져 있는 view 의 정의를 실제 view 객체로 만드는 역할
            //inflate 를 사용하기 위해서는 우선 inflater 를 얻어와야 합니다.
            //LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            // final View view;
            final Data_Ad data = items.get(position);
            final int index = position;

            if(convertView == null)
            {
                convertView = m_inflager.inflate(R.layout.item_ad, parent,false);

                holder = new ViewHolder();

                //  view 에서 얻어 초기화
                holder.View_ad_type = (LinearLayout) convertView.findViewById(R.id.ad_adtype);
                holder.View_ad_type_margin = (LinearLayout) convertView.findViewById(R.id.ad_adtype_margin);
                holder.View_ad_img = (ImageView) convertView.findViewById(R.id.ad_adimg);
                holder.View_ad_title = (TextView) convertView.findViewById(R.id.ad_adtitle);
                holder.View_ad_nameandage = (TextView) convertView.findViewById(R.id.ad_adnicknameandage);
                holder.View_ad_adsector = (TextView) convertView.findViewById(R.id.ad_adsector);
                holder.View_ad_sex = (TextView) convertView.findViewById(R.id.ad_adsex);
                holder.View_ad_pay = (TextView) convertView.findViewById(R.id.ad_adpay);
                holder.View_ad_payvalue = (TextView) convertView.findViewById(R.id.ad_adpayvalue);
                holder.View_ad_adress1 = (TextView) convertView.findViewById(R.id.ad_adadress1);
                holder.View_ad_adress2 = (TextView) convertView.findViewById(R.id.ad_adadress2);
                holder.View_ad_loaction = (TextView) convertView.findViewById(R.id.ad_adlocation);
                holder.View_ad_location_panel = (LinearLayout) convertView.findViewById(R.id.ad_adlocation_panel);


                holder.View_user_panel = (LinearLayout) convertView.findViewById(R.id.ad_user_panel);
                holder.View_my_panel = (LinearLayout) convertView.findViewById(R.id.ad_my_panel);
                holder.View_user_ad_detail = (LinearLayout) convertView.findViewById(R.id.ad_btn_detail);
                holder.View_user_ad_sand_massage = (LinearLayout) convertView.findViewById(R.id.ad_btn_sand_massage);
                holder.View_user_ad_fix = (LinearLayout) convertView.findViewById(R.id.ad_btn_adfix);
                holder.View_user_ad_delete = (LinearLayout) convertView.findViewById(R.id.ad_btn_addelete);




                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            if(data != null)
            {
                // 프리미엄 뱃지
                if(data.GET_ad_type().toString().equals("premium"))
                {
                    holder.View_ad_type.setVisibility(View.VISIBLE);
                    holder.View_ad_type_margin.setVisibility(View.GONE);
                }
                else
                {
                    holder.View_ad_type_margin.setVisibility(View.VISIBLE);
                    holder.View_ad_type.setVisibility(View.GONE);
                }
                // 프리미엄 뱃지 END

                // 광고 이미지 뷰어
                if(data.GET_ad_img().toString().equals("none"))
                {
                    Glide.with(convertView.getContext()).load(R.drawable.default_companyimg).dontAnimate().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).bitmapTransform(new CropCircleTransformation(convertView.getContext())).thumbnail(0.1f).into(holder.View_ad_img);

                }
                else
                {
                    Glide.with(convertView.getContext()).load(data.GET_ad_img()).dontAnimate().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).bitmapTransform(new CropCircleTransformation(convertView.getContext())).thumbnail(0.1f).into(holder.View_ad_img);
                }

                /*---광고 이미지 클릭 리스너 ---*/
                holder.View_ad_img.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(data.GET_ad_img().toString().equals("none"))
                        {
                            Toast.makeText(Main.MainContext,"업체 이미지가 없습니다.",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            ((Main) Main.MainContext).ImageViewer(data.GET_ad_img(), data.GET_user_name(), data.GET_user_age());
                        }
                    }
                });
                // 광고 이미지 뷰어 END

                // 광고 타이틀 텍스트
                holder.View_ad_title.setText(data.GET_ad_name());
                // 광고 타이틀 텍스트 END


                // 광고 작성자 성별 색, 닉네임 & 나이
                if(data.GET_user_sex().toString().equals("여자"))
                {
                    holder.View_ad_nameandage.setTextColor(convertView.getResources().getColor(R.color.girl));
                }
                else
                {
                    holder.View_ad_nameandage.setTextColor(convertView.getResources().getColor(R.color.man));
                }

                holder.View_ad_nameandage.setText(data.GET_user_name()+" ("+data.GET_user_age()+"세)");
                // 광고 작성자 성별 색, 닉네임 & 나이 END

                // 광고 업종
                holder.View_ad_adsector.setText(data.GET_ad_sector());
                // 광고 업종 END

                //광고 구하는 성별
                holder.View_ad_sex.setText(data.GET_ad_sex());
                //광고 구하는 성별 END

                //급여 타입
                holder.View_ad_pay.setText(data.GET_ad_pay());
                //급여 타입 END

                //급여 금액
                holder.View_ad_payvalue.setText(data.GET_ad_payvalue()+"원");
                //급여 금액 END

                // 시도
                holder.View_ad_adress1.setText(data.GET_ad_adress1());
                // 시도 END

                // 군구
                holder.View_ad_adress2.setText(data.GET_ad_adress2());
                // 군구 END

                //거리
                if(data.GET_ad_type().toString().equals("premium"))
                {
                    Tamp_location = Integer.parseInt(data.GET_ad_location().toString());

                    if(Tamp_location > 400)
                    {
                        holder.View_ad_loaction.setText("???km");
                    }
                    else
                    {
                        holder.View_ad_loaction.setText(data.GET_ad_location() + "km");
                    }

                    if (Tamp_location < 30)
                    {
                        holder.View_ad_loaction.setTextColor(convertView.getResources().getColor(R.color.km10));
                    }
                    else
                    {
                        if (Tamp_location < 60)
                        {
                            holder.View_ad_loaction.setTextColor(convertView.getResources().getColor(R.color.km50));
                        }
                        else
                        {
                            holder.View_ad_loaction.setTextColor(convertView.getResources().getColor(R.color.km100));
                        }
                    }
                }
                else
                {
                    holder.View_ad_location_panel.setVisibility(View.GONE);
                }
                //거리 END

                // 유저 패널 및 작성자 패널
                if(!data.GET_deviceID().toString().equals(AppInfo.MY_LOGINID))
                {
                    holder.View_user_panel.setVisibility(View.VISIBLE);
                    holder.View_my_panel.setVisibility(View.GONE);
                }
                else
                {
                    holder.View_user_panel.setVisibility(View.GONE);
                    holder.View_my_panel.setVisibility(View.VISIBLE);
                }
                // 유저 패널 및 작성자 패널 END

                // 업체 상세 보기 클릭
                holder.View_user_ad_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        // 광고글 식별 아이디로 Intent
                        ((Main) Main.MainContext).company_detail(data.GET_adidx(),data.GET_deviceID(), data.GET_ad_type(), data.GET_ad_sex(), data.GET_user_name(), data.GET_user_age(), data.GET_ad_location());
                    }
                });
                // 업체 상세 보기 클릭 END

                // 업체 쪽지보내기
                holder.View_user_ad_sand_massage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if(AppInfo.MY_TYPE.toString().equals("user"))
                        {
                            // 광고글 작성자 식별 아이디로 Intent
                            ((Main) Main.MainContext).sand_massage_company(data.GET_deviceID(), data.GET_ad_type(), data.GET_ad_sex(), data.GET_user_name(), data.GET_user_age(), data.GET_ad_location());
                        }
                        else
                        {
                            Toast.makeText(Main.MainContext,"업체 쪽지는 구직자만 보낼 수 있습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                // 업체 쪽지보내기 END


            }


            return convertView;

        }
        // 뷰 홀더
        public class ViewHolder
        {
            LinearLayout    View_ad_type;
            LinearLayout    View_ad_type_margin;
            ImageView       View_ad_img;
            TextView        View_ad_title;
            TextView        View_ad_nameandage;
            TextView        View_ad_adsector;
            TextView        View_ad_sex;
            TextView        View_ad_pay;
            TextView        View_ad_payvalue;
            TextView        View_ad_adress1;
            TextView        View_ad_adress2;
            TextView        View_ad_loaction;
            LinearLayout    View_ad_location_panel;

            LinearLayout    View_user_panel;
            LinearLayout    View_my_panel;
            LinearLayout    View_user_ad_detail;
            LinearLayout    View_user_ad_sand_massage;
            LinearLayout    View_user_ad_fix;
            LinearLayout    View_user_ad_delete;



        }
    }


    //---------------------------------------------------------- 토크 리스트 만들기--------------------------------------//
    private void Make_Talk()
    {
        if(listItem.size() == 0)
        {
            return;
        }

        list = (ListView) in_layout.findViewById(R.id.list_tab1);  // 리스트 레이아웃 부분 설정

        customAdapter = new CustomAdapter(Main.MainContext, R.id.list_item, listItem);
        list.setAdapter(customAdapter);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
                LastTalkVisibleFlag = ((totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount));
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
                //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻

                // Log.i("LIST","1 : "+listItem.size() +"/ 2 : "+Last_ListIndex + "             /   "+scrollState);
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && LastTalkVisibleFlag)
                {

                    Toast.makeText(Main.MainContext,"바닥입니다.",Toast.LENGTH_SHORT).show();
                    //TODO 화면이 바닦에 닿을때 처리
                }
            }

        });
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
