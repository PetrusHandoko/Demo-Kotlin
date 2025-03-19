package com.example.demo.model

import com.example.demo.config.AppConfig
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

import kotlin.concurrent.timer
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toJavaDuration


class TTLCache<K, V>(private val ttl: Duration = 30L.toDuration(DurationUnit.MINUTES)) {
    private val map = ConcurrentHashMap<K, CacheEntry<V>>()

    init {
        timer(daemon = true, period = ttl.inWholeMilliseconds) {
            cleanup()
        }
    }

    fun put(key: K, value: V) {
        map[key] = CacheEntry(value, Instant.now().plus(ttl.toJavaDuration()))
    }

    fun get(key: K): V? {
        val entry = map[key]
        return if (entry != null && entry.isValid()) {
            entry.value
        } else {
            map.remove(key)
            null
        }
    }

    fun remove(key: K) {
        map.remove(key)
    }

    private fun cleanup() {
        val now = Instant.now()
        map.entries.removeIf { (_, entry) -> !entry.isValid(now) }
    }

    private data class CacheEntry<V>(val value: V, val expiryTime: Instant) {
        fun isValid(now: Instant = Instant.now()) = now.isBefore(expiryTime)
    }
}