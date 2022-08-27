package com.project.creditmangement.cache

import org.apache.logging.log4j.LogManager
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import java.util.Collections
import java.util.LinkedHashMap
import java.util.Objects
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors


@Component
public class CacheManager {



    private val logger = LogManager.getLogger()

    val trueCache = linkedMapOf<String?,LinkedHashMap<String?,Any>>()

    fun cacheAll(cacheName: String?, entities: List<Cache>)  {
        val cache = LinkedHashMap<String?, Any>()
        entities.stream().forEach { entity: Cache -> cache.put(entity.key,entity) }
        logger.info("{} cached with [{}] rows",cacheName,entities.size )
        trueCache.put(cacheName,cache)
    }

    fun cache(name: String?,k: String?,v: Any) {
        if(!trueCache.containsKey(name)){
            val inCache = linkedMapOf<String?,Any>()
            trueCache.put(name,inCache)
        }
        trueCache.get(name)?.put(k,v)
    }

    fun get(name: String?,k: String?): Cache?{
        return trueCache.get(name)?.get(k) as Cache
    }

    fun getAll(name:String?): MutableList<Any>? {
        return trueCache.get(name)?.values?.stream()?.collect(Collectors.toList())
    }

}