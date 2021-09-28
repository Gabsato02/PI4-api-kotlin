package com.api.api.category

import com.api.api.DB
import com.api.api.formatDateToTimestamp
import com.api.api.item.DAOItem
import java.sql.ResultSet
import java.util.*

object DAOCategory {
    fun listAll(querySearch: String?, queryItems: Boolean): List<Category> {
        val search = if (querySearch.isNullOrBlank()) "" else "WHERE name LIKE '%$querySearch%'"
        val sql = "SELECT c.id, c.name, c.created_at, c.deleted_at, c.updated_at FROM category AS c $search"

        val categoryList = arrayListOf<Category>()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val result = preparedStatement.executeQuery()

            while(result.next()) {
                val category = returnCategoryData(result, queryItems)
                categoryList.add(category)
            }
        }
        return categoryList
    }

    fun list(id: Int, queryItems: Boolean): Category {
        val sql = "SELECT * FROM category WHERE id = $id "
        var category = Category()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val result = preparedStatement.executeQuery()
            if(result.next()) category = returnCategoryData(result, queryItems)
        }
        return category
    }

    fun insert(category: Category) {
        val sql = "INSERT INTO category (name) VALUES (?)"
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.setString(1, category.name)
            preparedStatement.execute()
        }
    }

    fun delete(id: Int) {
        val date = formatDateToTimestamp(Date())

        val sql = "UPDATE category SET deleted_at = '$date' WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.execute()
        }
    }

    fun update(id: Int, category: Category) {
        val currentData = DAOCategory.list(id, false)
        val name = if (category.name.isNullOrBlank()) currentData.name else category.name

        val sql = "UPDATE category SET name = ? WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.setString(1, name)
            preparedStatement.execute()
        }
    }

    fun restore(id: Int) {
        val sql = "UPDATE category SET deleted_at = null WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.execute()
        }
    }

    fun returnCategoryData(result: ResultSet, shouldBringItems: Boolean): Category {
        val category = Category()

        category.id = result.getInt("c.id")
        category.name = result.getString("c.name")
        category.created_at = result.getString("c.created_at")
        category.updated_at = result.getString("c.updated_at")
        category.deleted_at = result.getString("c.deleted_at")
        category.items = if(shouldBringItems) DAOItem.listItemsByCategory(category.id) else null
        return category
    }

}