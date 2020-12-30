package com.xs.common.utils.redis.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import daisy.commons.lang3.StringUtils;
import com.xs.common.utils.redis.RedisUtils;
import org.apache.log4j.Logger;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * User: heyanhao
 * Date: 2018年11月20日20:38:04
 * Explain: Redis操作的实现类
 */
@SuppressWarnings("unchecked")
public class RedisUtilsImpl implements RedisUtils<String, Object> {

    private static Logger logger = Logger.getLogger(RedisUtilsImpl.class);

    private RedisTemplate redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate getRedisTemplate() {
        return this.redisTemplate;
    }

    /**
     * 出异常，重复操作的次数
     */
    private static Integer times = 5;


    @Override
    public void addJsonList(String key, List<Object> list) {
        set(key, JSON.toJSON(list).toString());
    }

    @Override
    public <T> List<T> getJsonList(String key, Class tClass) {
        List<T> redisList = new ArrayList<>();
        String result = (String) get(key);
        if (StringUtils.isNotBlank(result)) {
            redisList.addAll(JSONObject.parseArray(result, tClass));
        }
        return redisList;
    }



    @Override
    public Double getCreateTimeScore(long date) {
        try {
            return date / 100000.0;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<String> getAllKeys() {
        try {
            return redisTemplate.keys("*");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> getAllString() {
        try {
            Set<String> stringSet = getAllKeys();
            Map<String, Object> map = new HashMap<String, Object>();
            Iterator<String> iterator = stringSet.iterator();
            while (iterator.hasNext()) {
                String k = iterator.next();
                if (getType(k) == DataType.STRING) {
                    map.put(k, get(k));
                }
            }
            return map;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Set<Object>> getAllSet() {
        try {
            Set<String> stringSet = getAllKeys();
            Map<String, Set<Object>> map = new HashMap<String, Set<Object>>();
            Iterator<String> iterator = stringSet.iterator();
            while (iterator.hasNext()) {
                String k = iterator.next();
                if (getType(k) == DataType.SET) {
                    map.put(k, getSet(k));
                }
            }
            return map;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Set<Object>> getAllZSetRange() {
        try {
            Set<String> stringSet = getAllKeys();
            Map<String, Set<Object>> map = new HashMap<String, Set<Object>>();
            Iterator<String> iterator = stringSet.iterator();
            while (iterator.hasNext()) {
                String k = iterator.next();
                if (getType(k) == DataType.ZSET) {
                    logger.debug("k:" + k);
                    map.put(k, getZSetRange(k));
                }
            }
            return map;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Set<Object>> getAllZSetReverseRange() {
        try {
            Set<String> stringSet = getAllKeys();
            Map<String, Set<Object>> map = new HashMap<String, Set<Object>>();
            Iterator<String> iterator = stringSet.iterator();
            while (iterator.hasNext()) {
                String k = iterator.next();
                if (getType(k) == DataType.ZSET) {
                    map.put(k, getZSetReverseRange(k));
                }
            }
            return map;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, List<Object>> getAllList() {
        try {
            Set<String> stringSet = getAllKeys();
            Map<String, List<Object>> map = new HashMap<String, List<Object>>();
            Iterator<String> iterator = stringSet.iterator();
            while (iterator.hasNext()) {
                String k = iterator.next();
                if (getType(k) == DataType.LIST) {
                    map.put(k, getList(k));
                }
            }
            return map;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Map<String, Object>> getAllMap() {
        try {
            Set<String> stringSet = getAllKeys();
            Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
            Iterator<String> iterator = stringSet.iterator();
            while (iterator.hasNext()) {
                String k = iterator.next();
                if (getType(k) == DataType.HASH) {
                    map.put(k, getMap(k));
                }
            }
            return map;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void addList(String key, List<Object> objectList) {
        try {
            for (Object obj : objectList) {
                addList(key, obj);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Long addList(String key, Object obj) {
        try {
            return redisTemplate.boundListOps(key).rightPush(obj);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    @Override
    public Long addList(String key, Object... obj) {
        try {
            return redisTemplate.boundListOps(key).rightPushAll(obj);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getList(String key, long s, long e) {
        try {
            return redisTemplate.boundListOps(key).range(s, e);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getList(String key) {
        try {
            return redisTemplate.boundListOps(key).range(0, getListSize(key));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Long getListSize(String key) {
        try {
            return redisTemplate.boundListOps(key).size();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Long removeListValue(String key, Object object) {
        try {
            return redisTemplate.boundListOps(key).remove(0, object);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Long removeListValue(String key, Object... objects) {
        try {
            long r = 0;
            for (Object object : objects) {
                r += removeListValue(key, object);
            }
            return r;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void remove(String... key) {
        try {
            if (key != null && key.length > 0) {
                if (key.length == 1) {
                    remove(key[0]);
                } else {
                    redisTemplate.delete(Arrays.asList(key));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void removeBlear(String... blears) {
        try {
            for (String blear : blears) {
                removeBlear(blear);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        try {
            return redisTemplate.renameIfAbsent(oldKey, newKey);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public void removeBlear(String blear) {
        try {
            redisTemplate.delete(redisTemplate.keys(blear));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void removeByRegular(String... blears) {
        try {
            for (String blear : blears) {
                removeBlear(blear);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void removeByRegular(String blear) {
        try {
            Set<String> stringSet = getAllKeys();
            for (String s : stringSet) {
                if (Pattern.compile(blear).matcher(s).matches()) {
                    redisTemplate.delete(s);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void removeMapFieldByRegular(String key, String... blears) {
        try {
            for (String blear : blears) {
                removeMapFieldByRegular(key, blear);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void removeMapFieldByRegular(String key, String blear) {
        try {
            Map<String, Object> map = getMap(key);
            Set<String> stringSet = map.keySet();
            for (String s : stringSet) {
                if (Pattern.compile(blear).matcher(s).matches()) {
                    redisTemplate.boundHashOps(key).delete(s);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Long removeZSetValue(String key, Object... value) {
        try {
            return redisTemplate.boundZSetOps(key).remove(value);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void removeZSet(String key) {
        try {
            removeZSetRange(key, 0L, getZSetSize(key));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void removeZSetRange(String key, Long start, Long end) {
        try {
            redisTemplate.boundZSetOps(key).removeRange(start, end);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void setZSetUnionAndStore(String key, String key1, String key2) {
        try {
            redisTemplate.boundZSetOps(key).unionAndStore(key1, key2);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Set<Object> getZSetRange(String key) {
        try {
            return getZSetRange(key, 0, getZSetSize(key));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<Object> getZSetRange(String key, long s, long e) {
        try {
            return redisTemplate.boundZSetOps(key).range(s, e);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public Set<Object> getZSetReverseRange(String key) {
        try {
            return getZSetReverseRange(key, 0, getZSetSize(key));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<Object> getZSetReverseRange(String key, long start, long end) {
        try {
            return redisTemplate.boundZSetOps(key).reverseRange(start, end);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<Object> getZSetRangeByScore(String key, double start, double end) {
        try {
            return redisTemplate.boundZSetOps(key).rangeByScore(start, end);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<Object> getZSetReverseRangeByScore(String key, double start, double end) {
        try {
            return redisTemplate.boundZSetOps(key).reverseRangeByScore(start, end);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> getZSetRangeWithScores(String key, long start, long end) {
        try {
            return redisTemplate.boundZSetOps(key).rangeWithScores(start, end);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> getZSetReverseRangeWithScores(String key, long start, long end) {
        try {
            return redisTemplate.boundZSetOps(key).reverseRangeWithScores(start, end);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> getZSetRangeWithScores(String key) {
        try {
            return getZSetRangeWithScores(key, 0, getZSetSize(key));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> getZSetReverseRangeWithScores(String key) {
        try {
            return getZSetReverseRangeWithScores(key, 0, getZSetSize(key));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Long getZSetCountSize(String key, double sMin, double sMax) {
        try {
            return redisTemplate.boundZSetOps(key).count(sMin, sMax);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Long getZSetSize(String key) {
        try {
            return redisTemplate.boundZSetOps(key).size();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Double getZSetScore(String key, Object value) {
        try {
            return redisTemplate.boundZSetOps(key).score(value);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Double incrementZSetScore(String key, Object value, double delta) {
        try {
            return redisTemplate.boundZSetOps(key).incrementScore(value, delta);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean addZSet(String key, double score, Object value) {
        try {
            return redisTemplate.boundZSetOps(key).add(value, score);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Long addZSet(String key, TreeSet<Object> value) {
        try {
            return redisTemplate.boundZSetOps(key).add(value);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean addZSet(String key, double[] score, Object[] value) {
        try {
            if (score.length != value.length) {
                return false;
            }
            for (int i = 0; i < score.length; i++) {
                if (addZSet(key, score[i], value[i]) == false) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public void remove(String key) {
        try {
            if (exists(key)) {
                redisTemplate.delete(key);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void removeZSetRangeByScore(String key, double s, double e) {
        try {
            redisTemplate.boundZSetOps(key).removeRangeByScore(s, e);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public Boolean setSetExpireTime(String key, Long time) {
        try {
            return redisTemplate.boundSetOps(key).expire(time, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean setZSetExpireTime(String key, Long time) {
        try {
            return redisTemplate.boundZSetOps(key).expire(time, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean exists(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Object get(String key) {
        try {
            return redisTemplate.boundValueOps(key).get();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> get(String... keys) {
        try {
            List<Object> list = new ArrayList<Object>();
            for (String key : keys) {
                list.add(get(key));
            }
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getByRegular(String regKey) {
        try {
            Set<String> stringSet = getAllKeys();
            List<Object> objectList = new ArrayList<Object>();
            for (String s : stringSet) {
                if (Pattern.compile(regKey).matcher(s).matches() && getType(s) == DataType.STRING) {
                    objectList.add(get(s));
                }
            }
            return objectList;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void set(String key, Object value) {
        try {
            redisTemplate.boundValueOps(key).set(value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void set(String key, Object value, Long expireTime) {
        try {
            redisTemplate.boundValueOps(key).set(value, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Boolean setExpireTime(String key, Long expireTime) {
        try {
            return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }


    @Override
    public DataType getType(String key) {
        try {
            return redisTemplate.type(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    @Override
    public void removeMapField(String key, Object... field) {
        try {
            redisTemplate.boundHashOps(key).delete(field);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Long getMapSize(String key) {
        try {
            return redisTemplate.boundHashOps(key).size();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> getMap(String key) {
        try {
            return redisTemplate.boundHashOps(key).entries();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public <T> T getMapField(String key, String field) {
        try {
            return (T) redisTemplate.boundHashOps(key).get(field);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean hasMapKey(String key, String field) {
        try {
            return redisTemplate.boundHashOps(key).hasKey(field);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Object> getMapFieldValue(String key) {
        try {
            return redisTemplate.boundHashOps(key).values();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<Object> getMapFieldKey(String key) {
        try {
            return redisTemplate.boundHashOps(key).keys();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void addMap(String key, Map<String, Object> map) {
        try {
            redisTemplate.boundHashOps(key).putAll(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void addMap(String key, String field, Object value) {
        try {
            redisTemplate.boundHashOps(key).put(field, value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void addMap(String key, String field, Object value, long time) {
        try {
            redisTemplate.boundHashOps(key).put(field, value);
            redisTemplate.boundHashOps(key).expire(time, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void watch(String key) {
        try {
            redisTemplate.watch(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void addSet(String key, Object... obj) {
        try {
            redisTemplate.boundSetOps(key).add(obj);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Long removeSetValue(String key, Object obj) {
        try {
            return redisTemplate.boundSetOps(key).remove(obj);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Long removeSetValue(String key, Object... obj) {
        try {
            if (obj != null && obj.length > 0) {
                return redisTemplate.boundSetOps(key).remove(obj);
            }
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Long getSetSize(String key) {
        try {
            return redisTemplate.boundSetOps(key).size();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean hasSetValue(String key, Object obj) {
        try {
            Boolean boo = null;
            int t = 0;
            while (true) {
                try {
                    boo = redisTemplate.boundSetOps(key).isMember(obj);
                    break;
                } catch (Exception e) {
                    logger.error("key[" + key + "],obj[" + obj + "]判断Set中的值是否存在失败,异常信息:" + e.getMessage());
                    t++;
                }
                if (t > times) {
                    break;
                }
            }
            logger.info("key[" + key + "],obj[" + obj + "]是否存在,boo:" + boo);
            return boo;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Set<Object> getSet(String key) {
        try {
            return redisTemplate.boundSetOps(key).members();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<Object> getSetUnion(String key, String otherKey) {
        try {
            return redisTemplate.boundSetOps(key).union(otherKey);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<Object> getSetUnion(String key, Set<Object> set) {
        try {
            return redisTemplate.boundSetOps(key).union(set);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    @Override
    public Set<Object> getSetIntersect(String key, String otherKey) {
        try {
            return redisTemplate.boundSetOps(key).intersect(otherKey);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<Object> getSetIntersect(String key, Set<Object> set) {
        try {
            return redisTemplate.boundSetOps(key).intersect(set);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<String> getKeys(String key) {
        try {
            String prefix = key + "*";
            return redisTemplate.keys(prefix);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

}
