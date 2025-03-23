package com.example.demo.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test


import java.lang.Thread.sleep
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class TTLCacheTest {

    private val ttlCache = TTLCache<String, String>(1000L.toDuration(DurationUnit.MILLISECONDS))

    @Test
    fun `Test cache with ttl`() {
        ttlCache.put("Hello", "World")
        assertEquals("World", ttlCache.get("Hello"))
        sleep(1000L)
        assertNull(ttlCache.get("Hello"))
    }

    @Test
    fun `Test clean` (){
        ttlCache.remove("HelloWorld")
        assertNull(ttlCache.get("HelloWorld"))
    }


}

