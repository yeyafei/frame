package com.frame.bean;
/**
* ResultBean
* 
* @author yyf
* @date 2018年11月1日
*/

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.constant.Constant;

public class R<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String msg;
	
	private T data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	

	
	public R<T> ok(T data){
		this.code =Constant.OK;
		this.data = data;
		return this;
	}
	
	
	public R<T> success(String msg){
		this.code =Constant.OK;
		this.msg = msg;
		return this;
	}
	
	public R<T> error(String code, String msg){
		this.code = code;
		this.msg = msg;
		return this;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
