package com.wang.photo.network.base;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * body参数处理
 */
public class BaseBody {

    /**
     * 表单序列化
     */
    public Map<String, String> getForm() {
        Map<String, String> map = new HashMap<>();
        Class clazz = getClass();
        Field[] fields = clazz.getDeclaredFields();//获得当前类的成员变量，不包括父类的成员变量。

        for (Field f : fields) {
            f.setAccessible(true);//设置可访问私有变量
            try {
                if (!f.isSynthetic()) {//过滤掉编译器自动生成的成员变量，和序列化id
                    if (!TextUtils.equals("serialVersionUID", f.getName())) {
                        if (f.get(this) != null) {
                            map.put(f.getName(), f.get(this).toString());
                        } else {
                            map.put(f.getName(), "");
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
