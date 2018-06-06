package org.seckill.exception;

/**
 * @Author: Zhihao Chen
 * @Date: Created in 14:12 2018/5/1
 * @Description 秒杀相关业务异常
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
