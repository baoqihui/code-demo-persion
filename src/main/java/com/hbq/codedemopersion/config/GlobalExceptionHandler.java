package com.hbq.codedemopersion.config;

import com.hbq.codedemopersion.common.model.ErrorEnum;
import com.hbq.codedemopersion.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * @author: hbq
 * @description: 统一异常拦截
 * @date: 2017/10/24 10:31
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

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
	 * 常规异常处理
	 */
	@ExceptionHandler(Throwable.class)
	public Result handleGeneralException(Exception e) {
		log.error("未知异常：{}\nat：{}", e.getMessage(), Arrays.toString(e.getStackTrace()).replaceAll(",", "\n   "));
		return Result.failedWith("", ErrorEnum.E_500.getErrorCode(), ErrorEnum.E_500.getErrorMsg());
	}
}
