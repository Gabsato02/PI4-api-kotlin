package com.api.api.characteristics

import com.api.api.DB
import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.util.*

object DAOCharacteristics {
    fun listAll(querySearch: String?): List<Characteristic> {
        val search = if (querySearch.isNullOrBlank()) {
            "WHERE deleted_at IS NULL"
        } else {
            "WHERE name LIKE '%$querySearch%' AND deleted_at IS NULL"
        }

        val sql = "SELECT * FROM characteristics $search"
        val characteristicList = arrayListOf<Characteristic>()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val resultSet = preparedStatement.executeQuery()

            while(resultSet.next()) {
                val characteristic = returnCharacteristicData(resultSet)
                characteristicList.add(characteristic)
            }
        }
        return characteristicList
    }

    fun list(id: Int): Characteristic {
        val sql = "SELECT * FROM characteristics WHERE id = $id AND deleted_at IS NULL"
        var characteristic = Characteristic()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val resultSet = preparedStatement.executeQuery()

            if(resultSet.next()){
                characteristic = returnCharacteristicData(resultSet)
            }
        }
        return characteristic
    }

    fun listAllDeleted(querySearch: String?): List<Characteristic> {
        val search = if (querySearch.isNullOrBlank()) {
            "WHERE deleted_at IS NOT NULL"
        } else {
            "WHERE name LIKE '%$querySearch%' AND deleted_at IS NOT NULL"
        }

        val sql = "SELECT * FROM characteristics $search"
        val characteristicList = arrayListOf<Characteristic>()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val resultSet = preparedStatement.executeQuery()

            while(resultSet.next()) {
                val characteristic = returnCharacteristicData(resultSet)
                characteristicList.add(characteristic)
            }
        }
        return characteristicList
    }

    fun listDeleted(id: Int): Characteristic {
        val sql = "SELECT * FROM characteristics WHERE id = $id AND deleted_at IS NOT NULL"
        var characteristic = Characteristic()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val resultSet = preparedStatement.executeQuery()

            if(resultSet.next()){
                characteristic = returnCharacteristicData(resultSet)
            }
        }
        return characteristic
    }

    fun insert(characteristic: Characteristic) {
        val sql = "INSERT INTO characteristics (name, description, characteristics_value) VALUES (?, ?, ?)"
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)

            preparedStatement.setString(1, characteristic.name)
            preparedStatement.setString(2, characteristic.description)
            preparedStatement.setString(3, characteristic.characteristics_value)
            preparedStatement.execute()
        }
    }

    fun delete(id: Int) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.format(Date())

        val sql = "UPDATE characteristics SET deleted_at = '$date' WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.execute()
        }
    }

    fun update(id: Int, characteristic: Characteristic)  {
        val currentData = list(id)
        val name = if (characteristic.name.isNullOrBlank()) currentData.name else characteristic.name
        val description = if (characteristic.description.isNullOrBlank()) currentData.description else characteristic.description
        val characteristicsValue = if (characteristic.characteristics_value.isNullOrBlank()) currentData.characteristics_value else characteristic.characteristics_value

        val sql = "UPDATE characteristics SET name = ?, description = ?, characteristics_value = ? WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)

            preparedStatement.setString(1, name)
            preparedStatement.setString(2, description)
            preparedStatement.setString(3, characteristicsValue)

            preparedStatement.execute()
        }
    }

    fun restore(id: Int) {
        val sql = "UPDATE characteristics SET deleted_at = null WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)

            preparedStatement.execute()
        }
    }

    private fun returnCharacteristicData(resultSet: ResultSet): Characteristic {
        val characteristic = Characteristic()

        characteristic.id = resultSet.getInt("id")
        characteristic.name = resultSet.getString("name")
        characteristic.description = resultSet.getString("description")
        characteristic.characteristics_value = resultSet.getString("characteristics_value")
        characteristic.created_at = resultSet.getString("created_at")
        characteristic.updated_at = resultSet.getString("updated_at")
        characteristic.deleted_at = resultSet.getString("deleted_at")

        return characteristic
    }
}