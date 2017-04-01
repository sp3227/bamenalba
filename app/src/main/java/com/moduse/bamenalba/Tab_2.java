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

public class Tab_2 extends Activity
{
    ProgressDialog loading;

    AppInfo appInfo;

    public LinearLayout in_layout;
    public LayoutInflater Inflater;


    ArrayList<Data_Joperlist> listItem = new ArrayList<Data_Joperlist>();
    private ListView list = null;
    private CustomAdapter customAdapter = null;

    // 페이지 넘머 기록
    int Last_ListIndex = 0;
    boolean LastTalkVisibleFlag = false;

    //임시 거리
    int Tamp_location;


    Tab_2()
    {
        loading = new ProgressDialog(Main.MainContext);
        InitShow();

        Inflater = ((Main) Main.MainContext).getLayoutInflater();
        Inflater = (LayoutInflater) Main.MainContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        in_layout = (LinearLayout) Inflater.inflate(R.layout.tab_2, null);
    }


    //------------------------------------------------------- 인재 불러오기------------------------------------------//
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

//------------------------------- 임시 데이터 --------------//
    public void Tamps()
    {
        String idx                  = "1";

        String user_deviceID        = "4503740579";
        String user_proimg          = "http://imgnews.naver.com/image/469/2017/03/06/629dd411639e44d89fbe77fa8f705e8e_99_20170306234302.jpg";
        String user_sex             = "여자";
        String user_name            = "도봉순";
        String user_age             = "22";
        String user_ment            = "안전한 일자리 구하고 싶어요~";
        String user_loaction        = "5";

        listItem.add(new Data_Joperlist(idx, user_deviceID, user_proimg, user_sex, user_name, user_age, user_ment, user_loaction));

        idx                  = "2";

        user_deviceID        = "567567567";
        user_proimg          = "none";
        user_sex             = "남자";
        user_name            = "홍길동";
        user_age             = "26";
        user_ment            = "안전한 일자리 구하고 싶어요~";
        user_loaction        = "56";

        listItem.add(new Data_Joperlist(idx, user_deviceID, user_proimg, user_sex, user_name, user_age, user_ment, user_loaction));


        Make_Talk();
    }


    //------------------------------------------------------- 리스트 어댑터 함수----------------------------------//

    private class CustomAdapter extends ArrayAdapter<Data_Joperlist>
    {
        private LayoutInflater m_inflager = null;
        private ArrayList<Data_Joperlist> items;
        private int m_recource_id;
        CustomAdapter.ViewHolder holder;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<Data_Joperlist> objects)
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
            final Data_Joperlist data = items.get(position);
            final int index = position;

            if(convertView == null)
            {
                convertView = m_inflager.inflate(R.layout.item_jophunterlist, parent,false);

                holder = new CustomAdapter.ViewHolder();

                //  view 에서 얻어 초기화
                holder.View_joperlist_img = (ImageView) convertView.findViewById(R.id.joperlist_img);
                holder.View_joperlist_nickname = (TextView) convertView.findViewById(R.id.joperlist_nickname);
                holder.View_joperlist_age = (TextView) convertView.findViewById(R.id.joperlist_age);
                holder.View_joperlist_loaction = (TextView) convertView.findViewById(R.id.joperlist_loaction);
                holder.View_joperlist_ment = (TextView) convertView.findViewById(R.id.joperlist_ment);
                holder.View_joperlist_btn_detail = (LinearLayout) convertView.findViewById(R.id.joperlist_btn_detail);
                holder.View_joperlist_btn_sand_massage = (LinearLayout) convertView.findViewById(R.id.joperlist_btn_sand_massage);

                convertView.setTag(holder);
            }
            else
            {
                holder = (CustomAdapter.ViewHolder) convertView.getTag();
            }

            if(data != null)
            {

                // 유저 이미지 뷰어
                if(data.GET_user_proimg().toString().equals("none"))
                {
                    if(data.GET_user_sex().toString().equals("여자"))
                    {
                        Glide.with(convertView.getContext()).load(R.drawable.default_icon_girl).dontAnimate().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).bitmapTransform(new CropCircleTransformation(convertView.getContext())).thumbnail(0.1f).into(holder.View_joperlist_img);
                    }
                    else
                    {
                        Glide.with(convertView.getContext()).load(R.drawable.default_icon_men).dontAnimate().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).bitmapTransform(new CropCircleTransformation(convertView.getContext())).thumbnail(0.1f).into(holder.View_joperlist_img);
                    }
                }
                else
                {
                    Glide.with(convertView.getContext()).load(data.GET_user_proimg()).dontAnimate().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).bitmapTransform(new CropCircleTransformation(convertView.getContext())).thumbnail(0.1f).into(holder.View_joperlist_img);
                }

                /*---유저 이미지 클릭 리스너 ---*/
                holder.View_joperlist_img.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(data.GET_user_proimg().toString().equals("none"))
                        {
                            Toast.makeText(Main.MainContext,"구직자 이미지가 없습니다.",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            ((Main) Main.MainContext).ImageViewer(data.GET_user_proimg(), data.GET_user_name(), data.GET_user_age());
                        }
                    }
                });
                // 유저 이미지 뷰어 END

                // 유저 작성자 성별 색, 닉네임 & 나이
                if(data.GET_user_sex().toString().equals("여자"))
                {
                    holder.View_joperlist_nickname.setTextColor(convertView.getResources().getColor(R.color.girl));
                    holder.View_joperlist_age.setTextColor(convertView.getResources().getColor(R.color.girl));
                }
                else
                {
                    holder.View_joperlist_nickname.setTextColor(convertView.getResources().getColor(R.color.man));
                    holder.View_joperlist_age.setTextColor(convertView.getResources().getColor(R.color.man));
                }
                // 유저 작성자 성별 색, 닉네임 & 나이 END

                //유저 닉네임
                holder.View_joperlist_nickname.setText(data.GET_user_name());

                //유저 나이
                holder.View_joperlist_age.setText("("+data.GET_user_age()+"세)");

                // 유저 거리
                holder.View_joperlist_loaction.setText(data.GET_user_loaction());

                Tamp_location = Integer.parseInt(data.GET_user_loaction().toString());

                if(Tamp_location > 400)
                {
                    holder.View_joperlist_loaction.setText("???km");
                }
                else
                {
                    holder.View_joperlist_loaction.setText(data.GET_user_loaction() + "km");
                }

                if (Tamp_location < 30)
                {
                    holder.View_joperlist_loaction.setTextColor(convertView.getResources().getColor(R.color.km10));
                }
                else
                {
                    if (Tamp_location < 60)
                    {
                        holder.View_joperlist_loaction.setTextColor(convertView.getResources().getColor(R.color.km50));
                    }
                    else
                    {
                        holder.View_joperlist_loaction.setTextColor(convertView.getResources().getColor(R.color.km100));
                    }
                }
            }

            //유저 멘트
            holder.View_joperlist_ment.setText(data.GET_user_ment());



            // 유저 디테일 클릭 리스너
            holder.View_joperlist_btn_detail.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    ((Main) Main.MainContext).joper_detail(data.GET_user_deviceID(), data.GET_user_sex(), data.GET_user_name(), data.GET_user_age(), data.GET_user_loaction());
                }
            });

            // 유저 쪽지 보내기 클릭 리스너
            holder.View_joperlist_btn_sand_massage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(AppInfo.MY_TYPE.toString().equals("company"))
                    {
                        // 광고주가 -> 구직자에게 쪽지
                        ((Main) Main.MainContext).sand_massage(data.GET_user_deviceID(), "company", data.GET_user_sex(), data.GET_user_name(), data.GET_user_age(), data.GET_user_loaction());
                    }
                    else
                    {
                        Toast.makeText(Main.MainContext,"업체만 구직자에게 쪽지를 보낼수 있습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });


            return convertView;

        }
        // 뷰 홀더
        public class ViewHolder
        {
            ImageView    View_joperlist_img;
            TextView     View_joperlist_nickname;
            TextView     View_joperlist_age;
            TextView     View_joperlist_loaction;
            TextView     View_joperlist_ment;

            LinearLayout View_joperlist_btn_detail;
            LinearLayout View_joperlist_btn_sand_massage;

        }
    }


    //---------------------------------------------------------- 토크 리스트 만들기--------------------------------------//
    private void Make_Talk()
    {
        if(listItem.size() == 0)
        {
            return;
        }

        list = (ListView) in_layout.findViewById(R.id.list_tab2);  // 리스트 레이아웃 부분 설정

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
        loading.setMessage("인재를 불러오는 중입니다..");
    }
    public void SetmsgShow(String value)
    {
        loading.setMessage(value);
    }
    public void StartShow() {loading.show();}
    public void StopShow() {loading.dismiss();}
}
