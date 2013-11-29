package com.wangjie.androidinject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.wangjie.androidinject.annotation.annotations.InitLayout;
import com.wangjie.androidinject.annotation.annotations.InitView;

@InitLayout(R.layout.main)
public class MainActivity extends BaseActivity {

    @InitView(id = R.id.btn1, clickMethod = "onClickCallback")
    private Button btn1;

    @InitView(id = R.id.btn2, clickMethod = "onClickCallback")
    private Button btn2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onClickCallback(View view){
        if(view instanceof Button){
            Toast.makeText(context, ((Button)view).getText()+ "", Toast.LENGTH_SHORT).show();
        }

    }




}
