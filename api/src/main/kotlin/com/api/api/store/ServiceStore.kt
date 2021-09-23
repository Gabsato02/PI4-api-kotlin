package com.api.api.store

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/store")
class ServiceStore {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(@QueryParam("search") querySearch: String? ): List<Store> {
        return try {
            DAOStore.listAll(querySearch)
        } catch (error: Exception) {
            return emptyList()
        }
    }

    @Path("/list/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun list(@PathParam("id") queryId: Int): Store {
        return try {
            DAOStore.list(queryId)
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
            "Não foi possível atualizar o item. Tente novamente.\n${error.message}"
        }
    }

//    @Path("/{storeId}/addItem")
}