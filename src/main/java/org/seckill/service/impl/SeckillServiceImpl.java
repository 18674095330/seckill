package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SucccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author: Zhihao Chen
 * @Date: Created in 14:23 2018/5/1
 * @Description
 */

@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SucccessKilledDao succcessKilledDao;

    //用于增加md5的复杂度
    private final String salt = "dfhdsj)(&&^%324214323^&%$$#46545";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 10);
    }


    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }


    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //系统当前时间
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
    }



    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }



    @Transactional
    /**
     * 使用注解控制事务方法的优点
     * 1：开发团队达成一致约定，明确标注事务方法的编程风格
     * 2：保证事务方法的执行时间尽可能的短，不要穿插其他网络操作，RPC/HTTP请求或者剥离到事务方法外部.
     * 3：不是所有的方法都需要事务，如只有一条修改操作或只读操作不需要事务控制
     */
    /*如果方法执行的时候没有抛出异常就commui，如果抛出异常就rollback*/
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String url_md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (url_md5 == null || !url_md5.equals(getMD5(seckillId))) {
            throw new SeckillException("秒杀数据被重写");
        }

        //执行秒杀逻辑：1.减库存 2.插入购买行为
        try {
            int updateCount = seckillDao.reduceNumber(seckillId, new Date());
            if (updateCount <= 0) {
                throw new SeckillCloseException("秒杀结束");
            } else {
                int insertCount = succcessKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    throw new RepeatKillException("重复秒杀");
                } else {
                    SuccessKilled successKilled = succcessKilledDao.queryWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }

        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //将所有编译异常转化为运行异常
            throw new SeckillException("seckill inner error" + e.getMessage());
        }
    }
}
