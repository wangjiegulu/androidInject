package com.wangjie.androidinject;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.wangjie.androidinject.annotation.annotations.AIFullScreen;
import com.wangjie.androidinject.annotation.annotations.AILayout;
import com.wangjie.androidinject.annotation.annotations.AINoTitle;
import com.wangjie.androidinject.annotation.present.AISupportFragmentActivity;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 13-12-4
 * Time: 下午4:06
 */
@AILayout(R.layout.secend)
public class SecendActivity extends AISupportFragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, new FragmentA());
        ft.commit();


    }



}
