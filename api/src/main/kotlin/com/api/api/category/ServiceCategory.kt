package com.api.api.category

import com.api.api.returnResponse
import java.lang.Exception
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/category")
class ServiceCategory {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(@QueryParam("search") querySearch: String?, @QueryParam("items") queryItems: Boolean = false ): Response {
        return try {
            val response = DAOCategory.listAll(querySearch, queryItems)
            if (response.isEmpty()) return returnResponse("not_found", null)
            returnResponse("success", response)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/list/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun list(@PathParam("id") queryId: Int,  @QueryParam("items") queryItems: Boolean = false): Response {
        return try {
            val response = DAOCategory.list(queryId, queryItems)
            if (response.id == 0) return returnResponse("not_found", null)
            returnResponse("success", response)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun insert(category: Category): Response {
        val validation = category.validate()
        if (validation != "OK") return returnResponse("bad_request", validation)
        return try {
            category.let { DAOCategory.insert(it) }
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun delete(@PathParam("id") queryId: Int): Response {
        return try {
            DAOCategory.delete(queryId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("error", null)
        }
    }

    @Path("/update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun update(@PathParam("id") queryId: Int, category: Category): Response {
        val validation = category.validate()
        if (validation != "OK") return returnResponse("bad_request", validation)

        return try {
            DAOCategory.update(queryId, category)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("error", null)
        }
    }

    @Path("/restore/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun restore(@PathParam("id") queryId: Int): Response {
        return try {
            DAOCategory.restore(queryId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("error", null)
        }
    }
}