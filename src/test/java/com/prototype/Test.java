package com.prototype;

import com.prototype.ehcache.EhCache;
import com.prototype.redis.Redis;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.Assert.*;

public class Test {

    private SecureRandom random = new SecureRandom();

    private Redis elasticache;
    private Redis redis;
    private EhCache ehCache;

    @org.junit.Before
    public void setUp() throws Exception {
        elasticache = new Redis("replication-001.dk5qsd.0001.use1.cache.amazonaws.com", 6379);
        redis = new Redis("localhost", 6379);
        ehCache = new EhCache("cache1");
    }

    @org.junit.Test
    public void setGet(){

        String key = "k1";
        String value = "v1";

        System.out.println("Elaticache time: " + calculateGetSet(key, value, elasticache));
        System.out.println("Redis time: " + calculateGetSet(key, value, redis));
        System.out.println("EhCache time: " + calculateGetSet(key, value, ehCache));
    }

    @org.junit.Test
    public void bulkSetGet(){

        int seed=100;

        for(int i=0;i<3;i++){
            System.gc();
            System.out.println("For "+seed+" calls the total Elaticache time is " + setGetN(seed,elasticache));

            seed=seed*10;
        }
    }

    private long setGetN(int n, ICache cache){

        long total = 0;
        double warming = 0.1;


        for(int i=0;i<(n*0.1); i++) {
            calculateGetSet(nextKeyValue(), nextKeyValue(), cache);
        }

        for(int i=0;i<n; i++) {
            total+=calculateGetSet(nextKeyValue(), nextKeyValue(), cache);
        }

        return total;
    }

    private long calculateGetSet(String key, String value, ICache cache){

        cache.set(key, value);

        long redisBegin = System.currentTimeMillis();

        assertEquals(value, cache.get(key));

        return System.currentTimeMillis() - redisBegin;
    }

    private String nextKeyValue() {
        return new BigInteger(130, random).toString(32);
    }

}