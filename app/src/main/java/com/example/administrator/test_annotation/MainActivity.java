package com.example.administrator.test_annotation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.test_annotation.Utils.BindViewUtils;
import com.example.administrator.test_annotation.Utils.UseAnnotation;
import com.example.my_annotation.*;

public class MainActivity extends AppCompatActivity {

    @ViewSeeker1(R.id.tv_show)
    TextView textView;

    @ViewSeeker1(R.id.tv_show1)
    TextView textView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //UseAnnotation.findView(this);
        BindViewUtils.register(this);
        textView.setText("我是第一行");
        textView1.setText("我是第二行");
    }


    @ClickSeeker(R.id.tv_show)
    public void showHit(){
        Toast.makeText(this,"点击了第一行",Toast.LENGTH_SHORT).show();
    }


}