package com.good.study.exseckill.result;

import lombok.Data;

@Data
public class Result<T> {
	
	private int code;

	private String msg;

	private T data;

	private Result(T data) {
		this.code = 0;
		this.msg = "sucess";
		this.data = data;
	}

	private Result(CodeMsg codeMsg) {
		if (codeMsg == null) {
			return;
		}
		this.code = codeMsg.getCode();
		this.msg = codeMsg.getMsg();
	}

	public static <T> Result<T> sucess(T data) {
		return new Result<T>(data);
	}

	public static <T> Result<T> error(CodeMsg codeMsg) {
		return new Result<T>(codeMsg);
	}

}
