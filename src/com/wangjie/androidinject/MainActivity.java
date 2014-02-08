package com.wangjie.androidinject;

import android.app.AlarmManager;
import android.content.Intent;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.wangjie.androidinject.annotation.annotations.base.*;
import com.wangjie.androidinject.annotation.annotations.dimens.AIScreenSize;
import com.wangjie.androidinject.annotation.annotations.net.AINetWorker;
import com.wangjie.androidinject.annotation.core.net.RetMessage;
import com.wangjie.androidinject.annotation.core.net.NetInvoHandler;
import com.wangjie.androidinject.annotation.present.AIActivity;
import com.wangjie.androidinject.model.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@AIFullScreen
//@AINoTitle
@AILayout(R.layout.main)
public class MainActivity extends AIActivity{

    @AIView(id = R.id.btn1, clickMethod = "onClickCallback", longClickMethod = "onLongClickCallback")
    private Button btn1;

    @AIView(clickMethod = "onClickCallback", longClickMethod = "onLongClickCallback")
    private Button btn2;

//    @AIView(id = R.id.btn3)
//    private Button btn3;

//    @AIView(id = R.id.listView, itemClickMethod = "onItemClickCallback", itemLongClickMethod = "onItemLongClickCallbackForListView")
    @AIView(id = R.id.listView)
    private ListView listView;

    @AIBean
    private Person person;

    @AISystemService
    private AlarmManager alarmManager;
    @AISystemService
    private LocationManager locationManager;
    @AISystemService
    private LayoutInflater inflater;
    @AIScreenSize
    private Point sSize;
    @AINetWorker
    private PersonWorker personWorker;

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

        person.setName("wangjie");
        person.setAge(23);
        System.out.println(person.toString());

        System.out.println("alarmManager: " + alarmManager + ", locationManager: " + locationManager + ", inflater: " + inflater);

        System.out.println("screen size --> width: " + sSize.x + ", height: " + sSize.y);

        new Thread(new Runnable() {
            @Override
            public void run() {
                RetMessage<Person> retMsg = personWorker.getPersonsForPost("a1", "b1", "c1");
                System.out.println(retMsg.getList().toString());
            }
        }).start();

    }


    public void onClickCallback(View view){
        if(view instanceof Button){
            Toast.makeText(context, "onClickCallback: " + ((Button)view).getText(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onLongClickCallback(View view){
        if(view instanceof Button){
            Toast.makeText(context, "onLongClickCallback: " + ((Button)view).getText(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onItemClickCallback(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "onItemClickCallback: " + ((Map<String, String>)adapterView.getAdapter().getItem(i)).get("title"), Toast.LENGTH_SHORT).show();
    }

    @AIClick({R.id.btn3, R.id.toFragmentBtn})
    public void onClickCallbackForBtn3(View view){
        if(view instanceof Button){
            Toast.makeText(context, "onClickForBtn3: " + ((Button)view).getText(), Toast.LENGTH_SHORT).show();
        }

        if(view.getId() == R.id.toFragmentBtn){
            startActivity(new Intent(context, SecendActivity.class));
        }

    }

    @AILongClick({R.id.btn3})
    public void onLongClickCallbackForBtn3(View view){
        if(view instanceof Button){
            Toast.makeText(context, "onLongClickCallbackForBtn3: " + ((Button)view).getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @AIItemClick({R.id.listView})
    public void onItemClickCallbackForListView(AdapterView<?> adapterView, View view, int i, long l){
        Toast.makeText(context, "onItemClickCallbackForListView: " + ((Map<String, String>)adapterView.getAdapter().getItem(i)).get("title"), Toast.LENGTH_SHORT).show();
    }

    @AIItemLongClick(R.id.listView)
    public boolean onItemLongClickCallbackForListView(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "onItemLongClickCallbackForListView: " + ((Map<String, String>)adapterView.getAdapter().getItem(i)).get("title"), Toast.LENGTH_SHORT).show();
        return true;
    }



}
