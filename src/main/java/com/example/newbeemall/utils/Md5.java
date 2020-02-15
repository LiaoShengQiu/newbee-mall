package com.example.newbeemall.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5 {

    public static String getMd5(String data){
        return DigestUtils.md5Hex(data);
    }

}
