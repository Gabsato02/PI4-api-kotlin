package com.api.api.item

import com.api.api.DB
import java.text.SimpleDateFormat
import java.util.*

object DAOItem {
    // TODO adicionar a recuperação de datas com as queries de listagem + função de update
    fun listAll(): List<Item> {
        val sql = "SELECT * FROM item WHERE deleted_at IS NULL"
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
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.format(Date())

        val sql = "UPDATE item SET deleted_at = '$date' WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.execute()
        }
    }
}