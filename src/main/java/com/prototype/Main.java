package com.prototype;

import com.prototype.redis.Redis;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Main {

    private static SecureRandom random = new SecureRandom();

    private static Redis elasticache;

    public static void main(String [] args) {
        elasticache = new Redis("test2.dk5qsd.0001.use1.cache.amazonaws.com", 6379);

        bulkSetGet();
    }

    private static void bulkSetGet(){

        int seed=100;

        for(int i=0;i<3;i++){
            System.gc();
            System.out.println("For "+seed+" calls the total Elaticache time is " + setGetN(seed,elasticache));

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

        cache.set(key, value);

        long redisBegin = System.currentTimeMillis();

        return System.currentTimeMillis() - redisBegin;
    }

    private static String nextKeyValue() {
        return new BigInteger(130, random).toString(32);
    }

}
