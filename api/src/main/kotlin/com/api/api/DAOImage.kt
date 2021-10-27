package com.api.api

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
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
                val image = result.getString("image")
                byte = Base64.getMimeDecoder().decode(image)
                println(byte)
            }
        }
        val b = ByteArrayInputStream(byte)
        return ImageIO.read(b)
    }
}