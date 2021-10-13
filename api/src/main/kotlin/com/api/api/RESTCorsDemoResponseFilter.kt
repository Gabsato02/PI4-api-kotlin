package com.api.api

import javax.ws.rs.container.PreMatching
import javax.ws.rs.container.ContainerResponseFilter
import kotlin.Throws
import java.io.IOException
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import java.util.logging.Logger
import javax.ws.rs.ext.Provider

@Provider
@PreMatching
class RESTCorsDemoResponseFilter : ContainerResponseFilter {
    @Throws(IOException::class)
    override fun filter(requestCtx: ContainerRequestContext, responseCtx: ContainerResponseContext) {
        responseCtx.headers.add("Access-Control-Allow-Origin", "*")
        responseCtx.headers.add(
            "Access-Control-Allow-Headers",
            "origin, content-type, accept, authorization"
        )
        responseCtx.headers.add("Access-Control-Allow-Credentials", "true")
        responseCtx.headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
    }

    companion object {
        private val log = Logger.getLogger(RESTCorsDemoResponseFilter::class.java.name)
    }
}