package com.frame.exception;

/**
 * ServiceException
 * 
 * @author yyf
 * @date 2018年11月1日
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
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

	public ServiceException(String errorCode, String msg) {
		this(msg);
		this.errorCode = errorCode;
	}

	public ServiceException(String errorCode, String msg, Throwable e) {
		super(msg, e);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
