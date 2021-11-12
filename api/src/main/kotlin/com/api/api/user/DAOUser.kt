package com.api.api.user

import com.api.api.DB

import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.util.*

object DAOUser {
    private var rowsAffected = 0

    fun listAll(querySearch: String?): List<User> {
        val search = if (querySearch.isNullOrBlank()) "" else "WHERE name LIKE '%$querySearch%'"
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
        val sql = "SELECT * FROM user WHERE id = $id"
        var user = User()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val resultSet = preparedStatement.executeQuery()
            if(resultSet.next()) user = returnUserData(resultSet)
        }
        return user
    }

    fun insert(user: User) {
        val sql = "INSERT INTO user (name, email, password, role, image) SELECT ?, ?, ?, ?, ? WHERE NOT EXISTS (SELECT * FROM user WHERE email = ?) LIMIT 1"

        var rowsAffected = 0
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)

            preparedStatement.setString(1, user.name)
            preparedStatement.setString(2, user.email)
            preparedStatement.setString(3, user.password)
            preparedStatement.setString(4, user.role)
            preparedStatement.setString(5, user.image)
            preparedStatement.setString(6, user.email)

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
        val currentData = list(id)
        val name = if (user.name.isNullOrBlank()) currentData.name else user.name
        val email = if (user.email.isNullOrBlank()) currentData.email else user.email
        var newPassword = user.newPassword
        var password = user.password
        password = if (newPassword.isNullOrBlank() || currentData.id == 0) {
            currentData.password.toString()
        } else {
            if (password == currentData.password) {
                newPassword
            } else {
                throw Exception("Senha atual incorreta.")
            }
        }
        val role = if (user.role.isNullOrBlank()) currentData.role else user.role

        val sql = "UPDATE user SET name = ?, email = ?, password = ?, role = ? WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)

            preparedStatement.setString(1, name)
            preparedStatement.setString(2, email)
            preparedStatement.setString(3, password)
            preparedStatement.setString(4, role)

            rowsAffected = preparedStatement.executeUpdate()
        }

        if (rowsAffected <= 0) throw Exception()
    }

    fun restore(id: Int) {
        val sql = "UPDATE user SET deleted_at = null WHERE id = $id"

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.execute()
        }
    }

    fun login(login: Login): String {
        val sql = "SELECT * FROM user WHERE email = '${login.email}' AND password = '${login.password}'"
        var userLogin = Login()
        var userRole = ""

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val resultSet = preparedStatement.executeQuery()
            if(resultSet.next()) {
                userLogin.email = resultSet.getString("email")
                userLogin.password = resultSet.getString("password")
                userRole = resultSet.getString("role")
            }
        }
        if (userLogin.email.isNullOrBlank() || userLogin.password.isNullOrBlank()) {
            throw Exception()
        } else {
            return userRole
        }
    }


    private fun returnUserData(resultSet: ResultSet): User {
        val user = User()
        user.id = resultSet.getInt("id")
        user.name = resultSet.getString("name")
        user.email = resultSet.getString("email")
        user.password = resultSet.getString("password")
        user.role = resultSet.getString("role")
        user.image = resultSet.getString("image")
        user.created_at = resultSet.getString("created_at")
        user.updated_at = resultSet.getString("updated_at")
        user.deleted_at = resultSet.getString("deleted_at")
        return user
    }
}