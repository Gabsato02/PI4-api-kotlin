package com.api.api.store

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/store")
class ServiceStore {

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun insert(store: Store?) {
        store?.let { DAOStore.insert(it) }
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun list(): List<Store> {
        return DAOStore.list()
    }

    @Path("/show/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun show(@PathParam("id") queryId: Int): Store {
        return DAOStore.show(queryId)
    }
}