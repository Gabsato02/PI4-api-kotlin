package com.api.api

import com.api.api.category.DAOCategory
import java.lang.Exception
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/image")
class ServiceImage {

    @Path("/{type}/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun list(@PathParam("type") type: String, @PathParam("id") queryId: Int): Image {
        return try {
            return DAOImage.list(type, queryId)
            // returnResponse("success", response)
        } catch (error: Exception) {
            Image()
        }
    }
}