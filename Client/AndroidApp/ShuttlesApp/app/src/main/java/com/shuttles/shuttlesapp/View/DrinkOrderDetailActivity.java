package com.shuttles.shuttlesapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shuttles.shuttlesapp.GlobalApplication;
import com.shuttles.shuttlesapp.R;
import com.shuttles.shuttlesapp.vo.DrinkListVO;

public class DrinkOrderDetailActivity extends AppCompatActivity {
    private DrinkListVO drinkListVO = null;
    private TextView drinkPriceTextView = null;
    private TextView drinkNameTextView = null;
    private TextView drinkDescription = null;
    private ImageView drinkImageView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_order_detail_layout);

        int num = (int) getIntent().getExtras().getInt("DRINK_VO");
        drinkListVO = GlobalApplication.drinkList.get(num);

        if(drinkListVO!=null){
            drinkImageView = (ImageView)findViewById(R.id.drink_img);
            drinkImageView.setImageDrawable(drinkListVO.getImg());

            drinkPriceTextView = (TextView)findViewById(R.id.drink_price);
            drinkPriceTextView.setText(drinkListVO.getPrice());

            drinkNameTextView = (TextView)findViewById(R.id.drink_name);
            drinkNameTextView.setText(drinkListVO.getName());

            drinkDescription = (TextView)findViewById(R.id.drink_description);
            drinkDescription.setText(drinkListVO.getDescription());
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_drink_order_detail);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_12dp); // your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        ImageView ivCart = (ImageView) findViewById(R.id.iv_cart_in_drink_order_detail);


        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "장바구니", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CartActivityV2.class);
                startActivity(intent);
            }
        });

        Button btnOrderWithFood = (Button)findViewById(R.id.btn_order_with_food);
        btnOrderWithFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "스페셜푸드와 함께", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(intent);
            }
        });
    }
}
