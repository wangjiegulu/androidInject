package com.wangjie.androidinject.example;

import android.app.AlarmManager;
import android.content.Intent;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.wangjie.androidinject.R;
import com.wangjie.androidinject.annotation.annotations.base.*;
import com.wangjie.androidinject.annotation.annotations.dimens.AIScreenSize;
import com.wangjie.androidinject.annotation.annotations.net.AINetWorker;
import com.wangjie.androidinject.annotation.core.net.RetMessage;
import com.wangjie.androidinject.annotation.core.orm.AIDbExecutor;
import com.wangjie.androidinject.annotation.present.AIActivity;
import com.wangjie.androidinject.annotation.util.Params;
import com.wangjie.androidinject.example.database.DbExecutor;
import com.wangjie.androidinject.example.model.Person;
import com.wangjie.androidinject.example.model.UploadFile;
import com.wangjie.androidinject.example.model.User;
import com.wangjie.androidinject.example.net.PersonWorker;

import java.io.File;
import java.util.*;

//@AIFullScreen
//@AINoTitle
@AILayout(R.layout.main)
public class MainActivity extends AIActivity{

    @AIView(id = R.id.insertBtn, clickMethod = "onClickCallback", longClickMethod = "onLongClickCallback")
    private Button insertBtn;

    @AIView(clickMethod = "onClickCallback", longClickMethod = "onLongClickCallback")
    private Button queryBtn;

//    @AIView(id = R.id.btn3)
//    private Button btn3;

//    @AIView(id = R.id.listView, itemClickMethod = "onItemClickCallback", itemLongClickMethod = "onItemLongClickCallbackForListView")
    @AIView(R.id.listView)
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
                try{
//                RetMessage<Person> retMsg = personWorker.getPersonsForGet("a1", "b1", "c1");
                RetMessage<Person> retMsg = personWorker.getPersonsForGet2(new Params().add("aa", "a1").add("bb", "b1").add("cc", "c1"));

//                    RetMessage<Person> retMsg = personWorker.getPersonsForPost2(new Params().add("aa", "a1").add("bb", "b1").add("cc", "c1"));
                    System.out.println("getPersonsForGet2: " + retMsg.getList().toString());
                }catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonStr = personWorker.getPersonsForGetToString(new Params().add("aa", "a1").add("bb", "b1").add("cc", "c1"));
                    System.out.println("getPersonsForGetToString: " + jsonStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    // 上传多个文件
                    /*
                    List<File> files = new ArrayList<File>();
                    files.add(new File("/storage/emulated/0/DCIM/Camera/20140130_132710.jpg"));
                    files.add(new File("/storage/emulated/0/DCIM/Camera/20140130_132559.jpg"));
                    files.add(new File("/storage/emulated/0/DCIM/Camera/20140130_132533.jpg"));
                    files.add(new File("/storage/emulated/0/DCIM/Camera/20140130_132508.jpg"));
                    RetMessage<UploadFile> retMsg = personWorker.uploadFile(files);
                    System.out.println(retMsg.getList().toString());
                    */
                    // 上传单个文件
                    RetMessage<UploadFile> retMsg = personWorker.uploadFile2(new File("/storage/emulated/0/DCIM/Camera/20140130_132710.jpg"));
                    System.out.println(retMsg.getList().toString());

                }catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        }).start();



        userExecutor = new DbExecutor<User>(context);
    }

    AIDbExecutor<User> userExecutor = null;
    User dbUser = null;
    List<User> users = null;
    Random rd = new Random();
    @AIClick({R.id.deletebtn, R.id.updateBtn})
    public void onClickCallback(View view) throws Exception{
        if(view instanceof Button){
            Toast.makeText(context, "onClickCallback: " + ((Button)view).getText(), Toast.LENGTH_SHORT).show();
        }
        switch(view.getId()){
            case R.id.insertBtn: // 插入user对象到user表
                dbUser = new User("wangjie" + rd.nextInt(10000), String.valueOf(rd.nextInt(10000) + 10000), System.currentTimeMillis(), rd.nextInt(80) + 120, rd.nextInt(80) + 120, "aaaa");
                userExecutor.executeSave(dbUser);
                break;

            case R.id.queryBtn: // 查询user表
                users = userExecutor.executeQuery("select * from user where uid > ?", new String[]{"4"}, User.class);
                System.out.println("[queryBtn]users: " + users);
                break;

            case R.id.deletebtn: // 删除user表的一条数据
                if(null == users || users.size() <= 0){
                    break;
                }
                userExecutor.executeDelete(users.get(0));
                break;

            case R.id.updateBtn: // 更新user表的一条数据
                if(null == users || users.size() <= 0){
                    break;
                }
                User user = users.get(0);
                user.setUsername(user.getUsername().startsWith("wangjie") ? "jiewang" + rd.nextInt(10000) : "wangjie" + rd.nextInt(10000));
                user.setPassword(user.getPassword().startsWith("123456") ? "abcdef" : "123456");
                user.setCreatemillis(System.currentTimeMillis());
                user.setHeight(rd.nextInt(80) + 120);
                user.setWeight(rd.nextInt(80) + 120);
                user.setNotCol("bbb");
                userExecutor.executeUpdate(user, null, new String[]{"createmillis"});
                break;

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

    @AIClick({R.id.toFragmentBtn})
    public void onClickCallbackForBtn3(View view){
        if(view instanceof Button){
            Toast.makeText(context, "onClickForUpdateBtn: " + ((Button)view).getText(), Toast.LENGTH_SHORT).show();
        }

        if(view.getId() == R.id.toFragmentBtn){
            startActivity(new Intent(context, SecendActivity.class));
        }


    }

    @AILongClick({R.id.updateBtn})
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
