package com.api.api.trait

import com.api.api.DB
import java.lang.Exception
import java.sql.ResultSet
import java.sql.SQLException
import java.text.SimpleDateFormat
import java.util.*

object DAOTrait {
    fun listAll(querySearch: String?): List<Trait> {
        val search = if (querySearch.isNullOrBlank()) {
            "WHERE deleted_at IS NULL"
        } else {
            "WHERE name LIKE '%$querySearch%' AND deleted_at IS NULL"
        }
        val sql = "SELECT * FROM trait $search"

        val traitList = arrayListOf<Trait>()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val resultSet = preparedStatement.executeQuery()

            while(resultSet.next()) {
                val trait = returnTraitData(resultSet)
                traitList.add(trait)
            }
        }
        return traitList
    }

    fun list(id: Int): Trait {
        val sql = "SELECT * FROM trait WHERE id = $id AND deleted_at IS NULL"
        var trait = Trait()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val resultSet = preparedStatement.executeQuery()

            if(resultSet.next()){
                trait = returnTraitData(resultSet)
            }
        }
        return trait
    }

    fun listAllDeleted(querySearch: String?): List<Trait> {
        val search = if (querySearch.isNullOrBlank()) {
            "WHERE deleted_at IS NOT NULL"
        } else {
            "WHERE name LIKE '%$querySearch%' AND deleted_at IS NOT NULL"
        }

        val sql = "SELECT * FROM trait $search"

        val traitList = arrayListOf<Trait>()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val resultSet = preparedStatement.executeQuery()

            while(resultSet.next()) {
                val trait = returnTraitData(resultSet)
                traitList.add(trait)
            }
        }
        return traitList
    }

    fun listDeleted(id: Int): Trait {
        val sql = "SELECT * FROM trait WHERE id = $id AND deleted_at IS NOT NULL"
        var trait = Trait()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val resultSet = preparedStatement.executeQuery()

            if(resultSet.next()){
                trait = returnTraitData(resultSet)
            }
        }
        return trait
    }

    fun insert(trait: Trait) {
        val sql = "INSERT INTO trait (name, description) VALUES (?, ?)"
        var rowsAffected = 0
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)

            preparedStatement.setString(1, trait.name)
            preparedStatement.setString(2, trait.description)
            rowsAffected = preparedStatement.executeUpdate()
        }
        if (rowsAffected <= 0) throw Exception()
    }

    fun delete(id: Int) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.format(Date())

        val sql = "UPDATE trait SET deleted_at = '$date' WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.execute()
        }
    }

    fun update(id: Int, trait: Trait)  {
        val currentData = list(id)
        println(currentData.description)
        val name = if (trait.name.isNullOrBlank()) currentData.name else trait.name
        val description = if (trait.description.isNullOrBlank()) currentData.description else trait.description

        val sql = "UPDATE trait SET name = ?, description = ? WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)

            preparedStatement.setString(1, name)
            preparedStatement.setString(2, description)

            preparedStatement.execute()
        }
    }

    fun restore(id: Int) {
        val sql = "UPDATE trait SET deleted_at = null WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)

            preparedStatement.execute()
        }
    }

    private fun returnTraitData(resultSet: ResultSet): Trait {
        val trait = Trait()
        trait.id = resultSet.getInt("id")
        trait.name = resultSet.getString("name")
        trait.description = resultSet.getString("description")
        trait.created_at = resultSet.getString("created_at")
        trait.updated_at = resultSet.getString("updated_at")
        trait.deleted_at = resultSet.getString("deleted_at")
        return trait
    }
}