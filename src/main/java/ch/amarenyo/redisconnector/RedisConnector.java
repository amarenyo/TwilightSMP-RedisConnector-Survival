package ch.amarenyo.redisconnector;

import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.RedisClient;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class RedisConnector extends JavaPlugin {
    //Starting connection with redis server
    RedisClient redis = RedisClient.builder().hostAndPort("localhost", 6379).build();

    @Override
    public void onEnable() {

        //test connection, will output PONG on server start if it was successful
        String ping = redis.ping();
        getLogger().info(ping);

        //Set key color to red on the server
        redis.sadd("color", "Red");

        //start an executor to run sub in background
        ExecutorService executor;
        executor = Executors.newSingleThreadExecutor();

        //run the executor
        executor.submit(() -> {
            try (Jedis jedis = new Jedis()) {

                jedis.subscribe(new JedisPubSub() {

                @Override
                public void onMessage(String channel, String message) {
                    //function that happen on the arrival of a message

                }
            }, "channel", "channel2"); //specify on which channels should get listened
            }
        });
    }

    @Override
    public void onDisable() {
        redis.close();//close connection on shutdown
        getLogger().info("Shutdown redis connection");
    }
}
