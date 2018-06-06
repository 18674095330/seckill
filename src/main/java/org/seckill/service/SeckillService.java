package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * @Author: Zhihao Chen
 * @Date: Created in 0:31 2018/4/27
 * @Description
 */
public interface SeckillService {

    List<Seckill> getSeckillList();

    Seckill getById(long seckillId);

    /**秒杀开启输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间*/
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param url_md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String url_md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;
}
