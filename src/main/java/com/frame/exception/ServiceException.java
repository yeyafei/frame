package com.frame.exception;
/**
*
*
* @author yyf
* @date 2018年11月1日
*/
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	
	public ServiceException() {
		super();
	}
	
	public ServiceException(Throwable e) {
		super(e);
	}
	public ServiceException(String msg) {
		super(msg);
	}
	public ServiceException(String errorCode,String msg,Throwable e) {
        super(msg, e);
        this.errorCode = errorCode;
	}
}
