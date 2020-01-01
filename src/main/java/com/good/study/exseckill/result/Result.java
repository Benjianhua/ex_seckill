package com.good.study.exseckill.result;

import javax.annotation.PostConstruct;

public class Result {

	private String name;

	private String text;
	
	private String say="快乐的一天";

	public Result() {
		System.out.println("世界开始了！！！");
		System.out.println(this.say);
	}

	@PostConstruct
	public void init() {
		this.name = "顾建华";
		this.text = "世界聚焦于你";
		this.say="快的的每一天";
	}

	public void printIn() {
		System.out.println(this.text+this.name+this.say);
	}
	
	public static void main(String[] args) {
		Result result=new Result();
		result.printIn();
	}
}
