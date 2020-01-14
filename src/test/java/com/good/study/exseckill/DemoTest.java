package com.good.study.exseckill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.good.study.exseckill.ExSeckillApplication;
import com.good.study.exseckill.redis.RedisService;
import com.good.study.exseckill.redis.UserKey;

/**
 * @author 76366
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExSeckillApplication.class)
public class DemoTest {

	@Autowired
	private RedisService redisService;

	@Test
	public void test() {
		System.out.println("begin ---------------");
		redisService.set(UserKey.getById, "01", "01");
		System.out.println("end ---------------");
	}
}
