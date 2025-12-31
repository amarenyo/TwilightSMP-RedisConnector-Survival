package ch.amarenyo.redisconnector;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.RedisClient;

public final class RedisConnector extends JavaPlugin {
    //Starting connection with redis server
    RedisClient redis = RedisClient.builder().hostAndPort("localhost", 6379).build();
    private static RedisConnector instance;

    @Override
    public void onEnable() {

        instance = this;


        //test connection, will output PONG on server start if it was successful
        String ping = redis.ping();
        getLogger().info(ping);


        //Set key color to red on the server
        redis.sadd("color", "Red");

        //start an executor to run sub in background

        Bukkit.getScheduler().runTaskAsynchronously(RedisConnector.getInstance(), () -> {

        try (Jedis jedis = new Jedis()) {

            jedis.subscribe(new JedisPubSub() {

                @Override
                public void onMessage(String channel, String message) {
                    if (channel.equals("getSurvival.Server.All") && message.contains("Survival")) {
                        getLogger().info("Incomming Request getSurvival.Server.All");
                        Server server = Bukkit.getServer();
                        int onlineplayers = server.getOnlinePlayers().size();
                        int maxplayers = server.getMaxPlayers();

                        getLogger().info(onlineplayers + " " + maxplayers);

                        jedis.publish("survival.Server.All", onlineplayers + " / " + maxplayers);
                        getLogger().info("Published: " + onlineplayers + " / " + maxplayers);
                    } else if (channel.equals("getSurvival.Player.All")) {

                    } else {
                        getLogger().warning("If you see this message i fucked up");
                    }
                }
            }, "getSurvival.Server.All", "getSurvival.Player.All"); //specify on which channels should get listened
        }
        });
    }

    @Override
    public void onDisable() {
        redis.close();//close connection on shutdown
        getLogger().info("Shutdown redis connection");
    }

    public static RedisConnector getInstance() {
        return instance;
    }

}
