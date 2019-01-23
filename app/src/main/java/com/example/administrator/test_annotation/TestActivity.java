package com.example.administrator.test_annotation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.test_annotation.Utils.BindViewUtils;
import com.example.my_annotation.ViewSeeker1;

public class TestActivity extends AppCompatActivity {

    @ViewSeeker1(R.id.tv_title)
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        BindViewUtils.register(this);

        textView.setText("我是第一行");

    }
}