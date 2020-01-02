package com.good.study.exseckill.exception;

import com.good.study.exseckill.result.CodeMsg;

/**
 * @author 76366
 *
 */
public class GlobalException extends RuntimeException {
	private static final long serialVersionUID = -3358191712219668149L;
	private CodeMsg codeMsg;

	public GlobalException(CodeMsg codeMsg) {
		super();
		this.codeMsg = codeMsg;
	}

	public CodeMsg getCodeMsg() {
		return codeMsg;
	}

}
