package com.api.api.user

import com.api.api.DB
import java.lang.Exception
import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.util.*

object DAOUser {
    fun listAll(querySearch: String?): List<User> {
        val search = if (querySearch.isNullOrBlank()) {
            "WHERE deleted_at IS NULL"
        } else {
            "WHERE name LIKE '%$querySearch%' AND deleted_at IS NULL"
        }
        val sql = "SELECT * FROM user $search"

        val userList = arrayListOf<User>()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val resultSet = preparedStatement.executeQuery()

            while(resultSet.next()) {
                val user = DAOUser.returnUserData(resultSet)
                userList.add(user)
            }
        }
        return userList
    }

    fun list(id: Int): User {
        val sql = "SELECT * FROM user WHERE id = $id AND deleted_at IS NULL"
        var user = User()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val resultSet = preparedStatement.executeQuery()
            if(resultSet.next()) user = returnUserData(resultSet)
        }
        return user
    }

    fun insert(user: User) {
        val sql = "INSERT INTO user (name, email, password, role) VALUES (?, ?, ?, ?)"
        var rowsAffected = 0
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)

            preparedStatement.setString(1, user.name)
            preparedStatement.setString(2, user.email)
            preparedStatement.setString(3, user.password)
            preparedStatement.setString(4, user.role)

            rowsAffected = preparedStatement.executeUpdate()
        }
        if (rowsAffected <= 0) throw Exception()
    }

    fun delete(id: Int) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.format(Date())

        val sql = "UPDATE user SET deleted_at = '$date' WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.execute()
        }
    }

    fun update(id: Int, user: User)  {
        val currentData = DAOUser.list(id)
        val name = if (user.name.isNullOrBlank()) currentData.name else user.name
        val email = if (user.email.isNullOrBlank()) currentData.email else user.email
        val password = if (user.password.isNullOrBlank()) currentData.password else user.password
        val role = if (user.role.isNullOrBlank()) currentData.role else user.role

        val sql = "UPDATE user SET name = ?, email = ?, password = ?, role = ? WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)

            preparedStatement.setString(1, name)
            preparedStatement.setString(2, email)
            preparedStatement.setString(3, password)
            preparedStatement.setString(4, role)

            preparedStatement.execute()
        }
    }

    fun restore(id: Int) {
        val sql = "UPDATE user SET deleted_at = null WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.execute()
        }
    }

    private fun returnUserData(resultSet: ResultSet): User {
        val user = User()
        user.id = resultSet.getInt("id")
        user.name = resultSet.getString("name")
        user.email = resultSet.getString("email")
        user.password = resultSet.getString("password")
        user.role = resultSet.getString("role")
        user.created_at = resultSet.getString("created_at")
        user.updated_at = resultSet.getString("updated_at")
        user.deleted_at = resultSet.getString("deleted_at")
        return user
    }
}