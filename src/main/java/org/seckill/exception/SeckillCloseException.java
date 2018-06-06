package org.seckill.exception;

/**
 * @Author: Zhihao Chen
 * @Date: Created in 14:10 2018/5/1
 * @Description 秒杀关闭异常
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
