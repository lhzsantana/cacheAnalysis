package com.prototype;

import com.prototype.ehcache.EhCache;
import com.prototype.redis.Redis;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Main {

    private static SecureRandom random = new SecureRandom();

    private static Redis elasticache;
    private static Redis redis;
    private static EhCache ehCache;

    public static void main(String [] args) {
        elasticache = new Redis("test2.dk5qsd.0001.use1.cache.amazonaws.com", 6379);
        redis = new Redis("localhost", 6379);
        ehCache = new EhCache("cache1");

        bulkSetGet();
    }

    private static void bulkSetGet(){

        int seed=100;

        for(int i=0;i<3;i++){
            System.gc();
            System.out.println("For "+seed+" calls the total Elaticache time is " + setGetN(seed,elasticache));
            System.out.println("For "+seed+" calls the total Redis time is " + setGetN(seed,redis));
            System.out.println("For "+seed+" calls the total EhCache time is " + setGetN(seed,ehCache));

            seed=seed*10;
        }
    }

    private static long setGetN(int n, ICache cache){

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

    private static long calculateGetSet(String key, String value, ICache cache){

        long redisBegin = System.currentTimeMillis();

        cache.set(key, value);

        cache.get(key);

        return System.currentTimeMillis() - redisBegin;
    }

    private static String nextKeyValue() {
        return new BigInteger(130, random).toString(32);
    }

}
