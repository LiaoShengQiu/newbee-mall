package com.example.newbeemall.mapper.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5 {

    public static String getMd5(String data){
        if(data.isEmpty())
            return null;
        return DigestUtils.md5Hex(data);
    }

}
