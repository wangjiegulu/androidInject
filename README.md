androidInject
=============

###使用注解来简化android开发  <br/>(Use annotations inject to simplify the development of android)<br/>

###注解(Annotations): <br/>
###
        @AILayout: 类注解
            value[int]: 用于设置该Activity的布局 ---- setContentView(resId);


        @AIView: 属性注解
            id[int]: 用于绑定控件 ---- findViewById(resId);
            clickMethod[String]: 用于设置控件点击事件的回调方法, 可选, 方法名称任意, 参数必须为(View view)
            longClickMethod[String]: 用于设置控件长按的回调方法, 可选, 方法名任意, 参数必须为(View view)
            itemClickMethod[String]: 用于设置控件item点击的回调方法, 可选, 方法名任意, 参数必须为(AdapterView, View, int, long)


        @AIClick: 方法注解
            value[int[], 所要绑定控件的id]: 用于绑定控件点击事件的回调方法, 方法名称任意, 参数必须为(View view)

        @AIItemClick: 方法注解
            value[int[], 所要绑定控件的id]: 用于绑定控件item点击事件的回调方法, 方法名称任意, 参数必须为(AdapterView, View, int, long)

        @AILongClick: 方法注解
            value[int[], 所要绑定控件的id]: 用于绑定控件长按事件的回调方法, 方法名称任意, 参数必须为(View view)

###提交日志(Commit Logs): <br/>
###
        2013-12-1:
        1. RENAME @InitLayout, @InitView TO @AILayout, @AIView
        2. ADD method annotations: @AIClick, @AILongClick, @AIItemClick
        3. refactor source











