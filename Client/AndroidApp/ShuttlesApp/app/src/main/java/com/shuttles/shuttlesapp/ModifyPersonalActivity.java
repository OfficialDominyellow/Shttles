package com.shuttles.shuttlesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ModifyPersonalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_personal_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_modify_personal);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_12dp); // your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

    }
}
