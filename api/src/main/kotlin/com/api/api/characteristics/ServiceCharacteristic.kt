package com.api.api.characteristics

import com.api.api.returnResponse
import javax.ws.rs.core.Response
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/characteristic")
class ServiceCharacteristic {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(@QueryParam("search") querySearch: String? ): Response {
        return try {
            val response = DAOCharacteristics.listAll(querySearch)
            if (response.isEmpty()) return returnResponse("success", response)
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
            val response = DAOCharacteristics.list(queryId)
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
            val response = DAOCharacteristics.listAllDeleted(querySearch)
            if (response.isEmpty()) return returnResponse("success", response)
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
            val response = DAOCharacteristics.listDeleted(queryId)
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
    fun insert(characteristic: Characteristic): Response {
        val validation = characteristic.validate()
        if (validation != "OK") return returnResponse("bad_request", validation)
        return try {
            characteristic.let { DAOCharacteristics.insert(it) }
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
            DAOCharacteristics.delete(queryId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun update(@PathParam("id") queryId: Int, characteristic: Characteristic): Response {
        val validation = characteristic.validate()
        if (validation != "OK") return returnResponse("bad_request", validation)
        return try {
            DAOCharacteristics.update(queryId, characteristic)
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
            DAOCharacteristics.restore(queryId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }
}