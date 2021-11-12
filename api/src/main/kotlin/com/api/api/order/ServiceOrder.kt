package com.api.api.order

import com.api.api.returnResponse
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/order")
class ServiceOrder {

    @Path("/list/user/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun list(@PathParam("userId") userId: Int, @QueryParam("store") storeId: Int): Response {
        return try {
            val response = DAOOrder.list(userId, storeId)
            if (response.isEmpty()) return returnResponse("not_found", null)
            returnResponse("success", response)
        } catch (error: Exception) {
            returnResponse("error", null)
        }
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    fun insert(order: Order): Response {
        val validation = order.validate()
        if (validation != "OK") return returnResponse("bad_request", validation)

        return try {
            val response = order.let { DAOOrder.insert(it) }
            returnResponse("success", response)
        } catch (error: Exception) {
            returnResponse("error", null)
        }
    }

    @Path("/add-item")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun addItem(orderItem: OrderItem): Response {
        val validation = orderItem.validate()
        if (validation != "OK") return returnResponse("bad_request", validation)

        return try {
            orderItem.let { DAOOrder.addItem(it) }
            returnResponse("success", null)
        } catch (error: java.lang.Exception) {
            returnResponse("error", null)
        }
    }
}