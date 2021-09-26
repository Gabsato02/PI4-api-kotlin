package com.api.api.trait

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/trait")
class ServiceTrait {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(@QueryParam("search") querySearch: String? ): List<Trait> {
        return try {
            DAOTrait.listAll(querySearch)
        } catch (error: Exception) {
            return emptyList()
        }
    }

    @Path("/list/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun list(@PathParam("id") queryId: Int): Trait {
        return try {
            DAOTrait.list(queryId)
        } catch (error: Exception) {
            return Trait()
        }
    }

    @Path("/list/deleted")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAllDeleted(@QueryParam("search") querySearch: String? ): List<Trait> {
        return try {
            DAOTrait.listAllDeleted(querySearch)
        } catch (error: Exception) {
            return emptyList()
        }
    }

    @Path("/list/deleted/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listDeleted(@PathParam("id") queryId: Int): Trait {
        return try {
            DAOTrait.listDeleted(queryId)
        } catch (error: Exception) {
            return Trait()
        }
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun insert(trait: Trait?): String {
        return try {
            trait?.let { DAOTrait.insert(it) }
            "Registro inserido com sucesso."
        } catch (error: Exception) {
            "Não foi possível apagar o traço. Tente novamente.\n${error.message}"
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun delete(@PathParam("id") queryId: Int): String {
        return try {
            DAOTrait.delete(queryId)
            "Registro apagado com sucesso."
        } catch (error: Exception) {
            "Não foi possível apagar o traço. Tente novamente.\n${error.message}"
        }
    }

    @Path("/update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun update(@PathParam("id") queryId: Int, trait: Trait): String {
        return try {
            DAOTrait.update(queryId, trait)
            "Registro atualizado com sucesso."
        } catch (error: Exception) {
            "Não foi possível atualizar o traço. Tente novamente.\n${error.message}"
        }
    }

    @Path("/restore/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun restore(@PathParam("id") queryId: Int): String {
        return try {
            DAOTrait.restore(queryId)
            "Registro restaurado com sucesso."
        } catch (error: Exception) {
            "Não foi possível restaurar o traço. Tente novamente.\n${error.message}"
        }
    }
}