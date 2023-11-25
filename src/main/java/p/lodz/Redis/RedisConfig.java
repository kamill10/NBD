package p.lodz.Redis;

import lombok.Getter;
import redis.clients.jedis.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class RedisConfig {
    private JedisPool jedisPool;

    public RedisConfig() {
        String connectionString = loadConnectionString();
        jedisPool = new JedisPool(new JedisPoolConfig(), connectionString);
    }

    private String loadConnectionString() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("redis.properties")) {
            if (input == null) {
                System.out.println("Brak pliku konfiguracyjnego.");
                return null;
            }
            prop.load(input);
            return prop.getProperty("redis.connection.url");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
