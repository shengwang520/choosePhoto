package com.wang.choosephoto.utils;

import com.google.gson.Gson;

public class GsonUtils {
    private static Gson gson;

    private GsonUtils() {
    }

    public static Gson getInstance() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }
}
