package com.prototype;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;

public class TestJedisCluster2 {

    @org.junit.Test
    public void setGetMany() throws IOException, ClassNotFoundException {

        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("54.174.109.253", 7000));
        nodes.add(new HostAndPort("54.174.109.253", 7001));
        nodes.add(new HostAndPort("54.174.109.253", 7002));

        JedisCluster jedisCluster = new JedisCluster(nodes,1000,15);

        System.out.println("Begin test");

        for(int i=0;i<1000;i++) {
            String v = String.valueOf(i);

            Long t1 = System.currentTimeMillis();

            System.out.println("Teste "+i+" "+jedisCluster.set(v, v));

            Long t2 = System.currentTimeMillis();
            System.out.println("Time in seconds (set) : " + ((t2-t1)));

            System.out.println( jedisCluster.get(v));

            Long t3 = System.currentTimeMillis();
            System.out.println("Time in seconds (get) : " + ((t3-t2)));

            System.out.println("Time in seconds : " + ((t3-t1)));
        }
    }
}
