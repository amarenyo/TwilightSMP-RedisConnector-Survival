# RedisConnector

A simple **Minecraft Paper/Spigot plugin** that communicates with other servers or processes via **Redis**.  
Uses **Jedis 7** and works out-of-the-box.

---

## Requirements

- **Minecraft Server:** Paper 1.21.x or Spigot API 1.21.x compatible
- **Java:** 21
- **Redis Server:** reachable at `localhost:6379` (can be configured in the plugin)

> For development or building the plugin from source, **Maven** is required.

---

## Installation

1. Download the **shaded JAR** from `target/redisconnector-0.2.0-shaded.jar`.
2. Copy the JAR into your server's `plugins` folder.
3. Restart the server.

> The shaded JAR already includes all dependencies (Jedis). Paper/Spigot will not throw errors about missing libraries.

---

## Configuration

Redis host and port can be changed directly in the plugin code:

~~~java
RedisClient redis = RedisClient.builder()
        .hostAndPort("localhost", 6379)
        .build();
~~~

---

## Features

- Receive messages via Redis Pub/Sub
- Thread-safe for Minecraft server usage

## License

[MIT](LICENSE) – free to use, modify, and distribute.