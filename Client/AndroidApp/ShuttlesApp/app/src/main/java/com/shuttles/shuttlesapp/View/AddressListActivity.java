package com.shuttles.shuttlesapp.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import com.shuttles.shuttlesapp.R;
import com.shuttles.shuttlesapp.vo.AddressVO;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by domin on 2018-03-22.
 */

public class AddressListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_list_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_address_list);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_12dp); // your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        //init Realm
        Realm.init(getApplicationContext());

        ImageView ivCart = (ImageView) findViewById(R.id.iv_add_address);
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "주소록추가", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AddressNewActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        final AddressListViewAdapter addressListViewAdapter = new AddressListViewAdapter();
        ListViewCompat lvAddressList = (ListViewCompat) findViewById(R.id.lv_address_list);

        lvAddressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AddressVO addressVO = (AddressVO) adapterView.getItemAtPosition(i);

                int id = addressVO.getId();

                //Toast.makeText(getApplicationContext(), "id : " + id + " pos : " + i, Toast.LENGTH_SHORT).show();
            }
        });

        lvAddressList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AddressVO addressVO = (AddressVO) parent.getItemAtPosition(position);

                int addressVOId = addressVO.getId();

                //Toast.makeText(getApplicationContext(), "LONG id : " + position + " pos : " + addressVOId, Toast.LENGTH_SHORT).show();
                showAlertDialog(addressVOId);

                return false;
            }
        });
        Realm realm = Realm.getDefaultInstance();
        RealmResults<AddressVO> results = realm.where(AddressVO.class).findAll();

        for(AddressVO e : results){
            addressListViewAdapter.addItem(e.getId(), e.getZipcode(), e.getAddress1(), e.getAddress2(), e.getAddressExtra(), e.getOrderComment()) ;
        }

        lvAddressList.setAdapter(addressListViewAdapter);
    }

    private void showAlertDialog(final int addressVOId){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("주소 삭제");
        alertDialog.setMessage("해당 주소를 주소록에서 삭제합니다.");
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Realm.init(getApplicationContext());
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<AddressVO> result = realm.where(AddressVO.class).equalTo("id",addressVOId).findAll();
                        result.deleteAllFromRealm();
                    }
                });
                realm.commitTransaction();

                //refresh activity
                finish();
                startActivity(getIntent());
            }
        });
        alertDialog.show();
    }
}

class AddressListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<AddressVO> listViewItemList = new ArrayList<AddressVO>() ;

    // AddressListViewAdapter 생성자
    public AddressListViewAdapter() {

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
            convertView = inflater.inflate(R.layout.address_list_row, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView tvZipcode = (TextView) convertView.findViewById(R.id.tv_zipcode);
        TextView tvFullAddress = (TextView) convertView.findViewById(R.id.tv_full_address);
        TextView tvAddressExtra = (TextView) convertView.findViewById(R.id.tv_address_extra);
        TextView tvOrderComment = (TextView) convertView.findViewById(R.id.tv_order_comment);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        AddressVO addressVO = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        tvZipcode.setText(addressVO.getZipcode());
        tvFullAddress.setText(addressVO.getAddress1() + " " + addressVO.getAddress2());
        tvAddressExtra.setText(addressVO.getAddressExtra());
        tvOrderComment.setText(addressVO.getOrderComment());

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
    public void addItem(int id, String zipcode, String address1, String address2, String addressExtra, String orderComment) {
        AddressVO item = new AddressVO(id, zipcode, address1, address2, addressExtra, orderComment);

        listViewItemList.add(item);
    }
}
