package com.project.creditmangement.cache

import mu.KotlinLogging
import org.apache.logging.log4j.LogManager
import java.util.logging.Logger

abstract class Cache {

    private val logger = LogManager.getLogger()

    private var cacheManager: CacheManager? = null
    fun cache(entities: List<Cache?>?) {
        cacheManager!!.cacheAll(key, entities as List<Cache>)
        logger.info("{} cached with [{}] rows",key,entities.size )

    }

    abstract val key: String?
}


