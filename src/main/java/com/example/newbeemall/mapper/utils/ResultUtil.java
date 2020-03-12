package com.example.newbeemall.mapper.utils;

import lombok.Data;

@Data
public class ResultUtil {
	private int resultCode;         //执行状态
	private Object data;           //数据
	
	public ResultUtil(){
	}
	
	public ResultUtil(boolean bool){
		this();
		if(bool==true)
			this.resultCode=200;
		else
			this.resultCode=500;
	}
	
	public ResultUtil(Object data){
	    this.data=data;
	}
	
	public ResultUtil(Object data,boolean bool){
		this(bool);
		this.data=data;
	}
}
