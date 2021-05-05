package vn.zeus.web.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Iterator;
import java.util.Set;

@Slf4j
public class CacheUtils {
  private static final String SYS_CACHE = "sys-cache";
  private static final CacheManager cacheManager = SpringUtils.getBean(CacheManager.class);

  /**
   * Get SYS_CACHE cache
   *
   * @param key
   * @return
   */
  public static Object get(String key) {
    return get(SYS_CACHE, key);
  }

  /**
   * Get SYS_CACHE cache
   *
   * @param key
   * @param defaultValue
   * @return
   */
  public static Object get(String key, Object defaultValue) {
    Object value = get(key);
    return value != null ? value : defaultValue;
  }

  /**
   * Write to SYS_CACHE cache
   *
   * @param key
   * @return
   */
  public static void put(String key, Object value) {
    put(SYS_CACHE, key, value);
  }

  /**
   * Remove from SYS_CACHE cache
   *
   * @param key
   * @return
   */
  public static void remove(String key) {
    remove(SYS_CACHE, key);
  }

  /**
   * Get cache
   *
   * @param cacheName
   * @param key
   * @return
   */
  public static Object get(String cacheName, String key) {
    return getCache(cacheName).get(getKey(key));
  }

  /**
   * Get cache
   *
   * @param cacheName
   * @param key
   * @param defaultValue
   * @return
   */
  public static Object get(String cacheName, String key, Object defaultValue) {
    Object value = get(cacheName, getKey(key));
    return value != null ? value : defaultValue;
  }

  /**
   * Write cache
   *
   * @param cacheName
   * @param key
   * @param value
   */
  public static void put(String cacheName, String key, Object value) {
    Cache cache = getCache(cacheName);
    if (cache != null) {
      cache.put(getKey(key), value);
    }
  }

  /**
   * Remove from cache
   *
   * @param cacheName
   * @param key
   */
  public static void remove(String cacheName, String key) {
    getCache(cacheName).evict(getKey(key));
  }

  /**
   * Remove all from cache
   *
   * @param cacheName
   */
  public static void removeAll(String cacheName) {
    Cache cache = getCache(cacheName);
    cache.clear();
    log.info("Clear cache");
  }

  /**
   * Remove the specified key from the cache
   *
   * @param keys
   */
  public static void removeByKeys(Set<String> keys) {
    removeByKeys(SYS_CACHE, keys);
  }

  /**
   * Remove the specified key from the cache
   *
   * @param cacheName
   * @param keys
   */
  public static void removeByKeys(String cacheName, Set<String> keys) {
    for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
      remove(it.next());
    }
    log.info("Clear cache： {} => {}", cacheName, keys);
  }

  /**
   * Get the cache key name
   *
   * @param key
   * @return
   */
  private static String getKey(String key) {
    return key;
  }

  /**
   * Get a Cache, if not, display the log.
   *
   * @param cacheName
   * @return
   */
  private static Cache getCache(String cacheName) {
    if (cacheManager == null) {
      return null;
    }
    Cache cache = cacheManager.getCache(cacheName);
    if (cache == null) {
      throw new RuntimeException("Cache “" + cacheName + "” not exist.");
    }
    return cache;
  }

}
