androidInject
=============

###使用注解来简化android开发  <br/>(Use annotations inject to simplify the development of android)<br/>

###注意: <br/>
> 重要：需要添加Library[AndroidBucket项目](https://github.com/wangjiegulu/AndroidBucket)的支持（日志、线程、util等）
>
> 使用fragment的注解，需要android-support-v4.jar的支持（以兼容低版本）
>
> 使用网络请求的注解时，如果需要自动返回封装类，则需要[gson.jar](https://code.google.com/p/google-gson/downloads/list)的支持
>
> 使用文件上传的注解，需要[httpmime.jar](http://hc.apache.org/downloads.cgi)的支持

###Gadle
        compile 'com.github.wangjiegulu:AndroidInject:1.0.2'
###Maven
        <dependency>
                <groupId>com.github.wangjiegulu</groupId>
                <artifactId>AndroidInject</artifactId>
                <version>1.0.2</version>
        </dependency>

###例子1：Android注解<br/>
        @AIFullScreen
        @AINoTitle
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
                ThreadPool.go(new Runtask<Object, Object>() {
                    @Override
                    public Object runInBackground() {
                        try {
        //                RetMessage<Person> retMsg = personWorker.getPersonsForGet("a1", "b1", "c1");
                            RetMessage<Person> retMsg = personWorker.getPersonsForGet2(new Params().add("aa", "a1").add("bb", "b1").add("cc", "c1"));
        
        //                    RetMessage<Person> retMsg = personWorker.getPersonsForPost2(new Params().add("aa", "a1").add("bb", "b1").add("cc", "c1"));
                            System.out.println("getPersonsForGet2: " + retMsg.getList().toString());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return null;
                    }
                });
        
        
                ThreadPool.go(new Runtask<Object, Object>() {
                    @Override
                    public Object runInBackground() {
                        try {
                            String jsonStr = personWorker.getPersonsForGetToString(new Params().add("aa", "a1").add("bb", "b1").add("cc", "c1"));
                            System.out.println("getPersonsForGetToString: " + jsonStr);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
        
        
                ThreadPool.go(new Runtask<Object, Object>() {
                    @Override
                    public Object runInBackground() {
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
                        return null;
                    }
                });
        
        
        
                userExecutor = new DbExecutor<User>(context);
            }
        
            DbExecutor<User> userExecutor = null;
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
        
        

###例子2：网络请求注解<br/>
        @AIMapper("http://192.168.2.198:8080/HelloSpringMVC")
        public interface PersonWorker {
            @AIGet("/person/findPersons?aa=#{a3}&bb=#{b3}&cc=#{c3}")
            public RetMessage<Person> getPersonsForGet(@AIParam("a3")String a2, @AIParam("b3") String b2, @AIParam("c3") String c2) throws Exception;
        
            @AIPost("/person/findPersons")
            public RetMessage<Person> getPersonsForPost(@AIParam("aa")String a2, @AIParam("bb") String b2, @AIParam("cc") String c2) throws Exception;
        
            @AIGet(value = "/person/findPersons", connTimeout = 12345, soTimeout = 54321)
            public RetMessage<Person> getPersonsForGet2(Params params) throws Exception;
        
            @AIPost(value = "/person/findPersons", connTimeout = 30000, soTimeout = 25000)
            public RetMessage<Person> getPersonsForPost2(Params params) throws Exception;
        
            @AIUpload("/upload/uploadFiles")
            public RetMessage<UploadFile> uploadFile(List<File> files) throws Exception;
        
            @AIUpload("/upload/uploadFiles")
            public RetMessage<UploadFile> uploadFile2(File file) throws Exception;
        
            @AIGet(value = "/person/findPersons", connTimeout = 12345, soTimeout = 54321)
            public String getPersonsForGetToString(Params params) throws Exception;
        
        }

###例子3：数据表映射注解<br/>
        @AITable
        public class User implements Serializable{
            @AIColumn
            @AIPrimaryKey(insertable = false)
            private int uid;
            @AIColumn("username")
            private String username;
            @AIColumn
            private String password;
            @AIColumn
            private long createmillis;
            @AIColumn
            private float height;
            @AIColumn
            private double weight;
        
            private String notCol;
        
            public User() {
            }
        
            public User(String username, String password, long createmillis, float height, double weight, String notCol) {
                this.username = username;
                this.password = password;
                this.createmillis = createmillis;
                this.height = height;
                this.weight = weight;
                this.notCol = notCol;
            }
            // ... getter/setter
        }


###可用注解(Annotations): <br/>
###
        @AINoTitle: 类注解, 只适用于Activity(需继承于AIActivity), 设置Activity不显示Title

        @AIFullScreen: 类注解, 只适用于Activity(需继承于AIActivity), 设置Activity全屏

        @AILayout: 类注解
            value[int]: 用于设置该Activity的布局 ---- setContentView(resId);


        @AIView: 属性注解
            value[int]: 用于绑定控件 ---- findViewById(resId);(default identifier[R.id.{field name}] if did not set id)
            id[int]: 同value,如果两个都设置，则使用value值
            clickMethod[String]: 用于设置控件点击事件的回调方法, 可选, 方法名称任意, 参数必须为(View view)
            longClickMethod[String]: 用于设置控件长按的回调方法, 可选, 方法名任意, 参数必须为(View view)
            itemClickMethod[String]: 用于设置控件item点击的回调方法, 可选, 方法名任意, 参数必须为(AdapterView, View, int, long)
            itemLongClickMethod[String]: 用于设置控件item长按的回调方法, 可选, 方法名任意, 参数必须为(AdapterView, View, int, long)

        @AIBean: 属性注解, 为该属性生成一个对象并注入, 该对象必须有个默认的不带参数的构造方法

        @AISystemService: 属性注解，为该属性注入系统服务对象


        @AIClick: 方法注解
            value[int[], 所要绑定控件的id]: 用于绑定控件点击事件的回调方法, 方法名称任意, 参数必须为(View view)

        @AIItemClick: 方法注解
            value[int[], 所要绑定控件的id]: 用于绑定控件item点击事件的回调方法, 方法名称任意, 参数必须为(AdapterView, View, int, long)

        @AILongClick: 方法注解
            value[int[], 所要绑定控件的id]: 用于绑定控件长按事件的回调方法, 方法名称任意, 参数必须为(View view)

        @AIItemLongClick: 方法注解
            value[int[], 所要绑定控件的id]: 用于绑定控件item长按事件的回调方法, 方法名称任意, 参数必须为(AdapterView, View, int, long)

        @AIScreenSize: 属性注解
            用于注入当前设备的屏幕大小（宽高）

        @AIMapper：类注解，使用在NetWorker接口上面
            value：value值可以自动拼接在该接口中声明的方法url上面

        @AIGet: 方法注解
            value[String, 所要请求的url]：表示以GET来请求url
            connTimeout[int, 连接超时时间]：连接一个url的连接等待时间
            soTimeout[int, response返回超时时间]：连接上一个url，获取response的返回等待时间

        @AIPost: 方法注解
            value[String, 所要请求的url]：表示以Post来请求url
            connTimeout[int, 连接超时时间]：连接一个url的连接等待时间
            soTimeout[int, response返回超时时间]：连接上一个url，获取response的返回等待时间

        @AIParam: 方法参数注解
            value[String, 请求的参数别名]：注入@AIGet或@AIPost注解方法的请求参数

        @AINetWorker: 属性注解
            注入网络请求服务

        @AIUpload: 方法注解，用于上传文件到服务器（支持多文件上传）
            value[String, 所要请求的url]：表示要上传的url，默认用post请求（不需要使用@AIPost注解）
            connTimeout[int, 连接超时时间]：连接一个url的连接等待时间
            soTimeout[int, response返回超时时间]：连接上一个url，获取response的返回等待时间
            注意：使用此注解的方法参数需要包含Collection<File>或其子类型集合 或者包含File对象 来作为要上传的文件

        @AIColumn: 属性注解，用于映射属性到表字段
            value[String]：表示要映射到的表字段名称，不填写则默认以属性名作为表字段名

        @AIPrimaryKey: 属性注解，用于指定属性为主键
            insertable[boolean]：表示插入数据时是否同时也插入主键到表。默认为false，即表的主键应该为自动生成

        @AITable: 类注解，用于映射类到表
            value[String]: 表示要映射到的表的名称，不填写或未增加该注解则默认以类名小写为表名

        @AIPresenter：属性注解，使用MVP时使用，该注解用于在Activity上注入Presenter，自动在Presenter中注入Viewer和Interactor对象



###提交日志(Commit Logs): <br/>
###
        2014-7-18:
        1. 新增@AIPresenter注解，使用MVP时使用，该注解用于在Activity上注入Presenter，自动在Presenter中注入Viewer和Interactor对象

        2014-4-15:
        1. 新增@AIMapper注解，该注解使用在NetWorker接口上面，value值可以自动拼接在该接口中声明的方法url上面

        2014-4-13:
        1. 解决Gson所引发的bug

        2014-3-27:
        1. 去除线程池，转移到AndroidBucket项目中，所以需要添加Library[AndroidBucket项目](https://github.com/wangjiegulu/AndroidBucket)的支持（日志、线程、util等）
        2. 修改Gson DateFormat为yyyy-MM-dd HH:mm:ss:SSS

        2014-3-25:
        1. 增加线程池的配置

        2014-3-25:
        1. AIDbExecutor增加使用事务执行多条sql

        2014-3-25:
        1. 增加对sqlite3数据库的orm注解支持，增加@AIColumn、@AIPrimaryKey、@AITable三个注解来映射到表（有待改进）
        2. 重构代码

        2014-3-24：
        1. build androidInject_1.3.jar

        2014-3-11:
        1. netWorker中方法申明的返回类型为String时，则直接返回请求体的String

        2014-2-26:
        1. 修改@AIView注解的value和id值均代表控件redId，简化注入AIView代码，如：@AIView(R.id.listView)，如果两个都设置，则使用value值
        2. 增加AIActivity、AISupportFragment、AISupportFragmentActivity解析Annotations时间统计，打印log

        2014-2-10:
        1. 增加文件上传注解@AIUpload
        2. 代码重构

        2014-2-10:
        1. @AIGet和@AIPost增加connTimeout（连接一个url的连接等待时间）和soTimeout设置（连接上一个url，获取response的返回等待时间）

        2014-2-10:
        1. Worker中添加异常抛出

        2014-2-8:
        1. 增加GET或POST请求时请求参数可使用Params类传入，简化代码

        2014-2-8:
        1. 增加@AIScreenSize注解，作用于属性，用于注入当前设备的屏幕大小（宽高）
        2. 增加对网络请求的支持，使用动态代理实现：@AIGet注解，作用于接口方法，表示以GET来请求url；@AIPost注解，作用于接口方法，表示以POST来请求url；@AIParam，用于注入请求参数
        3. 增加@AINetWorker注解，作用于属性，用于注入网络请求服务
        4. 重构代码

        2013-12-17:
        1. refactor source

        2013-12-5:
        1. build androidInject_1.0.jar

        2013-12-5:
        1. add fragment support(need android-support-v4.jar)

        2013-12-4:
        1. add typeList annotations: @AINoTitle, @AIFullScreen
        2. modify field annotation ---- @AIView: default identifier[R.id.{field name}] if did not set id

        2013-12-2:
        1. add field annotations: @AIBean, @AISystemService

        2013-12-1:
        1. add method annotations: @AIItemLongClick
        2. add annotations(itemLongClickMethod) of @AIView

        2013-12-1:
        1. RENAME @InitLayout, @InitView TO @AILayout, @AIView
        2. ADD method annotations: @AIClick, @AILongClick, @AIItemClick
        3. refactor source


License
=======

    Copyright 2013 Wang Jie

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing blacklist and
    limitations under the License.


[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-androidInject-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1581)

