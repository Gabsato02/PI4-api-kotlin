package com.api.api.user

import com.api.api.CustomResponse
import com.api.api.returnResponse
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/user")
class ServiceUser {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(@QueryParam("search") querySearch: String? ): Response {
        return try {
            val response = DAOUser.listAll(querySearch)
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
            val response = DAOUser.list(queryId)
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
    fun insert(user: User): Response {
        val validation = user.validate()
        if (validation != "OK") return returnResponse("bad_request", validation)
        return try {
            user.let { DAOUser.insert(it) }
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
            DAOUser.delete(queryId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun update(@PathParam("id") queryId: Int, user: User): Response {
        val validation = user.validate()
        if (validation != "OK") return returnResponse("bad_request", validation)
        return try {
            DAOUser.update(queryId, user)
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
            DAOUser.restore(queryId)
            returnResponse("success", null)
        } catch (error: Exception) {
            returnResponse("not_found", null)
        }
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun login(login: Login): Response {
        return try {
            val response = login.let { DAOUser.login(it) }
            val customResponse = CustomResponse()
            customResponse.message = response
            Response.status(Response.Status.OK).entity(customResponse).build()
        } catch (error: Exception) {
            returnResponse("unauthorized", null)
        }
    }
}