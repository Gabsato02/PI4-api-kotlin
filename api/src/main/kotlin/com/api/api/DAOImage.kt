package com.api.api

import java.util.*

object DAOImage {
    fun list(type: String, id: Int): Image {
        val sql = "SELECT * FROM $type WHERE id = $id"
        val image = Image()
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val result = preparedStatement.executeQuery()
            if(result.next()) {
                val imageString = result.getString("name")
                val byte = Base64.getDecoder().decode(imageString)
                image.image = byte
            }
        }
        return image
    }
}