package com.good.study.exseckill.redis;

/**
 * @author 76366 reids prefix前缀实现接口
 */
public interface KeyPrefix {
	
	int expireSeconds();

	String getPrefix();
}
