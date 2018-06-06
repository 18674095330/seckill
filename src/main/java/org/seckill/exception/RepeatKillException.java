package org.seckill.exception;

/**
 * @Author: Zhihao Chen
 * @Date: Created in 14:03 2018/5/1
 * @Description 重复秒杀异常（运行期异常）
 */
public class RepeatKillException extends SeckillException {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
