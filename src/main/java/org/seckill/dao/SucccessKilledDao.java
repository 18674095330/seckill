package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * @Author: Zhihao Chen
 * @Date: Created in 21:16 2018/4/25
 * @Description
 */
public interface SucccessKilledDao {

    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    SuccessKilled queryWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
