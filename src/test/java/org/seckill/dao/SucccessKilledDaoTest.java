package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Author: Zhihao Chen
 * @Date: Created in 23:44 2018/4/26
 * @Description
 */

/** 配置spring和junit整合，junit启动时加载springIOC容器
 *  spring-test,junit*/
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SucccessKilledDaoTest {

    @Resource
    private SucccessKilledDao succcessKilledDao;
    @Test
    public void insertSuccessKilled() throws Exception {
        long id = 1001L;
        long phone = 18674095333L;
        int insertCount = succcessKilledDao.insertSuccessKilled(id, phone);
        System.out.println(insertCount);
    }

    @Test
    public void queryWithSeckill() throws Exception {
        SuccessKilled successKilled = succcessKilledDao.queryWithSeckill(1000L, 18674095333L);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}