package com.prototype.redis;

import com.prototype.ICache;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class RedisCluster implements ICache {

    private JedisCluster jedisCluster;

    private JedisCluster getClient(){

            if(jedisCluster==null){

            HashSet<HostAndPort> nodes = new HashSet<>();
            nodes.add(new HostAndPort("54.174.109.253", 7000));
            nodes.add(new HostAndPort("54.174.109.253", 7001));
            nodes.add(new HostAndPort("54.174.109.253", 7002));
            nodes.add(new HostAndPort("54.174.109.253", 7003));
            nodes.add(new HostAndPort("54.174.109.253", 7004));
            nodes.add(new HostAndPort("54.174.109.253", 7005));
            //nodes.add(new HostAndPort(this.host, this.port));
            //nodes.add(new HostAndPort("192.168.99.100", 5000));


            jedisCluster = new JedisCluster(nodes, 100, 100);
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

    public void printAllKeys() {

        Set<String> keys = new HashSet<>();

        for(JedisPool pool : jedisCluster.getClusterNodes().values()){

            try {
                Jedis jedis = pool.getResource();

                if (jedis.isConnected()) {
                    for (String key : jedis.keys("*")) {
                        keys.add(key);
                    }
                }
            }catch(Exception e){
                //e.printStackTrace();
            }
        }

        for(String key:keys){
            System.out.println("Key "+key+" value "+jedisCluster.get(key));
        }

        System.out.println(keys.size());
    }

    public void clear() {

        for(JedisPool pool : jedisCluster.getClusterNodes().values()){

            try {
                Jedis jedis = pool.getResource();

                jedis.flushAll();
            }catch(Exception e){
                //e.printStackTrace();
            }
        }
    }
}
