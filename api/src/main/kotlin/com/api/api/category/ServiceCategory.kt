package com.api.api.category

import java.lang.Exception
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/category")
class ServiceCategory {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(@QueryParam("search") querySearch: String?, @QueryParam("items") queryItems: Boolean = false ): List<Category> {
        return try {
            DAOCategory.listAll(querySearch, queryItems)
        } catch (error: Exception) {
            println(error)
            return emptyList()
        }
    }

    @Path("/list/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun list(@PathParam("id") queryId: Int,  @QueryParam("items") queryItems: Boolean = false): Category {
        return try {
            DAOCategory.list(queryId, queryItems)
        } catch (error: Exception) {
            println(error)
            return Category()
        }
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun insert(category: Category): String {
        return try {
            category.let { DAOCategory.insert(it) }
            "Registro inserido com sucesso."
        } catch (error: Exception) {
            "Não foi possível inserir a categoria. Tente novamente.\n${error.message}"
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun delete(@PathParam("id") queryId: Int): String {
        return try {
            DAOCategory.delete(queryId)
            "Registro apagado com sucesso."
        } catch (error: Exception) {
            "Não foi possível apagar a categoria. Tente novamente.\n${error.message}"
        }
    }

    @Path("/update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun update(@PathParam("id") queryId: Int, category: Category): String {
        return try {
            DAOCategory.update(queryId, category)
            "Registro atualizado com sucesso."
        } catch (error: Exception) {
            "Não foi possível atualizar a categoria. Tente novamente.\n${error.message}"
        }
    }
}