package p.lodz.Exceptions;

import redis.clients.jedis.exceptions.JedisException;

public class ExceptionRedis extends JedisException {
    public ExceptionRedis(String msg) {
        super(msg);
    }
}
