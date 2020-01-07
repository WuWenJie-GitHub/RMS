package org.tangdao.modules.state.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.tangdao.common.cache.JedisUtils;
import org.tangdao.common.collect.ListUtils;
import org.tangdao.common.collect.MapUtils;
import org.tangdao.common.lang.ObjectUtils;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.mapper.JsonMapper;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @ClassName: CacheService.java
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ruyang
 * @date 2019年3月8日 上午8:33:23
 * 
 */
@Service
public class CacheService {
	
	@Autowired
	private JedisUtils jedisUtils;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 *  检索缓存
	 * @param pattern
	 * @return
	 */
	public List<Map<String, Object>> keys(String pattern) {
		if(StringUtils.isEmpty(pattern)) {
			pattern = "*";
		}
		List<Map<String, Object>> list = ListUtils.newArrayList();
		redisTemplate.keys(pattern).forEach((key) -> {
			Map<String, Object> e = MapUtils.newHashMap();
			e.put("key", key);
			e.put("dataType", redisTemplate.type(key).code());
			list.add(e);
		});
		return list;
	}
	
	/**
	 *  获取缓存结果
	 * @param key
	 * @param type
	 * @param args
	 * @return
	 * 
	 *  如果时list 参数 args[0]开始，args[1]结束
	 */
	public List<Map<String, Object>> values(String key, String type, long ...args) {
		List<Map<String, Object>> list = ListUtils.newLinkedList();
		if(DataType.STRING.code().equals(type)) {
			Map<String, Object> map = MapUtils.newHashMap();
			map.put("value", jedisUtils.get(key));
			list.add(map);
		} else if(DataType.HASH.code().equals(type)) {
			Map<Object, Object> map = jedisUtils.getHashEntries(key);
			map.entrySet().forEach(item->{
				Map<String, Object> e = MapUtils.newHashMap();
				e.put("key", (String)item.getKey());
				e.put("value", obj2String(item.getValue()));
				list.add(e);
			});
		}else if(DataType.SET.code().equals(type)) {
			Set<Object> sets = jedisUtils.members(key);
			sets.forEach(obj->{
				Map<String, Object> e = MapUtils.newHashMap();
				e.put("value", obj2String(obj));
				list.add(e);
			});
		}else if(DataType.LIST.code().equals(type)) {
			List<Object> ranges = jedisUtils.range(key, args[0], args[1]);
			ranges.forEach(obj->{
				Map<String, Object> e = MapUtils.newHashMap();
				e.put("value", obj2String(obj));
				list.add(e);
			});
		}
		return list;
	}
	
	/**
	 *  删除key
	 * @param keys
	 */
	public void delete(String ...keys) {
		jedisUtils.delete(keys);
	}
	
	/**
	 * 移除hashKey
	 * @param key
	 * @param hashKeys
	 */
	public void removeHashKey(String key, Object ...hashKeys) {
		jedisUtils.removeHash(key, hashKeys);
	}
	
	/**
	 *  缓存过期时间
	 * @param key
	 * @return
	 */
	public long getTime(String key) {
		return jedisUtils.getTime(key);
	}
	
	/**
	 * 缓存大小
	 * @param key
	 * @return
	 */
	public long getSize(String key) {
		return jedisUtils.size(key);
	}
	
	@SuppressWarnings("unchecked")
	private String obj2String(Object obj) {
		String result = null;
		try {
			if (obj instanceof Collection) {
				obj = MapUtils.toMapList((Collection<Object>) obj);
			} else {
				if (!(obj instanceof Map || obj instanceof String || obj instanceof Number)) {
					obj = MapUtils.toMap(obj);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			result = new JsonMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonProcessingException je) {
			je.printStackTrace();
		}
		if (result == null) {
			result = ObjectUtils.toString2(obj);
		}
		return StringUtils.abbr(result, 512000);
	}
}
