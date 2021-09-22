package com.api.api.store

import com.api.api.DB
import java.text.SimpleDateFormat
import java.util.*

object DAOStore {
    fun insert(store: Store) {
        val sql = "INSERT INTO store (name, description, owner_id) VALUES (?, ?, ?)"
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)

            preparedStatement.setString(1, store.name)
            preparedStatement.setString(2, store.description)
            preparedStatement.setInt(3, 1)
            preparedStatement.execute()
        }
    }

    fun listAll(): List<Store> {
        val sql = "SELECT * FROM store"
        val storeList = arrayListOf<Store>()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val resultSet = preparedStatement.executeQuery()

            while(resultSet.next()) {
                val store = Store()
                store.id = resultSet.getInt("id")
                store.name = resultSet.getString("name")
                store.description = resultSet.getString("description")
                store.owner_id = resultSet.getInt("owner_id")
                storeList.add(store)
            }
        }
        return storeList
    }

    fun list(id: Int): Store {
        val sql = "SELECT * FROM store WHERE id = $id"
        val store = Store()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val resultSet = preparedStatement.executeQuery()

            if(resultSet.next()){
                store.id = resultSet.getInt("id")
                store.name = resultSet.getString("name")
                store.description = resultSet.getString("description")
                store.owner_id = resultSet.getInt("owner_id")
            }
        }
        return store
    }

    fun delete(id: Int) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.format(Date())

        val sql = "UPDATE store SET deleted_at = '$date' WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.execute()
        }
    }

    fun update(id: Int, store: Store) {
        val currentData = DAOStore.list(id)
        val name = if (store.name.isNullOrBlank()) currentData.name else store.name
        val description = if (store.description.isNullOrBlank()) currentData.description else store.description

        val sql = "UPDATE store SET name = ?, description = ? WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)

            preparedStatement.setString(1, name)
            preparedStatement.setString(2, description)

            preparedStatement.execute()
        }
    }
}