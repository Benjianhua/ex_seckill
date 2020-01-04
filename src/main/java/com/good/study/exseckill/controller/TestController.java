package com.good.study.exseckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.good.study.exseckill.dao.UserDao;
import com.good.study.exseckill.domain.User;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/user")
public class TestController {

	@Autowired
	private UserDao userDao;

	@ApiOperation(value = "获取用户信息", notes = "获取用户信息")
	@RequestMapping("/info/{id}/{name}")
	@ResponseBody
	public String insertUser(@PathVariable("id") int id, @PathVariable("name") String name) {
		User user = new User();
		user.setId(id);
		user.setName(name);
		userDao.insert(user);
		return "success";
	}

}
