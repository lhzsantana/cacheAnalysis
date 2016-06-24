package com.prototype;

import com.google.gson.Gson;
import com.prototype.ehcache.EhCache;
import com.prototype.redis.Redis;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

public class TestObject {

    private SecureRandom random = new SecureRandom();

    private Redis elasticache;
    private Redis redis;
    private EhCache ehCache;
    Gson gson = new Gson();

    @org.junit.Before
    public void setUp() throws Exception {
        redis = new Redis("localhost", 6379);
    }

    @org.junit.Test
    public void setGet() throws IOException, ClassNotFoundException {

        RamdonPOJO r = new RamdonPOJO();
        r.name="luiz";

        redis.set("1", convertToString(r));

        RamdonPOJO r2 = (RamdonPOJO)convertFromString(redis.get("1"));

        System.out.print(r2.name);

    }

    public class RamdonPOJO {

        public String name;
        public String address;
        public List<String> phones;

    }

    private String convertToString(Object object) throws IOException {
       return gson.toJson(object);
    }

    private Object convertFromString(String string) throws IOException, ClassNotFoundException {
        return gson.fromJson(string, RamdonPOJO.class);
    }
}