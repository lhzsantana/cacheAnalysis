package com.prototype.redis;

import com.prototype.ICache;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;

public class RedisCluster implements ICache {

    private JedisCluster jedisCluster;
    private String host;
    private int port;

    public RedisCluster(String host, int port){
        this.host = host;
        this.port = port;
    }

    private JedisCluster getClient(){

        if(jedisCluster==null){

            HashSet<HostAndPort> nodes = new HashSet<>();
            nodes.add(new HostAndPort(this.host, this.port));

            jedisCluster = new JedisCluster(nodes);
        }
        return jedisCluster;
    }

    public void set(String key, String value) {
        getClient().set(key, value);
    }

    public String get(String key) {
        return getClient().get(key);
    }

    public boolean exists(String key) {
        return getClient().exists(key);
    }

    public boolean remove(String key) {
        return getClient().del(key) > 0 ? true : false;
    }
}
