package com.api.api.item

import java.lang.Exception
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/item")
class ServiceItem {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(@QueryParam("search") querySearch: String? ): List<Item> {
        return try {
            DAOItem.listAll(querySearch)
        } catch (error: Exception) {
            println(error)
            return emptyList()
        }
    }

    @Path("/list/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun list(@PathParam("id") queryId: Int): Item {
        return try {
            DAOItem.list(queryId)
        } catch (error: Exception) {
            println(error)
            return Item()
        }
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun insert(item: Item): String {
        return try {
            item.let { DAOItem.insert(it) }
            "Registro inserido com sucesso."
        } catch (error: Exception) {
            "Não foi possível inserir o item. Tente novamente.\n${error.message}"
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun delete(@PathParam("id") queryId: Int): String {
        return try {
            DAOItem.delete(queryId)
            "Registro apagado com sucesso."
        } catch (error: Exception) {
            "Não foi possível apagar o item. Tente novamente.\n${error.message}"
        }
    }

    @Path("/update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun update(@PathParam("id") queryId: Int, item: Item): String {
        return try {
            DAOItem.update(queryId, item)
            "Registro atualizado com sucesso."
        } catch (error: Exception) {
            "Não foi possível atualizar o item. Tente novamente.\n${error.message}"
        }
    }
}