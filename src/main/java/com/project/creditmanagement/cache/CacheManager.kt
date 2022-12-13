package com.project.creditmanagement.cache

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import java.util.LinkedHashMap
import java.util.stream.Collectors


@Component
public class CacheManager {



    private val logger = LogManager.getLogger()

    val trueCache = linkedMapOf<String?,LinkedHashMap<String?,Any>>()

    fun cacheAll(cacheName: String?, entities: List<Cache>)  {
        val cache = LinkedHashMap<String?, Any>()
        entities.stream().forEach { entity: Cache -> cache[entity.key] = entity }
        logger.info("{} cached with [{}] rows",cacheName,entities.size )
        trueCache[cacheName] = cache
    }

    fun cache(name: String?,k: String?,v: Any) {
        if(!trueCache.containsKey(name)){
            val inCache = linkedMapOf<String?,Any>()
            trueCache[name] = inCache
        }
        trueCache[name]?.put(k,v)
    }

    fun get(name: String?,k: String?): Cache?{
        return trueCache.get(name)?.get(k) as Cache
    }

    fun getAll(name:String?): MutableList<Any>? {
        return trueCache[name]?.values?.stream()?.collect(Collectors.toList())
    }

}