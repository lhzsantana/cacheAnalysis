package com.prototype.ehcache;

import com.prototype.ICache;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCache implements ICache{

    private Cache cache;
    private String name;

    public EhCache(String name){
        this.name = name;
    }

    private Cache getCache(){

        if(cache==null) {
            CacheManager cm = CacheManager.getInstance();

            if(!cm.cacheExists(this.name)) {
                cm.addCache(this.name);
            }

            cache = cm.getCache(this.name);
        }

        return cache;
    }

    public void set(String key, String value) {
        getCache().put(new Element(key,value));
    }

    public String get(String key) {
        Element element = getCache().get(key);
        return element.getObjectValue().toString();
    }

    public boolean exists(String key) {
        return getCache().isKeyInCache(key);
    }

    public boolean remove(String key) {
        return getCache().remove(key);
    }
}
