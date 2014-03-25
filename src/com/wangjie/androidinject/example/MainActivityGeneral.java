package com.wangjie.androidinject.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.wangjie.androidinject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivityGeneral extends Activity implements View.OnClickListener, View.OnLongClickListener, AdapterView.OnItemClickListener{

    private Button insertBtn;

    private Button queryBtn;

    private Button updateBtn;

    private ListView listView;

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        long start = System.currentTimeMillis();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        context = this;

        insertBtn = (Button) findViewById(R.id.insertBtn);
        insertBtn.setOnClickListener(this);
        insertBtn.setOnLongClickListener(this);

        queryBtn = (Button) findViewById(R.id.queryBtn);
        queryBtn.setOnClickListener(this);
        queryBtn.setOnLongClickListener(this);

        updateBtn = (Button) findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(this);
        updateBtn.setOnLongClickListener(this);

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        for(int i = 0; i < 10; i++){
            map = new HashMap<String, String>();
            map.put("title", "item_" + i);
            list.add(map);
        }

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        SimpleAdapter adapter = new SimpleAdapter(context, list, R.layout.list_item, new String[]{"title"}, new int[]{R.id.list_item_title_tv});
        listView.setAdapter(adapter);
        Log.d("MainActivityGeneral", this.getClass().getSimpleName() + ": " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void onClick(View view) {
        if(view instanceof Button){
            Toast.makeText(context, "onClick: " + ((Button)view).getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "onItemClick: " + ((Map<String, String>)adapterView.getAdapter().getItem(i)).get("title"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View view) {
        if(view instanceof Button){
            Toast.makeText(context, "onLongClickCallback: " + ((Button)view).getText(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }


}
