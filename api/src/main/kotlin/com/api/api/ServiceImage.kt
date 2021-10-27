package com.api.api

import com.api.api.category.DAOCategory
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.lang.Exception
import javax.imageio.ImageIO
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/image")
class ServiceImage {

    @Path("/{type}/{id}")
    @GET
    @Produces("image/jpeg")
    fun list(@PathParam("type") type: String, @PathParam("id") queryId: Int): ByteArray {
        return try {
            val image = DAOImage.list(type, queryId)
            val baos = ByteArrayOutputStream()
            ImageIO.write(image, "png", baos)
            baos.toByteArray()
        } catch (error: Exception) {
            println(error)
            return byteArrayOf()
        }
    }
}