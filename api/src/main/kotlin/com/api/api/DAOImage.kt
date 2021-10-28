package com.api.api

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*
import javax.imageio.ImageIO

object DAOImage {
    fun list(type: String, id: Int): BufferedImage {
        val sql = "SELECT * FROM $type WHERE id = $id"
        var byte = byteArrayOf()
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val result = preparedStatement.executeQuery()
            if (result.next()) {
                var imageString = result.getString("image")
                val imageType = if ("png" in imageString) "png" else "jpeg"
                val sanitizedString = imageString.replace("data:image/$imageType;base64,", "")
                byte = Base64.getMimeDecoder().decode(sanitizedString)
            }
        }
        val b = ByteArrayInputStream(byte)
        return ImageIO.read(b)
    }
}