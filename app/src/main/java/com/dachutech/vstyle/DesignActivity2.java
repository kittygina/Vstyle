package com.dachutech.vstyle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.dachutech.vstyle.R.id.btn_cam;

public class DesignActivity2 extends AppCompatActivity {
    @InjectView(R.id.btn_cam)
    Button btn_cam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design2);
        ButterKnife.inject(this);
        btn_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), Camera2Activity.class);
                startActivity(intent);

            }
        });
    }

}
