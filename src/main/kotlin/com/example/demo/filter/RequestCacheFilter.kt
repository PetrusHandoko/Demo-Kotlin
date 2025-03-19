package com.example.demo.filter

import com.example.demo.model.TTLCache
import jakarta.servlet.*

import jakarta.servlet.http.HttpServletResponse
import org.apache.catalina.core.ApplicationFilterConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.PrintWriter
import kotlin.time.DurationUnit
import kotlin.time.toDuration


@Component
@Order(2)
@ConditionalOnProperty(value = ["demo.use.request.cache"], havingValue = "true")
class RequestCacheFilter : Filter {

    @Value("\${demo.request.cache.ttl}")
    private val cacheTTLDuration  = 0L

    private lateinit var ttlCache : TTLCache<String, String>

    override fun init(p0: FilterConfig?) {
        println(" TTL: $cacheTTLDuration ")
        ttlCache = TTLCache(cacheTTLDuration.toDuration(DurationUnit.MINUTES))
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

        val requestZip = request.parameterMap["zip"]?.get(0) as String
        val result = ttlCache.get(requestZip)
        if ( result != null){
            response.setContentType("application/json")
            val writer: PrintWriter = response.getWriter()
            writer.write(result)
            writer.flush()
            return  // Stop
        }

        val responseWrapper = ContentCachingResponseWrapper(response as HttpServletResponse)
        chain.doFilter(request, responseWrapper)
        ttlCache.put(requestZip, String(responseWrapper.contentAsByteArray))
        responseWrapper.copyBodyToResponse()
    }

    override fun destroy() {
    }
}
