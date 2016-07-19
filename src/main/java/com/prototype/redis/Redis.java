package com.prototype.redis;

import com.prototype.ICache;
import redis.clients.jedis.Jedis;

public class Redis implements ICache {

    private Jedis jedis;
    private String host;
    private int port;

    public Redis(String host, int port){
        this.host = host;
        this.port = port;
    }

    private Jedis getClient(){

        if(jedis==null){
            jedis = new Jedis(this.host, this.port);
        }

        //jedis.auth("teste");

        return jedis;
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
