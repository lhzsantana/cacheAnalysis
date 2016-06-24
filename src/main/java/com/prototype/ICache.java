package com.prototype;

public interface ICache {

    public void set(String key, String value);

    public String get(String key);

    public boolean exists(String key);

    public boolean remove(String key);

}
