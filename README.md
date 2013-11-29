androidInject
=============

使用注解来简化android开发

增加@InitLayout和@InitView注解:
InitLayout:
    value: 用于设置该Activity的布局 ---- setContentView(resId);
InitView:
    id: 用于绑定控件 ---- findViewById(resId);
    clickMethod: 用于设置控件点击事件的回调方法, 可选, 方法名称任意, 参数必须为(View view)