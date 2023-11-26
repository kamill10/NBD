package p.lodz.Exceptions;

import redis.clients.jedis.exceptions.JedisException;

public class RedisException extends JedisException {
    public RedisException(String msg) {
        super(msg);
    }
}
