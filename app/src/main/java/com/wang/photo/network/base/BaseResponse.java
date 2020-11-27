package com.wang.photo.network.base;

/**
 * 通用数据返回
 */
public class BaseResponse<T> {
    public int status = -1;//返回状态码

    public String msg;

    public T data;

}
