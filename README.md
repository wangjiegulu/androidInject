androidInject
=============

使用注解来简化android开发
----------------------

###注解: <br/>
###
        @InitLayout:
            value[int]: 用于设置该Activity的布局 ---- setContentView(resId);

        @InitView:
            id[int]: 用于绑定控件 ---- findViewById(resId);
            clickMethod[String]: 用于设置控件点击事件的回调方法, 可选, 方法名称任意, 参数必须为(View view)
            longClickMethod[String]: 用于设置控件长按的回调方法，可选，方法名任意, 参数必须为(View view)
            itemClickMethod[String]: 用于设置控件item点击的回调方法，可选，方法名任意，参数必须为(AdapterView, View, int, long)



