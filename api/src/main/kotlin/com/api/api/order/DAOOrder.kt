package com.api.api.order

import com.api.api.DB
import com.api.api.item.DAOItem
import com.api.api.store.DAOStore
import java.lang.Exception
import java.sql.ResultSet
import java.sql.Statement

object DAOOrder {
    private var rowsAffected = 0
    fun list(userId: Int, storeId: Int = 0): List<Order> {
        val query = if(storeId == 0) "user_id = $userId" else "user_id = $userId AND store_id = $storeId"
        val sql = "SELECT * FROM orders WHERE $query"
        val orderList = arrayListOf<Order>()

        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val result = preparedStatement.executeQuery()

            while(result.next()) {
                val order = returnOrderData(result)
                orderList.add(order)
            }
        }

        return orderList
    }

    private fun listOrderItems(orderId: Int): List<OrderItem> {
        val sql = "SELECT * FROM order_item WHERE order_id = $orderId"
        val orderItemList = arrayListOf<OrderItem>()
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            val result = preparedStatement.executeQuery()

            while(result.next()) {
                val orderItem = OrderItem()

                orderItem.id = result.getInt("id")
                orderItem.price = result.getInt("price")
                orderItem.quantity = result.getInt("quantity")
                orderItem.order_id = result.getInt("order_id")
                orderItem.item_id = result.getInt("item_id")
                orderItem.item = DAOItem.list(orderItem.item_id)
                orderItem.created_at = result.getString("created_at")
                orderItem.updated_at = result.getString("updated_at")
                orderItem.deleted_at = result.getString("deleted_at")

                orderItemList.add(orderItem)
            }
        }
        return orderItemList
    }

    fun insert(order: Order): Int {
        val sql = "INSERT INTO orders (store_id, user_id) VALUES (?, ?)"
        var lastInsertedId = 0
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)

            preparedStatement.setInt(1, order.store_id)
            preparedStatement.setInt(2, order.user_id)
            rowsAffected = preparedStatement.executeUpdate()
            val generatedKeys = preparedStatement.generatedKeys
            if (generatedKeys.next()) lastInsertedId = generatedKeys.getInt(1)
        }
        if (rowsAffected <= 0) throw Exception()
        return lastInsertedId
    }

    fun addItem(orderItem: OrderItem) {
        val sql = "INSERT INTO order_item (price, quantity, order_id, item_id) VALUES (?, ?, ?, ?)"
        DB.connection.use {
            val preparedStatement = it.prepareStatement(sql)
            preparedStatement.setInt(1, orderItem.price)
            preparedStatement.setInt(2, orderItem.quantity)
            preparedStatement.setInt(3, orderItem.order_id)
            preparedStatement.setInt(4, orderItem.item_id)
            rowsAffected = preparedStatement.executeUpdate()
        }
        if (rowsAffected <= 0) throw Exception()
    }

    private fun returnOrderData(result: ResultSet): Order {
        val order = Order()

        order.id = result.getInt("id")
        order.store_id = result.getInt("store_id")
        order.store = DAOStore.list(order.store_id, false)
        order.user_id = result.getInt("user_id")
        order.order_items = listOrderItems(order.id)
        order.created_at = result.getString("created_at")
        order.updated_at = result.getString("updated_at")
        order.deleted_at = result.getString("deleted_at")

        return order
    }
}