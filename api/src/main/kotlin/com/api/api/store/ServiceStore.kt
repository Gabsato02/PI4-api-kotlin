package com.api.api.store

import com.api.api.characteristics.DAOCharacteristics
import com.api.api.returnResponse
import com.api.api.trait.DAOTrait
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/store")
class ServiceStore {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(@QueryParam("search") querySearch: String?, @QueryParam("items") queryItems: Boolean = false ): Response {
        return try {
            val response = DAOStore.listAll(querySearch, queryItems)
            if (response.isEmpty()) return returnResponse("not_found", null)
            returnResponse("success", response)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/list/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun list(@PathParam("id") queryId: Int, @QueryParam("items") queryItems: Boolean = false): Response {
        return try {
            val response = DAOStore.list(queryId, queryItems)
            if (response.id == 0) return returnResponse("not_found", null)
            returnResponse("success", response)
        } catch (error: Exception) {
            println(error)
            returnResponse("not_found", null)
        }
    }

    @Path("/list/deleted")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAllDeleted(@QueryParam("search") querySearch: String? ): Response {
        return try {
            val response = DAOStore.listAllDeleted(querySearch)
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
            val response = DAOStore.listDeleted(queryId)
            if (response.id == 0) return returnResponse("not_found", null)
            returnResponse("success", response)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun insert(store: Store): Response {
        val validation = store.validate()
        if (validation != "OK") return returnResponse("bad_request", validation)
        return try {
            store.let { DAOStore.insert(it) }
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun delete(@PathParam("id") queryId: Int): Response {
        return try {
            DAOStore.delete(queryId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun update(@PathParam("id") queryId: Int, store: Store): Response {
        val validation = store.validate()
        if (validation != "OK") return returnResponse("bad_request", validation)
        return try {
            DAOStore.update(queryId, store)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/restore/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun restore(@PathParam("id") queryId: Int): Response {
        return try {
            DAOStore.restore(queryId)
            returnResponse("success", null)
        } catch (error: java.lang.Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/{storeId}/item/{itemId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun addCharacteristic(@PathParam("storeId") storeId: Int, @PathParam("itemId") itemId: Int): Response {
        return try {
            DAOStore.addItem(storeId, itemId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("error", null)
        }
    }

    @Path("/{storeId}/item/{itemId}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun removeCharacteristic(@PathParam("storeId") storeId: Int, @PathParam("itemId") itemId: Int): Response {
        return try {
            DAOStore.removeItem(storeId, itemId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("error", null)
        }
    }
}