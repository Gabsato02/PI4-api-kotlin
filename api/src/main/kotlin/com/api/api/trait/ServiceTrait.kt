package com.api.api.trait

import com.api.api.returnResponse
import java.lang.Exception
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/trait")
class ServiceTrait {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(@QueryParam("search") querySearch: String? ): Response {
        return try {
            val response = DAOTrait.listAll(querySearch)
            if (response.isEmpty()) return returnResponse("not_found", null)
            returnResponse("success", response)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/list/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun list(@PathParam("id") queryId: Int): Response {
        return try {
            val response = DAOTrait.list(queryId)
            if (response.id == 0) return returnResponse("not_found", null)
            returnResponse("success", response)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/list/deleted")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAllDeleted(@QueryParam("search") querySearch: String? ): Response {
        return try {
            val response =  DAOTrait.listAllDeleted(querySearch)
            if (response.isEmpty()) return returnResponse("not_found", null)
            returnResponse("success", response)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/list/deleted/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listDeleted(@PathParam("id") queryId: Int): Response {
        return try {
            val response = DAOTrait.listDeleted(queryId)
            if (response.id == 0) return returnResponse("not_found", null)
            returnResponse("success", response)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun insert(trait: Trait): Response {
        val validation = trait.validate()
        if (validation != "OK") return returnResponse("bad_request", validation)
        return try {
            trait.let { DAOTrait.insert(it) }
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun delete(@PathParam("id") queryId: Int): Response {
        return try {
            DAOTrait.delete(queryId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun update(@PathParam("id") queryId: Int, trait: Trait): Response {
        val validation = trait.validate()
        if (validation != "OK") return returnResponse("bad_request", validation)
        return try {
            DAOTrait.update(queryId, trait)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/restore/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun restore(@PathParam("id") queryId: Int): Response {
        return try {
            DAOTrait.restore(queryId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }
}