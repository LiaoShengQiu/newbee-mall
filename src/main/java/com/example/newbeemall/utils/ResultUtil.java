package com.example.newbeemall.utils;

import lombok.Data;

@Data
public class ResultUtil {
	private int resultCode;         //执行状态
    private String message;
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
		if(data==null) {
			this.resultCode=500;
		}else{
			this.resultCode = 200;
			this.data=data;
		}

	}
	
	public ResultUtil(Object data,boolean bool){
		this(bool);
		this.data=data;
	}

	public ResultUtil(boolean bool,String massage){
		this(bool);
		this.message=massage;
	}

	public ResultUtil(boolean bool,Object data){
		this(bool);
	}
}
