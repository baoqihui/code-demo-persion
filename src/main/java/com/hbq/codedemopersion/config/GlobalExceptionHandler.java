package com.hbq.codedemopersion.config;

import com.hbq.codedemopersion.common.model.ErrorEnum;
import com.hbq.codedemopersion.common.model.Result;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: hbq
 * @description: 统一异常拦截
 * @date: 2017/10/24 10:31
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * 未登录报错拦截
	 * 在请求需要权限的接口,而连登录都还没登录的时候,会报此错
	 */
	@ExceptionHandler(UnauthenticatedException.class)
	public Result unauthenticatedException() {
		return Result.failedWith("",ErrorEnum.E_203.getErrorCode(), ErrorEnum.E_203.getErrorMsg());
	}

	/**
	 * 权限不足报错拦截
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public Result unauthorizedExceptionHandler() {
		return Result.failedWith("", ErrorEnum.E_204.getErrorCode(), ErrorEnum.E_204.getErrorMsg());
	}


	/**
	 * 空指针异常
	 */
	/*
	@ExceptionHandler(NullPointerException.class)
	public Result nullException() {
		return Result.failedWith("", ErrorEnum.E_500.getErrorCode(), ErrorEnum.E_500.getErrorMsg());
	}*/
}
