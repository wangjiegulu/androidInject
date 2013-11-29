package com.wangjie.androidinject;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.wangjie.androidinject.annotation.annotations.InitLayout;
import com.wangjie.androidinject.annotation.annotations.InitView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InitLayout(R.layout.main)
public class MainActivity extends BaseActivity {

    @InitView(id = R.id.btn1, clickMethod = "onClickCallback", longClickMethod = "onLongClickCallback")
    private Button btn1;

    @InitView(id = R.id.btn2, clickMethod = "onClickCallback", longClickMethod = "onLongClickCallback")
    private Button btn2;

    @InitView(id = R.id.listView, itemClickMethod = "onItemClickCallback")
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        for(int i = 0; i < 10; i++){
            map = new HashMap<String, String>();
            map.put("title", "item_" + i);
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(context, list, R.layout.list_item, new String[]{"title"}, new int[]{R.id.list_item_title_tv});
        listView.setAdapter(adapter);

    }

    @Override
    public void onClickCallback(View view){
        if(view instanceof Button){
            Toast.makeText(context, "onClickCallback: " + ((Button)view).getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLongClickCallback(View view){
        if(view instanceof Button){
            Toast.makeText(context, "onLongClickCallback: " + ((Button)view).getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClickCallback(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "onItemClickCallback: " + ((Map<String, String>)adapterView.getAdapter().getItem(i)).get("title"), Toast.LENGTH_SHORT).show();
    }



}
