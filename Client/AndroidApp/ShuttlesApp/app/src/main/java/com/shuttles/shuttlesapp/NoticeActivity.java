package com.shuttles.shuttlesapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shuttles.shuttlesapp.vo.NoticeListVO;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_drink_order_detail);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_12dp); // your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        ListViewCompat lvNotice = (ListViewCompat) findViewById(R.id.lv_notice);
        final NoticeListViewAdapter noticeListViewAdapter = new NoticeListViewAdapter();

        lvNotice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NoticeListVO noticeListVO = (NoticeListVO) adapterView.getItemAtPosition(i);

                String title = noticeListVO.getTitle();
                String content = noticeListVO.getContent();

                Toast.makeText(getApplicationContext(), "titlie : " + title + ", content : " + content + ", pos : " + i, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), NoticeDetailActivity.class);
                startActivity(intent);
            }
        });

        //add dummy data
        for(int i=0; i<15; i++){
            noticeListViewAdapter.addItem("제목제목제목" + i, "내용냉녀욘애뇨내뇽........" + i);
        }

        lvNotice.setAdapter(noticeListViewAdapter);
    }
}

class NoticeListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<NoticeListVO> listViewItemList = new ArrayList<NoticeListVO>() ;

    // NoticeListViewAdapter 생성자
    public NoticeListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.notice_list_row, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView tvNoticeTitle = (TextView) convertView.findViewById(R.id.tv_notice_title) ;
        TextView tvNoticeContent = (TextView) convertView.findViewById(R.id.tv_notice_content) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        NoticeListVO noticeListVO = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        tvNoticeTitle.setText(noticeListVO.getTitle());
        tvNoticeContent.setText(noticeListVO.getContent());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String title, String content) {
        NoticeListVO item = new NoticeListVO();

        item.setTitle(title);
        item.setContent(content);

        listViewItemList.add(item);
    }
}