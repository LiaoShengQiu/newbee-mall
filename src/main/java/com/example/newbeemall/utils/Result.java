package com.example.newbeemall.utils;

import java.io.Serializable;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int resultCode;
    private String message;
    private T data;
    private long id;
    private Object obj;
    private Object gwc;

    public Result() {
    }

    public Result(int resultCode, long id, Object obj) {
        this.resultCode = resultCode;
        this.id = id;
        this.obj = obj;
    }
    public Result(int resultCode, long id, Object obj,Object gwc) {
        this.resultCode = resultCode;
        this.id = id;
        this.obj = obj;
        this.gwc = gwc;
    }
    public Result(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public Object getGwc() {
        return gwc;
    }

    public void setGwc(Object gwc) {
        this.gwc = gwc;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultCode=" + resultCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
