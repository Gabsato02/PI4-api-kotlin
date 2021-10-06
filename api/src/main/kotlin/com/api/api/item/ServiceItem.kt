package com.api.api.item

import com.api.api.returnResponse
import java.lang.Exception
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/item")
class ServiceItem {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(@QueryParam("search") querySearch: String? ): Response {
        return try {
            val response = DAOItem.listAll(querySearch)
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
            val response = DAOItem.list(queryId)
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
    fun insert(item: Item): Response {
        val validation = item.validate()
        if (validation != "OK") return returnResponse("bad_request", validation)

        return try {
            item.let { DAOItem.insert(it) }
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("error", null)
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun delete(@PathParam("id") queryId: Int): Response {
        return try {
            DAOItem.delete(queryId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("error", null)
        }
    }

    @Path("/update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun update(@PathParam("id") queryId: Int, item: Item): Response {
        val validation = item.validate()
        if (validation != "OK") return returnResponse("bad_request", validation)

        return try {
            DAOItem.update(queryId, item)
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
            DAOItem.restore(queryId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("error", null)
        }
    }

    @Path("/{itemId}/trait/{traitId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun addTrait(@PathParam("itemId") itemId: Int, @PathParam("traitId") traitId: Int): Response {
        return try {
            DAOItem.addTrait(itemId, traitId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("", null)
        }
    }

    @Path("/{itemId}/trait/{traitId}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun removeTrait(@PathParam("itemId") itemId: Int, @PathParam("traitId") traitId: Int): Response {
        return try {
            DAOItem.removeTrait(itemId, traitId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("error", null)
        }
    }

    @Path("/{itemId}/characteristic/{characteristicId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun addCharacteristic(@PathParam("itemId") itemId: Int, @PathParam("characteristicId") characteristicId: Int): Response {
        return try {
            DAOItem.addCharacteristic(itemId, characteristicId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("error", null)
        }
    }

    @Path("/{itemId}/characteristic/{characteristicId}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun removeCharacteristic(@PathParam("itemId") itemId: Int, @PathParam("characteristicId") characteristicId: Int): Response {
        return try {
            DAOItem.removeCharacteristic(itemId, characteristicId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("error", null)
        }
    }
}