package com.frame.redis;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

/**
 * RedisUtils
 * 
 * @author yyf
 * @date 2018年11月6日
 */
@Service
public class RedisUtils {
    @SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
	public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
	public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 批量删除对应的value
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * @param pattern
     */
    @SuppressWarnings("unchecked")
	public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }
    /**
     * 删除对应的value
     * @param key
     */
    @SuppressWarnings("unchecked")
	public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
	public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
	public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }
    /**
     * 读取集合缓存keys
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<?> multiGet(Collection<Serializable> keys) {
    	List<?> result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.multiGet((Collection<Serializable>) keys);
        return result;
    }
    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    @SuppressWarnings("unchecked")
	public void hmSet(String key, Object hashKey, Object value, Long expireTime){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
        if (expireTime != null) {
           redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
    }

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    @SuppressWarnings("unchecked")
	public Object hmGet(String key, Object hashKey){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }

    /**
     * 列表添加
     * @param k
     * @param v
     */
    @SuppressWarnings("unchecked")
	public void lPush(String k,Object v, Long expireTime){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k,v);
        if (expireTime != null) {
            redisTemplate.expire(k, expireTime, TimeUnit.SECONDS);
         }
    }

    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Object> lRange(String k, long l, long l1){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }

    /**
     * 集合添加
     * @param key
     * @param value
     */
    @SuppressWarnings("unchecked")
	public void add(String key, Object value, Long expireTime){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
        if (expireTime != null) {
           redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
    }

    /**
     * 集合获取
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
	public Set<Object> setMembers(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }
    
   /**
    * 移除set中的成员
    * @param key
    * @param members
    * @return
    */
    @SuppressWarnings("unchecked")
	public long removeSetMembers(String key, Object... members){
    	if (members != null && members.length > 0) {
            return redisTemplate.boundSetOps(key).remove(members);
        }
        return 0L;
       
    }
    
    /**
     * 得到set中所有元素size
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public long getSetSize(String key) {
        return redisTemplate.boundSetOps(key).size();
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    @SuppressWarnings("unchecked")
	public void zAdd(String key,Object value, double scoure, Long expireTime){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
        if (expireTime != null) {
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
    }

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    @SuppressWarnings("unchecked")
	public Set<Object> rangeByScore(String key,double scoure,double scoure1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }
    
    /**
     * 添加map
     * @param key
     * @param map
     */
    @SuppressWarnings("unchecked")
    public void addMap(String key, Map<String, Object> map, Long expireTime) {
        redisTemplate.boundHashOps(key).putAll(map);
        if (expireTime != null) {
        	redisTemplate.boundHashOps(key).expire(expireTime, TimeUnit.SECONDS);
        }
    }

    /**
     * 添加map中的具体key,value
     * @param key
     * @param field
     * @param value
     */
    @SuppressWarnings("unchecked")
    public void addMap(String key, String field, Object value, Long expireTime) {
        redisTemplate.boundHashOps(key).put(field, value);
        if (expireTime != null) {
        	redisTemplate.boundHashOps(key).expire(expireTime, TimeUnit.SECONDS);
        }
    }
    
    /**
     * 删除map中的某个或某些对象
     * @param key   map对应的key
     * @param field map中该对象的key
     */
    @SuppressWarnings("unchecked")
    public void removeMapFields(String key, Object... fields) {
        redisTemplate.boundHashOps(key).delete(fields);
    }

    /**
     * 获取map对象的size
     * @param key map对应的key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Long getMapSize(String key) {
        return redisTemplate.boundHashOps(key).size();
    }

    /**
     * 获取map对象
     * @param key map对应的key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(String key) {
        return redisTemplate.boundHashOps(key).entries();
    }

    /**
     * 获取map缓存中的某个对象
     * @param key map对应的key
     * @param field map中该对象的key
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapField(String key, String field) {
        return (T) redisTemplate.boundHashOps(key).get(field);
    }

    /**
     * 判断map中对应key的key是否存在
     * @param key map对应的key
     * @param field map对应的key中的key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Boolean hasMapKey(String key, String field) {
        return redisTemplate.boundHashOps(key).hasKey(field);
    }

    /**
     * 获取map对应key的value
     * @param key map对应的key
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object> getMapFieldValue(String key) {
        return redisTemplate.boundHashOps(key).values();
    }

    /**
     * 获取map的key
     * @param key map对应的key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Set<Object> getMapFieldKey(String key) {
        return redisTemplate.boundHashOps(key).keys();
    }
    
    /**
     * 设置失效时间:秒
     * @param key
     * @param expireTime
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean setExpireTime(String key, Long expireTime) {
        return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }
}
