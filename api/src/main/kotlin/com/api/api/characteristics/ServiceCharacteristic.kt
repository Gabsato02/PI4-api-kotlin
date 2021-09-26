package com.api.api.characteristics

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/characteristic")
class ServiceCharacteristic {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(@QueryParam("search") querySearch: String? ): List<Characteristic> {
        return try {
            DAOCharacteristics.listAll(querySearch)
        } catch (error: Exception) {
            return emptyList()
        }
    }

    @Path("/list/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun list(@PathParam("id") queryId: Int): Characteristic {
        return try {
            DAOCharacteristics.list(queryId)
        } catch (error: Exception) {
            return Characteristic()
        }
    }

    @Path("/list/deleted")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAllDeleted(@QueryParam("search") querySearch: String? ): List<Characteristic> {
        return try {
            DAOCharacteristics.listAllDeleted(querySearch)
        } catch (error: Exception) {
            return emptyList()
        }
    }

    @Path("/list/deleted/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listDeleted(@PathParam("id") queryId: Int): Characteristic {
        return try {
            DAOCharacteristics.listDeleted(queryId)
        } catch (error: Exception) {
            return Characteristic()
        }
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun insert(characteristic: Characteristic?): String {
        return try {
            characteristic?.let { DAOCharacteristics.insert(it) }
            "Registro inserido com sucesso."
        } catch (error: Exception) {
            "Não foi possível apagar a característica. Tente novamente.\n${error.message}"
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun delete(@PathParam("id") queryId: Int): String {
        return try {
            DAOCharacteristics.delete(queryId)
            "Registro apagado com sucesso."
        } catch (error: Exception) {
            "Não foi possível apagar a característica. Tente novamente.\n${error.message}"
        }
    }

    @Path("/update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun update(@PathParam("id") queryId: Int, characteristic: Characteristic): String {
        return try {
            DAOCharacteristics.update(queryId, characteristic)
            "Registro atualizado com sucesso."
        } catch (error: Exception) {
            "Não foi possível atualizar a característica. Tente novamente.\n${error.message}"
        }
    }

    @Path("/restore/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun restore(@PathParam("id") queryId: Int): String {
        return try {
            DAOCharacteristics.restore(queryId)
            "Registro restaurado com sucesso."
        } catch (error: Exception) {
            "Não foi possível restaurar a característica. Tente novamente.\n${error.message}"
        }
    }
}