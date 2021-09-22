package com.api.api.item

import com.api.api.DB
import com.api.api.formatDateToTimestamp
import java.text.SimpleDateFormat
import java.util.*

object DAOItem {
    fun listAll(): List<Item> {
        val sql = "SELECT * FROM item"
        val itemList = arrayListOf<Item>()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val result = preparedStatement.executeQuery()

            while(result.next()) {
                var item = Item()
                item.id = result.getInt("id")
                item.name = result.getString("name")
                item.price = result.getInt("price")
                item.description = result.getString("description")
                item.volume = result.getString("volume")
                // NOTA: substituir pelo nome da categoria
                item.category_id = result.getInt("category_id")
                item.created_at = result.getString("created_at")
                item.updated_at = result.getString("updated_at")
                item.deleted_at = result.getString("deleted_at")
                itemList.add(item)
            }
        }
        return itemList
    }

    fun list(id: Int): Item {
        val sql = "SELECT * FROM item WHERE id = $id"
        val item = Item()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val result = preparedStatement.executeQuery()

            if(result.next()) {
                item.id = result.getInt("id")
                item.name = result.getString("name")
                item.price = result.getInt("price")
                item.description = result.getString("description")
                item.volume = result.getString("volume")
                item.category_id = result.getInt("category_id")
                item.created_at = result.getString("created_at")
                item.updated_at = result.getString("updated_at")
                item.deleted_at = result.getString("deleted_at")
            }
        }
        return item
    }

    fun insert(item: Item) {
        val sql = "INSERT INTO item (name, price, description, volume, category_id) VALUES (?, ?, ?, ?, ?)"
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)

            preparedStatement.setString(1, item.name)
            preparedStatement.setInt(2, item.price)
            preparedStatement.setString(3, item.description)
            preparedStatement.setString(4, item.volume)
            preparedStatement.setInt(5, item.category_id)

            preparedStatement.execute()
        }
    }

    fun delete(id: Int) {
        val date = formatDateToTimestamp(Date())

        val sql = "UPDATE item SET deleted_at = '$date' WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.execute()
        }
    }

    fun update(id: Int, item: Item) {
        val currentData = list(id)
        val name = if (item.name.isNullOrBlank()) currentData.name else item.name
        val price = if (item.price == 0) currentData.price else item.price
        val description = if (item.description.isNullOrBlank()) currentData.description else item.description
        val volume = if (item.volume.isNullOrBlank()) currentData.volume else item.volume
        val categoryId = if (item.category_id == 0) currentData.category_id else item.category_id

        val sql = "UPDATE item SET name = ?, price = ?, description = ?, volume = ?, category_id = ? WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)

            preparedStatement.setString(1, name)
            preparedStatement.setInt(2, price)
            preparedStatement.setString(3, description)
            preparedStatement.setString(4, volume)
            preparedStatement.setInt(5, categoryId)

            preparedStatement.execute()
        }
    }
}