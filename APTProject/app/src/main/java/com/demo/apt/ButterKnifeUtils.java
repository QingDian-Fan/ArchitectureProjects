package com.demo.apt;

import android.app.Activity;
import android.util.Log;

import java.lang.reflect.Constructor;

public class ButterKnifeUtils {

    public static void bind(Activity mActivity) {
        try {
            //获取"当前的activity类名+Binding"的class对象
            Class bindingClass = Class.forName(mActivity.getClass().getCanonicalName() + "BindingView");
            //获取class对象的构造方法，该构造方法的参数为当前的activity对象
            Constructor constructor = bindingClass.getDeclaredConstructor(mActivity.getClass());
            //调用构造方法
            constructor.newInstance(mActivity);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG--->",e.getMessage());
        }
    }
}
