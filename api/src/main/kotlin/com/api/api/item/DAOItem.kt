package com.api.api.item

import com.api.api.DB
import com.api.api.category.DAOCategory
import com.api.api.characteristics.Characteristic
import com.api.api.characteristics.DAOCharacteristics
import com.api.api.formatDateToTimestamp
import com.api.api.trait.DAOTrait
import com.api.api.trait.Trait
import java.lang.Exception
import java.sql.ResultSet
import java.sql.Statement
import java.util.*

object DAOItem {
    var rowsAffected = 0
    fun listAll(querySearch: String?): List<Item> {
        val search = if (querySearch.isNullOrBlank()) "" else "WHERE i.name LIKE '%$querySearch%'"
        val sql = "SELECT * FROM item AS i JOIN category AS c ON i.category_id = c.id $search "

        val itemList = arrayListOf<Item>()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val result = preparedStatement.executeQuery()

            while(result.next()) {
                var item = returnItemData(result)
                itemList.add(item)
            }
        }
        return itemList
    }

    fun list(id: Int): Item {
        val sql = "SELECT * FROM item AS i JOIN category AS c ON i.category_id = c.id WHERE i.id = $id "
        var item = Item()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val result = preparedStatement.executeQuery()
            if(result.next()) item = returnItemData(result)
        }
        return item
    }

    fun listItemsByCategory(id: Int): List<Item> {
        val sql = "SELECT id, name, price, description, volume, category_id, created_at, updated_at, deleted_at FROM item WHERE category_id = $id"
        val itemsByCategoryList = arrayListOf<Item>()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val result = preparedStatement.executeQuery()

            while(result.next()) {
                var item = returnItemData(result, false)
                itemsByCategoryList.add(item)
            }
        }
        return itemsByCategoryList
    }

    fun listItemByStore(id: Int): List<Item> {
        val sql = "SELECT * FROM item_store AS i_store INNER JOIN item AS i ON " +
                "i_store.item_id = i.id WHERE i_store.store_id = $id AND i_store.deleted_at IS NULL AND i.deleted_at IS NULL GROUP BY i.id"
        val itemList = arrayListOf<Item>()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val result = preparedStatement.executeQuery()

            while(result.next()) {
                var item = list(result.getInt("item_id"))
                itemList.add(item)
            }
        }
        return itemList
    }

    private fun listItemCharacteristics(id: Int): List<Characteristic> {
        val sql = "SELECT * FROM item_characteristics AS ic INNER JOIN characteristics AS c ON " +
                "ic.characteristics_id = c.id WHERE ic.item_id = $id AND ic.deleted_at IS NULL AND c.deleted_at IS NULL GROUP BY c.id"
        val characteristicsList = arrayListOf<Characteristic>()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val result = preparedStatement.executeQuery()

            while(result.next()) {
                var characteristic = DAOCharacteristics.list(result.getInt("characteristics_id"))
                characteristicsList.add(characteristic)
            }
        }
        return characteristicsList
    }

    private fun listItemTraits(id: Int): List<Trait> {
        val sql = "SELECT * FROM item_trait AS it INNER JOIN trait AS t ON " +
                "it.trait_id = t.id WHERE it.item_id = $id AND it.deleted_at IS NULL AND t.deleted_at IS NULL GROUP BY t.id"
        val traitsList = arrayListOf<Trait>()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val result = preparedStatement.executeQuery()

            while(result.next()) {
                var trait = DAOTrait.list(result.getInt("trait_id"))
                traitsList.add(trait)
            }
        }
        return traitsList
    }

    fun insert(item: Item) {
        val sql = "INSERT INTO item (name, price, description, volume, category_id) VALUES (?, ?, ?, ?, ?)"
        var lastInsertedId = 0
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)

            preparedStatement.setString(1, item.name)
            preparedStatement.setInt(2, item.price)
            preparedStatement.setString(3, item.description)
            preparedStatement.setString(4, item.volume)
            preparedStatement.setInt(5, item.category_id)

            rowsAffected = preparedStatement.executeUpdate()
            val generatedKeys = preparedStatement.generatedKeys
            if (generatedKeys.next()) lastInsertedId = generatedKeys.getInt(1)

            val traits = item.traits
            if (traits != null) {
                for (trait in traits) {
                    addTrait(lastInsertedId, trait.id)
                }
            }

            val characteristics = item.characteristics
            if (characteristics != null) {
                for (characteristic in characteristics) {
                    addCharacteristic(lastInsertedId, characteristic.id)
                }
            }
        }
        if (rowsAffected <= 0) throw Exception()
    }

    fun delete(id: Int) {
        val date = formatDateToTimestamp(Date())

        val sql = "UPDATE item SET deleted_at = '$date' WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            rowsAffected = preparedStatement.executeUpdate()
        }
        if (rowsAffected <= 0) throw Exception()
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

            rowsAffected = preparedStatement.executeUpdate()
        }

        val traits = item.traits
        if (traits != null) {
            for (trait in traits) {
                addTrait(id, trait.id)
            }
        }

        val characteristics = item.characteristics
        if (characteristics != null) {
            for (characteristic in characteristics) {
                addCharacteristic(id, characteristic.id)
            }
        }

        if (rowsAffected <= 0) throw Exception()
    }

    fun restore(id: Int) {
        val sql = "UPDATE item SET deleted_at = null WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            rowsAffected = preparedStatement.executeUpdate()
        }
        if (rowsAffected <= 0) throw Exception()
    }

    fun addTrait(itemId: Int, traitId: Int) {
        val sql = "INSERT INTO item_trait (item_id, trait_id) VALUES (?, ?)"
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.setInt(1, itemId)
            preparedStatement.setInt(2, traitId)
            rowsAffected = preparedStatement.executeUpdate()
        }
        if (rowsAffected <= 0) throw Exception()
    }

    fun removeTrait(itemId: Int, traitId: Int) {
        val date = formatDateToTimestamp(Date())
        val sql = "UPDATE item_trait SET deleted_at = '$date' WHERE item_id = $itemId AND trait_id = $traitId"
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            rowsAffected = preparedStatement.executeUpdate()
        }
        if (rowsAffected <= 0) throw Exception()
    }

    fun addCharacteristic(itemId: Int, characteristicId: Int) {
        val sql = "INSERT INTO item_characteristics (item_id, characteristics_id) VALUES (?, ?)"
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.setInt(1, itemId)
            preparedStatement.setInt(2, characteristicId)
            rowsAffected = preparedStatement.executeUpdate()
        }
        if (rowsAffected <= 0) throw Exception()
    }

    fun removeCharacteristic(itemId: Int, characteristicId: Int) {
        val date = formatDateToTimestamp(Date())
        val sql = "UPDATE item_characteristics SET deleted_at = '$date' WHERE item_id = $itemId AND characteristics_id = $characteristicId"
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            rowsAffected = preparedStatement.executeUpdate()
        }
        if (rowsAffected <= 0) throw Exception()
    }

    private fun returnItemData(result: ResultSet, showCategory: Boolean = true): Item {
        val item = Item()

        item.id = result.getInt("id")
        item.name = result.getString("name")
        item.price = result.getInt("price")
        item.description = result.getString("description")
        item.volume = result.getString("volume")
        item.category = if (showCategory) DAOCategory.returnCategoryData(result, false) else null
        item.characteristics = listItemCharacteristics(item.id)
        item.traits = listItemTraits(item.id)
        item.category_id = result.getInt("category_id")
        item.created_at = result.getString("created_at")
        item.updated_at = result.getString("updated_at")
        item.deleted_at = result.getString("deleted_at")

        return item
    }
}