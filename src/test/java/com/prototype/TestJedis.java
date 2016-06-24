package com.prototype;

import com.prototype.redis.RedisCluster;

import java.io.IOException;

public class TestJedis {

    RedisCluster cluster;

    @org.junit.Before
    public void setUp() throws Exception {
        cluster = new RedisCluster("localhost", 6379);
    }

    @org.junit.Test
    public void setGet() throws IOException, ClassNotFoundException {

        cluster.set("k1","v1");
    }
}