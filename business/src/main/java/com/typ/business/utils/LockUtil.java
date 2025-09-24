package com.typ.business.utils;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LockUtil {
    private static final Logger log = LoggerFactory.getLogger(LockUtil.class);


    private static final LoadingCache<String, ReentrantLock> cache = Caffeine.newBuilder()
            .expireAfterWrite(15, TimeUnit.SECONDS)
            .build(key -> new ReentrantLock());

    public static boolean tryLock(String key) {
        try {
            return Objects.requireNonNull(cache.get(key)).tryLock(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("锁获取失败，检查加锁方法是否执行过长：key - {}", key);
            return false;
        }
    }

    public static void unlock(String key) {
        Objects.requireNonNull(cache.get(key)).unlock();
    }
}
