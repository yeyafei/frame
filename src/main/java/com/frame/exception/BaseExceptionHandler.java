package com.frame.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.frame.bean.R;
import com.frame.enums.ErrorCode;

/**
* ExceptionHandler
*
* @author yyf
* @date 2018年11月1日
*/
@RestControllerAdvice
public class BaseExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 自定义service异常
	 * @param e
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(ServiceException.class)
	public R handleServiceException(ServiceException e) {
		R r = new R();
		r.setCode(e.getErrorCode());
		r.setMsg(e.getMessage());
		return r;
	}
	/**
	 * 系统exception异常
	 * @param e
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(Exception.class)
	public R handleException(Exception e) {
		logger.error(e.getMessage(), e);
		R r = new R();
		r.setCode(ErrorCode.SysErrorType.SYSTEM_ERROR.getCode());
		if (e.getMessage() != null && e.getMessage().length() > 100) {
		    r.setMsg(ErrorCode.SysErrorType.SYSTEM_ERROR.getDesc());
		} else {
			r.setMsg(e.getMessage());
		}
		return r;
	}
}
