package com.api.api

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

    fun list(): List<Store> {
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

    fun show(id: Int): Store {
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
}