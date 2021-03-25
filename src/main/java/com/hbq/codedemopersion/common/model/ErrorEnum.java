package com.hbq.codedemopersion.common.model;

/**
 * @author: hxy
 * @date: 2017/10/24 10:16
 */
public enum ErrorEnum {
	/*
	 * 错误信息
	 * */
	E_202(202, "与服务器断开连接，请重新登录"),
	E_203(203, "登陆已过期,请重新登陆"),
	E_204(204, "权限不足"),
	E_500(500, "空指针异常");

	private Integer errorCode;

	private String errorMsg;

	ErrorEnum(Integer errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}