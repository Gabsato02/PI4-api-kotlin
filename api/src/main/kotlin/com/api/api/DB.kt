package com.api.api

import java.sql.Connection
import java.sql.DriverManager

object DB {
    val connection: Connection
        get() {
            Class.forName("com.mysql.cj.jdbc.Driver")
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/pi4","root","" )
        }
}