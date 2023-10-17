package com.demo.apt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.demo.annotation.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnifeUtils.bind(this);
        if (tvContent!=null){
            tvContent.setText("Hello APT");
        }

    }
}