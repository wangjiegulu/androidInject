package com.wangjie.androidinject.example;

import android.app.AlarmManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.wangjie.androidinject.R;
import com.wangjie.androidinject.annotation.annotations.base.*;
import com.wangjie.androidinject.annotation.present.AISupportFragment;
import com.wangjie.androidinject.example.model.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-12-4
 * Time: 下午4:37
 */
@AILayout(R.layout.fragment_a)
public class FragmentA extends AISupportFragment{

    @AIView
    private Button fragmentABtn1;

    @AIView
    private GridView fragmentGv;

    @AIBean
    private Person person;

    @AISystemService
    private AlarmManager alarmManager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        for(int i = 0; i < 10; i++){
            map = new HashMap<String, String>();
            map.put("title", "fragment_item_" + i);
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(context, list, R.layout.list_item, new String[]{"title"}, new int[]{R.id.list_item_title_tv});
        fragmentGv.setAdapter(adapter);

        person.setName("androidInject");
        person.setAge(1);
        System.out.println(person.toString());

        System.out.println("alarmManager: " + alarmManager);

    }

    @AIClick(R.id.fragmentABtn1)
    private void btnOnclick(View view){
        if(view instanceof Button){
            Toast.makeText(context, "btnOnclick: " + ((Button)view).getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @AILongClick(R.id.fragmentABtn1)
    private void btnOnLongClick(View view){
        if(view instanceof Button){
            Toast.makeText(context, "btnOnLongClick: " + ((Button)view).getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @AIItemClick(R.id.fragmentGv)
    private void gvOnItemClick(AdapterView<?> adapterView, View view, int i, long l){
        Toast.makeText(context, "gvOnItemClick: " + ((Map<String, String>)adapterView.getAdapter().getItem(i)).get("title"), Toast.LENGTH_SHORT).show();
    }

    @AIItemLongClick(R.id.fragmentGv)
    private void gvOnItemLongClick(AdapterView<?> adapterView, View view, int i, long l){
        Toast.makeText(context, "gvOnItemLongClick: " + ((Map<String, String>)adapterView.getAdapter().getItem(i)).get("title"), Toast.LENGTH_SHORT).show();
    }



}
