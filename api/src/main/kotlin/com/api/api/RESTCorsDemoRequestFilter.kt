package com.api.api

import javax.ws.rs.container.PreMatching
import javax.ws.rs.container.ContainerRequestFilter
import kotlin.Throws
import java.io.IOException
import javax.ws.rs.container.ContainerRequestContext
import java.util.logging.Logger
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Provider
@PreMatching
class RESTCorsDemoRequestFilter : ContainerRequestFilter {
    @Throws(IOException::class)
    override fun filter(requestCtx: ContainerRequestContext) {
        if (requestCtx.request.method == "OPTIONS") {
            requestCtx.abortWith(Response.status(Response.Status.OK).build())
        }
    }

    companion object {
        private val log = Logger.getLogger(RESTCorsDemoRequestFilter::class.java.name)
    }
}