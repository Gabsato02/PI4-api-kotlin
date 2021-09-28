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

    @Path("/restore/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun restore(@PathParam("id") queryId: Int): String {
        return try {
            DAOItem.restore(queryId)
            "Item restaurado com sucesso."
        } catch (error: Exception) {
            "Não foi possível restaurar o item. Tente novamente.\n${error.message}"
        }
    }

    @Path("/{itemId}/trait/{traitId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun addTrait(@PathParam("itemId") itemId: Int, @PathParam("traitId") traitId: Int): String {
        return try {
            DAOItem.addTrait(itemId, traitId)
            "Traço inserido com sucesso."
        } catch (error: Exception) {
            "Não foi possível inserir o traço. Tente novamente.\n${error.message}"
        }
    }

    @Path("/{itemId}/trait/{traitId}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun removeTrait(@PathParam("itemId") itemId: Int, @PathParam("traitId") traitId: Int): String {
        return try {
            DAOItem.removeTrait(itemId, traitId)
            "Traço removido com sucesso."
        } catch (error: Exception) {
            "Não foi possível remover o traço. Tente novamente.\n${error.message}"
        }
    }

    @Path("/{itemId}/characteristic/{characteristicId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun addCharacteristic(@PathParam("itemId") itemId: Int, @PathParam("characteristicId") characteristicId: Int): String {
        return try {
            DAOItem.addCharacteristic(itemId, characteristicId)
            "Característica inserida com sucesso."
        } catch (error: Exception) {
            "Não foi possível inserir o característica. Tente novamente.\n${error.message}"
        }
    }

    @Path("/{itemId}/characteristic/{characteristicId}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun removeCharacteristic(@PathParam("itemId") itemId: Int, @PathParam("characteristicId") characteristicId: Int): String {
        return try {
            DAOItem.removeCharacteristic(itemId, characteristicId)
            "Característica removida com sucesso."
        } catch (error: Exception) {
            "Não foi possível remover o característica. Tente novamente.\n${error.message}"
        }
    }
}