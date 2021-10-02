package com.api.api.store

import com.api.api.returnResponse
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/store")
class ServiceStore {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(@QueryParam("search") querySearch: String?, @QueryParam("items") queryItems: Boolean = false ): List<Store> {
        return try {
            DAOStore.listAll(querySearch, queryItems)
        } catch (error: Exception) {
            return emptyList()
        }
    }

    @Path("/list/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun list(@PathParam("id") queryId: Int, @QueryParam("items") queryItems: Boolean = false): Store {
        return try {
            DAOStore.list(queryId, queryItems)
        } catch (error: Exception) {
            println(error)
            return Store()
        }
    }

    @Path("/list/deleted")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAllDeleted(@QueryParam("search") querySearch: String? ): List<Store> {
        return try {
            DAOStore.listAllDeleted(querySearch)
        } catch (error: Exception) {
            return emptyList()
        }
    }

    @Path("/list/deleted/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listDeleted(@PathParam("id") queryId: Int): Store {
        return try {
            DAOStore.listDeleted(queryId)
        } catch (error: Exception) {
            return Store()
        }
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun insert(store: Store?): String {
        return try {
            store?.let { DAOStore.insert(it) }
            "Registro inserido com sucesso."
        } catch (error: Exception) {
            "Não foi possível apagar a loja. Tente novamente.\n${error.message}"
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun delete(@PathParam("id") queryId: Int): String {
        return try {
            DAOStore.delete(queryId)
            "Registro apagado com sucesso."
        } catch (error: Exception) {
            "Não foi possível apagar a loja. Tente novamente.\n${error.message}"
        }
    }

    @Path("/update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun update(@PathParam("id") queryId: Int, store: Store): String {
        return try {
            DAOStore.update(queryId, store)
            "Registro atualizado com sucesso."
        } catch (error: Exception) {
            "Não foi possível atualizar a loja. Tente novamente.\n${error.message}"
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