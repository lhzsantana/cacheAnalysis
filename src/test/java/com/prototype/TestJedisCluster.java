package com.prototype;

import com.prototype.redis.RedisCluster;
import redis.clients.jedis.exceptions.JedisClusterMaxRedirectionsException;

import java.io.IOException;

public class TestJedisCluster {

    RedisCluster cluster;

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.Test
    public void setGet() throws IOException, ClassNotFoundException {

        boolean success=false;

        //while(!success) {
            try {
                cluster = new RedisCluster();

                cluster.set("1", "122222");
            } catch (JedisClusterMaxRedirectionsException jcmre) {
            }

            if(cluster.exists("1"));

            System.out.println(cluster.get("1"));


            cluster.printAllKeys();

        cluster.clear();


       // }
    }


    @org.junit.Test
    public void setGetMany() throws IOException, ClassNotFoundException {


        cluster = new RedisCluster();

        for(int i = 0; i<1000; i++) {

            System.out.print(i);

            String key = "1";

            //cluster.set(key, String.valueOf(i));

            System.out.print(cluster.get(key));
        }

    }
}